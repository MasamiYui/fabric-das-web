package org.it611.das.Service.impl;

import org.it611.das.Service.AssetService;
import org.it611.das.domain.AssetFiles;
import org.it611.das.domain.AssetUser;
import org.it611.das.domain.BaseAsset;
import org.it611.das.domain.StudentIdCardAsset;
import org.it611.das.mapper.AssetMapper;
import org.it611.das.util.ParseInputAsset;
import org.it611.das.util.State;
import org.it611.das.vo.StudentIdCardAssetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetMapper assetDao;

    @Override
    @Transactional
    public int InsertStudentIdCardAsset(StudentIdCardAssetVO vo) {
        //解析VO
        Map<String, Object> dataMap = ParseInputAsset.parseStuIdCardAsset(vo);
        BaseAsset baseAsset = (BaseAsset)dataMap.get("baseAsset");
        List assetUserList = (ArrayList<AssetUser>)dataMap.get("assetUserList");
        StudentIdCardAsset studentIdCardAsset = (StudentIdCardAsset)dataMap.get("studentIdCardAsset");
        List assetFilesList = (ArrayList<AssetFiles>)dataMap.get("assetFilesList");

        //执行
        int r1 = assetDao.insertAssetBase(baseAsset);
        int r2 = assetDao.insertAssetUser(assetUserList);
        int r3 = assetDao.insertStudentIdCardAsset(studentIdCardAsset);
        int r4 = assetDao.insertAssetFiles(assetFilesList);

        if(r1>0 && r2>0 && r3>0 && r4>0) {
            return State.SUCCESS;
        }
        return State.FALSE;



    }
}
