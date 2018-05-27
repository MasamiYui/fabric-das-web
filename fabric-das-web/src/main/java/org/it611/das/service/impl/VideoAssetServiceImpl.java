package org.it611.das.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.it611.das.fabric.ChaincodeManager;
import org.it611.das.fabric.util.FabricManager;
import org.it611.das.mapper.VideoAssetMapper;
import org.it611.das.service.VideoAssetService;
import org.it611.das.util.MapUtil;
import org.it611.das.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VideoAssetServiceImpl implements VideoAssetService {

    @Autowired
    private VideoAssetMapper videoAssetMapper;

    @Override
    public JSONObject videoAssetList(int currentPage, int numberOfPages, String title) {

        Map data = new HashMap<String, Object>();
        List<HashMap> result = videoAssetMapper.selectVideoAssetByConditions(title);
        Long total = videoAssetMapper.selectTotal(title);
        data.put("rows", result);
        data.put("total", total);
        return ResponseUtil.constructResponse(200, "ok", data);
    }

    @Override
    public JSONObject videoAssetDetail(String id) throws InvalidArgumentException, NoSuchAlgorithmException, IOException, TransactionException, NoSuchProviderException, CryptoException, InvalidKeySpecException, ProposalException {

        HashMap<String, Object> resultMap = new HashMap();
        HashMap<String, Object> mysqlDataMap = videoAssetMapper.selectVideoAssetDetailById(id);//获取mysql中的数据
        HashMap<String, Object> fabricDataMap = null;
        if (mysqlDataMap.get("state").equals("0")) {//如果是未审核状态
            resultMap.put("mysqlData", mysqlDataMap);
            resultMap.put("fabricData", MapUtil.setMapValue(mysqlDataMap));
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
            resultMap.put("mysqlData", mysqlDataMap);
            resultMap.put("fabricData", fabricDataMap);//fabricDataMap==null
            return ResponseUtil.constructResponse(400, "query blockchain failed.", resultMap);
        }
        fabricDataMap = objectMapper.readValue(dataJson, HashMap.class);
        resultMap.put("mysqlData", mysqlDataMap);
        resultMap.put("fabricData", fabricDataMap);
        return ResponseUtil.constructResponse(200, "ok", resultMap);
    }

    @Override
    public JSONObject CheckVideoAssetAndChangeState(String id, String state) throws InvalidArgumentException, NoSuchAlgorithmException, IOException, TransactionException, NoSuchProviderException, CryptoException, InvalidKeySpecException, ProposalException {

        String selState= videoAssetMapper.selectStateById(id);
        if(selState.equals(state))  return ResponseUtil.constructResponse(200, "ok", null);//状态相同直接返回

        //如果不是进行审核(不等于1)，即单纯的state更新
        if(!"1".equals(state)){
            int updateResult = videoAssetMapper.updateState(id, state);
            if(!(updateResult>0)){
                return ResponseUtil.constructResponse(400, "insert database failed.", null);//出现异常直接返回错误
            }
            return ResponseUtil.constructResponse(200, "ok", null);//出现异常直接返回错误
        }
        //如果是审核，即传入进来的state=1
        //获取mysql中的学位证书信息
        HashMap<String, Object> dataMap = videoAssetMapper.selectVideoAssetDetailById(id);
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
                int updateResult = videoAssetMapper.updateStateAndTransaction(id,transactionId, state);
                if (!(updateResult >0)) {
                    return ResponseUtil.constructResponse(400, "insert database failed.", null);//出现异常直接返回错误
                }
                return ResponseUtil.constructResponse(200, "ok", null);//出现异常直接返回错误
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
        int updateResult = videoAssetMapper.updateStateAndTransaction(id,transactionId, state);
        if (!(updateResult >0)) {
            return ResponseUtil.constructResponse(400, "insert database failed.", null);//出现异常直接返回错误
        }
        return ResponseUtil.constructResponse(200, "ok", null);//正确的返回
    }


}