package org.it611.das.util;

import org.it611.das.domain.AssetFiles;
import org.it611.das.domain.BaseAsset;
import org.it611.das.domain.StudentIdCardAsset;
import org.it611.das.domain.AssetUser;
import org.it611.das.vo.StudentIdCardAssetVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParseInputAsset {

    public static Map<String, Object> parseStuIdCardAsset(StudentIdCardAssetVO aicaVO) {

        String assetId = IdUtil.getId();

        Map<String, Object> dataMap = new HashMap<>();
        ArrayList<AssetUser> assetUserList = new ArrayList<>();
        ArrayList<AssetFiles> assetFileList = new ArrayList<>();

        //解析asset_base表中的内容
        BaseAsset baseAsset = new BaseAsset(assetId, aicaVO.getType(), aicaVO.getTitle(), aicaVO.getDes(), TimeUtil.getLocalTime(), 0);

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
                aicaVO.getSex(), aicaVO.getDataOfBirth(), aicaVO.getIdCardNo(), aicaVO.getLengthOfSchooling(), aicaVO.getCollege(), aicaVO.getAddress(),
                aicaVO.getSchoolTime(), aicaVO.getTimeOfIssume());

        //解析所有的电子文件
        String filesUrls = aicaVO.getFiles();
        //尝试对fileUrls进行切分
        String[] urlArr = filesUrls.split(";");
        for(String url: urlArr) {
            AssetFiles af = new AssetFiles(IdUtil.getId(), assetId, url);
            assetFileList.add(af);
        }

        //把解析出来的这4个表的内容加入到Map中
        dataMap.put("baseAsset", baseAsset);
        dataMap.put("assetUserList", assetUserList);
        dataMap.put("studentIdCardAsset", aica);
        dataMap.put("assetFilesList", assetFileList);

        return dataMap;
    }

}
