package org.it611.das.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.it611.das.fabric.ChaincodeManager;
import org.it611.das.fabric.util.FabricManager;
import org.it611.das.mapper.DegreeCertificationMapper;
import org.it611.das.service.AssetService;
import org.it611.das.domain.*;
import org.it611.das.mapper.AssetMapper;
import org.it611.das.util.MapUtil;
import org.it611.das.util.ParseInputAsset;
import org.it611.das.util.ResponseUtil;
import org.it611.das.vo.StudentIdCardAssetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.JsonObject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Service
public class AssetServiceImpl implements AssetService {

    private static Logger logger = Logger.getLogger(AssetServiceImpl.class);

    @Autowired
    private AssetMapper assetDao;

    @Autowired
    private DegreeCertificationMapper degreeCertificationDao;


    @Override
    @Transactional
    public Map<String, Object> InsertStudentIdCardAsset(StudentIdCardAssetVO vo) throws Exception {

        HashMap resultData = new HashMap<String, Object>();
        //解析VO
        Map<String, Object> dataMap = ParseInputAsset.parseStuIdCardAsset(vo);
        String assetId = (String) dataMap.get("assetId");
        BaseAsset baseAsset = (BaseAsset) dataMap.get("baseAsset");
        List assetUserList = (ArrayList<AssetUser>) dataMap.get("assetUserList");
        StudentIdCardAsset studentIdCardAsset = (StudentIdCardAsset) dataMap.get("studentIdCardAsset");
        List assetFilesList = (ArrayList<AssetFiles>) dataMap.get("assetFilesList");
        String fabricStudentIdCardAssetJson = (String) dataMap.get("fabricStudentIdCardAssetJson");


        //执行Fabric的写入
        ChaincodeManager manager = FabricManager.obtain().getManager();
        String[] arguments = new String[]{assetId, fabricStudentIdCardAssetJson};
        Map<String, String> result = manager.invoke("addAsset", arguments);
        logger.info("insert fabric information:" + fabricStudentIdCardAssetJson);
        logger.info("endorse result:" + result.get("data").toString());
        baseAsset.setTxId(result.get("txid").toString());

        //执行MySQL写入
        int r1 = assetDao.insertAssetBase(baseAsset);
        int r2 = assetDao.insertAssetUser(assetUserList);
        int r3 = assetDao.insertStudentIdCardAsset(studentIdCardAsset);
        int r4 = assetDao.insertAssetFiles(assetFilesList);


        if (r1 > 0 && r2 > 0 && r3 > 0 && r4 > 0) {
            dataMap.put("code", 200);
            dataMap.put("assetId", assetId);
            dataMap.put("txId", result.get("txid").toString());
            return dataMap;
        }
        dataMap.put("code", 400);
        return dataMap;
    }

    @Override
    public HashMap<String, Object> selectStudentIdCardAssetById(String id) throws InvalidArgumentException, NoSuchAlgorithmException, IOException, TransactionException, NoSuchProviderException, CryptoException, InvalidKeySpecException, ProposalException {

        HashMap<String, Object> resultmap = new HashMap();
        HashMap<String, Object> mysqlData = assetDao.selectStudentIdCardAssetDetailById(id);//获取mysql中的数据
        ChaincodeManager manager = FabricManager.obtain().getManager();
        String[] argQuery = new String[]{id};
        Map<String, String> queryData = manager.query("query", argQuery);
        String resultState = queryData.get("code");

        String dataJson = queryData.get("data");
        ObjectMapper objectMapper = new ObjectMapper();
        if (resultState.equals("success") && mysqlData != null && !"".equals(dataJson)) {
            HashMap fabricStateData = objectMapper.readValue(dataJson, HashMap.class);
            resultmap.put("code", 200);//fabric请求成功
            resultmap.put("mysqlData", mysqlData);
            resultmap.put("fabricStateData", fabricStateData);
        } else {
            HashMap fabricStateData = objectMapper.readValue(dataJson, HashMap.class);
            resultmap.put("code", 400);//fabric请求失败
            resultmap.put("mysqlData", mysqlData);
            resultmap.put("fabricStateData", fabricStateData);
        }
        return resultmap;

    }

