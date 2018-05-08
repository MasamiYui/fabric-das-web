package org.it611.das.service.impl;

import org.apache.log4j.Logger;
import org.it611.das.fabric.ChaincodeManager;
import org.it611.das.fabric.util.FabricManager;
import org.it611.das.service.AssetService;
import org.it611.das.domain.*;
import org.it611.das.mapper.AssetMapper;
import org.it611.das.util.ParseInputAsset;
import org.it611.das.util.State;
import org.it611.das.vo.StudentIdCardAssetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AssetServiceImpl implements AssetService {

    private static Logger logger = Logger.getLogger(AssetServiceImpl.class);

    @Autowired
    private AssetMapper assetDao;


    @Override
    @Transactional
    public Map<String, Object> InsertStudentIdCardAsset(StudentIdCardAssetVO vo) throws Exception {

        HashMap resultData = new HashMap<String, Object>();
        //解析VO
        Map<String, Object> dataMap = ParseInputAsset.parseStuIdCardAsset(vo);
        String assetId = (String)dataMap.get("assetId");
        BaseAsset baseAsset = (BaseAsset)dataMap.get("baseAsset");
        List assetUserList = (ArrayList<AssetUser>)dataMap.get("assetUserList");
        StudentIdCardAsset studentIdCardAsset = (StudentIdCardAsset)dataMap.get("studentIdCardAsset");
        List assetFilesList = (ArrayList<AssetFiles>)dataMap.get("assetFilesList");
        String fabricStudentIdCardAssetJson = (String) dataMap.get("fabricStudentIdCardAssetJson");


        //执行Fabric的写入
        ChaincodeManager manager = FabricManager.obtain().getManager();
        String[] arguments = new String[]{assetId,fabricStudentIdCardAssetJson};
        Map<String, String> result = manager.invoke("addAsset", arguments);
        logger.info("insert fabric information:"+fabricStudentIdCardAssetJson);
        logger.info("endorse result:"+result.get("data").toString());
        baseAsset.setTxId(result.get("txid").toString());

        //执行MySQL写入
        int r1 = assetDao.insertAssetBase(baseAsset);
        int r2 = assetDao.insertAssetUser(assetUserList);
        int r3 = assetDao.insertStudentIdCardAsset(studentIdCardAsset);
        int r4 = assetDao.insertAssetFiles(assetFilesList);


        if(r1>0 && r2>0 && r3>0 && r4>0) {
            dataMap.put("code", 200);
            dataMap.put("assetId", assetId);
            dataMap.put("txId", result.get("txid").toString());
            return dataMap;
        }
        dataMap.put("code", 400);
        return dataMap;
    }
}
