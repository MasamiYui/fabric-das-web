package org.it611.das.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.it611.das.domain.Photo;
import org.it611.das.fabric.ChaincodeManager;
import org.it611.das.fabric.util.FabricManager;
import org.it611.das.mapper.PhotoAssetMapper;
import org.it611.das.mapper.VideoAssetMapper;
import org.it611.das.service.PhotoAssetService;
import org.it611.das.service.VideoAssetService;
import org.it611.das.util.MapUtil;
import org.it611.das.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PhotoAssetServiceImpl implements PhotoAssetService {

    @Autowired
    private PhotoAssetMapper photoAssetMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public JSONObject photoAssetList(int currentPage, int numberOfPages, String title,String state) {

        HashMap dataMap = new HashMap<String, Object>();
        Criteria ownerCriteria = null;
        if("".equals(state)){
            ownerCriteria = Criteria.where("title").regex(title);
        }else{
            ownerCriteria = Criteria.where("title").regex(title).and("state").is(state);
        }
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        long total = mongoTemplate.count(query, Photo.class);//查询总数
        query.skip((currentPage - 1) * numberOfPages).limit(numberOfPages);//分页查询
        List<Photo> resultData = mongoTemplate.find(query, Photo.class);
        dataMap.put("rows", resultData);
        dataMap.put("total", total);
        return ResponseUtil.constructResponse(200, "ok", dataMap);
    }

    @Override
    public JSONObject photoAssetDetail(String id) throws InvalidArgumentException, NoSuchAlgorithmException, IOException, TransactionException, NoSuchProviderException, CryptoException, InvalidKeySpecException, ProposalException {

        HashMap<String, Object> resultMap = new HashMap();
        HashMap mongoDataMap = null;
        HashMap<String, Object> fabricDataMap = null;
        Criteria ownerCriteria = Criteria.where("id").is(id);
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        try {
            mongoDataMap = MapUtil.convertToMap(mongoTemplate.find(query, Photo.class).get(0));
        } catch (Exception e) {
            return ResponseUtil.constructResponse(400, "query database failed.", resultMap);
        }

        if (mongoDataMap.get("state").equals("0")) {//如果是未审核状态
            resultMap.put("mongoData", mongoDataMap);
            resultMap.put("fabricData", MapUtil.setMapValue(mongoDataMap));
            return ResponseUtil.constructResponse(200, "ok", resultMap);
        }
        ChaincodeManager manager = FabricManager.obtain().getManager();
        String[] argQuery = new String[]{id};
        //如果是已审核状态，表明已有资产信息录入到区块链中
        Map<String, String> queryData = manager.query("query", argQuery);
        String resultState = queryData.get("code");

        String dataJson = queryData.get("data");
        ObjectMapper objectMapper = new ObjectMapper();
        if (!resultState.equals("success")) {//如果向区块链请求发生错误
            resultMap.put("mongoData", mongoDataMap);
            resultMap.put("fabricData", fabricDataMap);//fabricDataMap==null
            return ResponseUtil.constructResponse(400, "query blockchain failed.", resultMap);
        }
        if("".equals(dataJson)){
            resultMap.put("mongoData", mongoDataMap);
            resultMap.put("fabricData", MapUtil.setMapValue(mongoDataMap));
            return ResponseUtil.constructResponse(200, "ok", resultMap);
        }
        fabricDataMap = objectMapper.readValue(dataJson, HashMap.class);
        resultMap.put("mongoData", mongoDataMap);
        resultMap.put("fabricData", fabricDataMap);
        return ResponseUtil.constructResponse(200, "ok", resultMap);
    }

    @Override
    public JSONObject CheckPhotoAssetAndChangeState(String id, String state) throws Exception {

        Criteria ownerCriteria = Criteria.where("id").is(id);
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        Photo photo = mongoTemplate.find(query, Photo.class).get(0);
        String selState = photo.getState();

        if(selState.equals(state))  return ResponseUtil.constructResponse(200, "ok", null);//状态相同直接返回

        //如果不是进行审核(不等于1)，即单纯的state更新
        if(!"1".equals(state)){
            photo.setState(state);
            try{
                mongoTemplate.save(photo);
            }catch (Exception e){
                return ResponseUtil.constructResponse(400, "insert database failed.", null);//出现异常直接返回错误
            }
            return ResponseUtil.constructResponse(200, "ok", null);
        }
        //如果是审核，即传入进来的state=1
        //获取mysql中的学位证书信息
        //HashMap<String, Object> dataMap = photoAssetMapper.selectPhotoAssetDetailById(id);
        HashMap<String, Object> dataMap = MapUtil.convertToMap(photo);
        String assetId = dataMap.get("id").toString();//资产Id作为fabric state 数据库的key
        String transactionId = dataMap.get("transactionId").toString();
        Map<String, String> result = null;
        //剔除不需要上链的数据(数据暂定如下)
        dataMap.remove("id");
        dataMap.remove("files");
        dataMap.remove("submitTime");
        dataMap.remove("state");
        dataMap.remove("transactionId");

        String degreeCertificationJsonStr = null;//value转json字符串
        try {
            degreeCertificationJsonStr = new ObjectMapper().writeValueAsString(dataMap);
            //执行Fabric的写入（数据上链）
            ChaincodeManager manager = FabricManager.obtain().getManager();
            String[] arguments = new String[]{assetId, degreeCertificationJsonStr};
            if(!transactionId.equals("")){//如果是已经上链的数据，即数据库中已经有transactionId，只需要改变状态
                photo.setState(state);
                try{
                    mongoTemplate.save(photo);
                    return ResponseUtil.constructResponse(200, "ok", null);
                }catch (Exception e){
                    return ResponseUtil.constructResponse(400, "insert database failed.", null);//出现异常直接返回错误
                }
            }
            result = manager.invoke("addAsset", arguments);
            if(result.get("code").equals("error")){//如果插入错误
                return ResponseUtil.constructResponse(400, "insert blockchain failed.", null);//返回error直接返回错误
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.constructResponse(400, "insert blockchain failed.", null);//出现异常直接返回错误
        }
        //区块链正常写入之后
        transactionId = result.get("txid").toString();//由背书节点返回的transactionId
        System.out.println(transactionId);
        //数据库状态进行更新，包括transactionId和state
        photo.setState(state);
        photo.setTransactionId(transactionId);
        try{
            mongoTemplate.save(photo);
        }catch (Exception e){
            return ResponseUtil.constructResponse(400, "insert database failed.", null);//出现异常直接返回错误
        }
        return ResponseUtil.constructResponse(200, "ok", null);//正确的返回
    }


}
