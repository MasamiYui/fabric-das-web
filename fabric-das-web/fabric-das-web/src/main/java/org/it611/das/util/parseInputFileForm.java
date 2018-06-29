package org.it611.das.util;

import org.it611.das.domain.*;
import org.it611.das.domain.fabric.FabricStudentIdCardAsset;
import org.it611.das.vo.CompanyVO;
import org.it611.das.vo.UserVo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class parseInputFileForm {
    public static Map<String, Object> parseCompanyFileFomr(CompanyVO companyVO) {

        Map<String, Object> dataMap = new HashMap<>();
        ArrayList<OwnerFile> ownerFileList = new ArrayList<>();

        Company company = new Company(companyVO.getId(), companyVO.getName(), companyVO.getType(), companyVO.getAddress(), companyVO.getLegalRepresentative(),
                companyVO.getRegisteredCapital(), companyVO.getStartTime(), companyVO.getEndTime(), companyVO.getScopeOfBusiness(), companyVO.getRegistrationAuthority(), companyVO.getAuditTime(),
                TimeUtil.getLocalTime(), 1);

        String owner_id = companyVO.getId();
        String filePaths = companyVO.getFiles();
        if (filePaths != null && filePaths != "") {
            String[] pathArr = filePaths.split(",");
            for (String path : pathArr) {
                OwnerFile ownerFile = new OwnerFile(IdUtil.getId(), owner_id, path);
                ownerFileList.add(ownerFile);
            }
            dataMap.put("ownerFileList", ownerFileList);
        }

        dataMap.put("company", company);

        return dataMap;
    }


    public static Map<String, Object> parseUserFileForm(UserVo userVo) {

        Map<String, Object> dataMap = new HashMap<>();
        ArrayList<OwnerFile> ownerFileList = new ArrayList<>();

        User user = new User(userVo.getId(), userVo.getName(), userVo.getSex(), userVo.getNation(),
                userVo.getDate(), userVo.getAddress(), TimeUtil.getLocalTime(), 1);

        String owner_id = userVo.getId();
        String filePaths = userVo.getFiles();
        if (filePaths != null && filePaths != "") {
            String[] pathArr = filePaths.split(",");
            for (String path : pathArr) {
                OwnerFile ownerFile = new OwnerFile(IdUtil.getId(), owner_id, path);
                ownerFileList.add(ownerFile);
            }
            dataMap.put("ownerFileList", ownerFileList);
        }

        dataMap.put("user", user);

        return dataMap;
    }


}
