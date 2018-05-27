package org.it611.das.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.it611.das.fabric.ChaincodeManager;
import org.it611.das.fabric.util.FabricManager;
import org.it611.das.service.StudentIdAssetService;
import org.it611.das.domain.*;
import org.it611.das.mapper.AssetMapper;
import org.it611.das.util.ParseInputAsset;
import org.it611.das.vo.StudentIdCardAssetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentIdAssetServiceImpl implements StudentIdAssetService {

    private static Logger logger = Logger.getLogger(StudentIdAssetServiceImpl.class);

    @Autowired
    private AssetMapper assetDao;




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



}
