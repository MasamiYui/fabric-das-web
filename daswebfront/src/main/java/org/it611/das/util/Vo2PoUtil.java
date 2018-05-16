package org.it611.das.util;


import org.it611.das.domain.Company;
import org.it611.das.domain.User;
import org.it611.das.vo.CompanyVO;
import org.it611.das.vo.UserVO;

public final class Vo2PoUtil {

    public static User userVo2Po(UserVO userVo){

        return new User(IdUtil.getId(), userVo.getPhone(), userVo.getEmail(), MD5Util.generate(userVo.getPassword()), userVo.getIdcard(), userVo.getName(),
                userVo.getSex(), userVo.getNation(), userVo.getDate(), userVo.getAddress(), userVo.getFiles(), 0, TimeUtil.getLocalTime());

    }

    public static Company ConpanyVo2Po(CompanyVO companyVO) {
        return new Company(IdUtil.getId(), companyVO.getUsername(),MD5Util.generate(companyVO.getPassword()), companyVO.getCompanyName(), companyVO.getCompanyAddress(),
                companyVO.getCompanyEmail(), companyVO.getLandinePhone(), companyVO.getContact(), companyVO.getPhone(), companyVO.getCreditId(),
                companyVO.getCompanyType(), companyVO.getRepresentative(), companyVO.getEstablishmentTime(), companyVO.getStartTime(), companyVO.getEndTime(),
                companyVO.getBusinessScope(), companyVO.getRegistrationAuthority(), companyVO.getRegistrationTime(), companyVO.getFiles(), 0, TimeUtil.getLocalTime());
    }

}
