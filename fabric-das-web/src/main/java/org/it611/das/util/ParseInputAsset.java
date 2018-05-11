package org.it611.das.util;

import org.it611.das.domain.*;
import org.it611.das.domain.fabric.FabricStudentIdCardAsset;
import org.it611.das.vo.StudentIdCardAssetVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParseInputAsset {

    public static Map<String, Object> parseStuIdCardAsset(StudentIdCardAssetVO aicaVO) throws IOException {

        String assetId = IdUtil.getId();
        Map<String, Object> dataMap = new HashMap<>();
        ArrayList<AssetUser> assetUserList = new ArrayList<>();
        ArrayList<AssetFiles> assetFileList = new ArrayList<>();

        //解析asset_base表中的内容
        BaseAsset baseAsset = new BaseAsset(assetId, aicaVO.getType(), aicaVO.getTitle(), aicaVO.getDes(), TimeUtil.getLocalTime(), 0, null);

        //解析asset_owner表中的内容
        String users = aicaVO.getOwners();
        //尝试对users进行切分
        String[] userArr = users.split(";");
        for(String user: userArr) {
            AssetUser ua = new AssetUser(IdUtil.getId(), assetId, user);
            assetUserList.add(ua);
        }

        //解析asset_stuIdCard表中的内容
        StudentIdCardAsset aica = new StudentIdCardAsset(IdUtil.getId(), assetId, aicaVO.getStuId(), aicaVO.getSchool(), aicaVO.getName(),
                aicaVO.getSex(), aicaVO.getDateOfBirth(), aicaVO.getIdCardNo(), aicaVO.getLengthOfSchooling(), aicaVO.getCollege(), aicaVO.getAddress(),
                aicaVO.getSchoolTime(), aicaVO.getTimeOfIssume());

        //解析所有的电子文件
        String filesUrls = aicaVO.getFiles();
        String[] urlArr = filesUrls.split(";");
        String filesHash = aicaVO.getFilesHash();
        String[] fileHashArr = filesHash.split(";");
        int fileNum = fileHashArr.length;
        for(int i=0; i<fileNum; i++) {
            AssetFiles af = new AssetFiles(IdUtil.getId(), assetId, urlArr[i], fileHashArr[i]);
            assetFileList.add(af);
        }


        //解析需要写入Fabric中的数据
        FabricStudentIdCardAsset fabricStudentIdCardAsset = new FabricStudentIdCardAsset(aicaVO.getType(), aicaVO.getTitle(), userArr,
                aicaVO.getDes(), aicaVO.getStuId(), aica.getSchool(), aica.getName(), aicaVO.getSex(), aicaVO.getDateOfBirth(),
                aica.getIdCardNo(), aica.getLengthOfSchooling(), aicaVO.getCollege(), aica.getAddress(), aica.getSchoolTime(),
                aica.getTimeOfIssume(), fileHashArr);
        String fabricStudentIdCardAssetJson = JsonUtil.bean2Json(fabricStudentIdCardAsset);


        //把解析出来的这4个表的内容加入到Map中
        dataMap.put("assetId", assetId);//放入资产Id，便于后面操作
        dataMap.put("baseAsset", baseAsset);
        dataMap.put("assetUserList", assetUserList);
        dataMap.put("studentIdCardAsset", aica);
        dataMap.put("assetFilesList", assetFileList);
        dataMap.put("fabricStudentIdCardAssetJson",fabricStudentIdCardAssetJson);
        return dataMap;
    }


}
