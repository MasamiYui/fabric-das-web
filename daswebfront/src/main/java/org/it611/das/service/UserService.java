package org.it611.das.service;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.it611.das.vo.CompanyVO;
import org.it611.das.vo.UserVO;

public interface UserService {

    JSONObject userLogin(String loginStr, String password) throws JsonProcessingException;

    JSONObject userRegister(UserVO userVO);

    JSONObject companyLogin(String loginStr, String password) throws JsonProcessingException;

    JSONObject companyRegister(CompanyVO companyVO);

}