    @Override
    public HashMap<String, Object> selectAssetList(int currentPage, int numberOfPages, String title, String id, String txid) {

        HashMap dataMap = new HashMap<String, Object>();
        PageHelper.startPage(currentPage, numberOfPages);
        List<HashMap> resultData = assetDao.selectAsset(title, id, txid);
        long total = assetDao.selectTotal(title, id, txid);
        dataMap.put("rows", resultData);
        dataMap.put("total", total);
        return dataMap;
    }


    @Transactional
    @Override
    public List<HashMap> studentDetail(String id) {

        List<HashMap> result = assetDao.studentDetail(id);
        return result;
    }

    @Override
    public JSONObject degreeCertificationList(int currentPage, int numberOfPages, String certId) {
        Map data = new HashMap<String, Object>();
        List<HashMap> result = degreeCertificationDao.selectDegreeCertificationByConditions(certId);
        Long total = degreeCertificationDao.selectTotal(certId);
        data.put("rows", result);
        data.put("total", total);
        return ResponseUtil.constructResponse(200, "ok", data);

    }


    /**
     *学位证书资产对比业务
     */
    @Transactional
    @Override
    public JSONObject selectDegreeCertificationDetailById(String id) throws InvalidArgumentException, NoSuchAlgorithmException, IOException, TransactionException, NoSuchProviderException, CryptoException, InvalidKeySpecException, ProposalException {

        HashMap<String, Object> resultMap = new HashMap();
        HashMap<String, Object> mysqlDataMap = degreeCertificationDao.selectDegreeCertificationDetailById(id);//获取mysql中的数据
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
/*        if (resultState == "0") {//如果是未审核状态
            resultMap.put("mysqlData", mysqlDataMap);
            resultMap.put("fabricData", fabricDataMap);//fabricDataMap==null
            return ResponseUtil.constructResponse(200, "ok", resultMap);
        }*/
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


    /**
     *学位证书资产审核（包含数据上链，transactionId和state的修改）
     */
    @Override
    @Transactional
    public JSONObject CheckDegreeCertificationAndChangeState(String id, String state){

//        这里从数据库查找state对比传入参数的的state，结果相同就不进行操作
        String selState=degreeCertificationDao.selectStateById(id);
        if(selState.equals(state))  return ResponseUtil.constructResponse(200, "ok", null);//状态相同直接返回

        //如果不是进行审核(不等于1)，即单纯的state更新
        if(!"1".equals(state)){
            int updateResult = degreeCertificationDao.updateState(id, state);
            if(!(updateResult>0)){
                return ResponseUtil.constructResponse(400, "insert database failed.", null);//出现异常直接返回错误
            }
            return ResponseUtil.constructResponse(200, "ok", null);//出现异常直接返回错误
        }
        //如果是审核，即传入进来的state=1
        //获取mysql中的学位证书信息
        HashMap<String, Object> dataMap = degreeCertificationDao.selectDegreeCertificationDetailById(id);
        String assetId = dataMap.get("id").toString();//资产Id作为fabric state 数据库的key
        Map<String, String> result = null;
        //剔除不需要上链的数据(数据暂定如下)
        dataMap.remove("id");
        dataMap.remove("ownerId");
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
            result = manager.invoke("addAsset", arguments);
            if(result.get("code").equals("error")){//如果插入错误
                return ResponseUtil.constructResponse(400, "insert blockchain failed.", null);//返回error直接返回错误
            }
            logger.info("insert fabric information:" + degreeCertificationJsonStr);
            logger.info("endorse result:" + result.get("data").toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.constructResponse(400, "insert blockchain failed.", null);//出现异常直接返回错误
        }
        //区块链正常写入之后
        String transactionId = result.get("txid").toString();//由背书节点返回的transactionId
        System.out.println(transactionId);
        //数据库状态进行更新，包括transactionId和state
        int updateResult = degreeCertificationDao.updateStateAndTransaction(id,transactionId, "1");
        if (!(updateResult >0)) {
            return ResponseUtil.constructResponse(400, "insert database failed.", null);//出现异常直接返回错误
        }
        return ResponseUtil.constructResponse(200, "ok", null);//正确的返回
    }





}
