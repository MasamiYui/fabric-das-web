package org.it611.das.util;

import org.it611.das.domain.*;
import org.it611.das.vo.CompanyVO;
import org.it611.das.vo.UserVo;

public class Vo2PoUtil {

    public static User UserVo2Po(UserVo userVo){

        User user = new User(userVo.getId(), userVo.getName(), userVo.getSex(), userVo.getNation(),
                userVo.getDate(), userVo.getAddress(), TimeUtil.getLocalTime(), 1, userVo.getFiles());
        return user;
    }


    public static Company CompanyVo2Po(CompanyVO companyVO) {
        Company company = new Company(companyVO.getId(), companyVO.getName(), companyVO.getType(), companyVO.getAddress(), companyVO.getLegalRepresentative(),
                companyVO.getRegisteredCapital(), companyVO.getStartTime(), companyVO.getEndTime(), companyVO.getScopeOfBusiness(),companyVO.getRegistrationAuthority(), companyVO.getAuditTime(),
                TimeUtil.getLocalTime(), 1);
        return company;
    }

}
