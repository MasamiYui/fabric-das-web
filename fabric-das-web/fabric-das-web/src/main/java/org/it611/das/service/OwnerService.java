package org.it611.das.service;


import com.alibaba.fastjson.JSONObject;
import org.it611.das.vo.CompanyVO;
import org.it611.das.vo.UserVo;

import java.util.HashMap;
import java.util.List;

public interface OwnerService {

    //查询用户
    HashMap<String, Object> selectUsers(int pageNum, int pageSize,String name,String idcard,String state);

    //添加用户
    int addUser(UserVo userVo);

    //删除用户
    int deleteUser(String id);

    //查询公司
    HashMap<String, Object> selectCompanies(int pageNum, int pageSize,String name,String id,String address,String legalRepresentative);

    //添加公司
    int addComany(CompanyVO companyVO);

    //根据id查询记录
    List<HashMap> getRecordDetail(String id);

    //根据id查询公司记录
    List<HashMap> getCompanyDetail(String id);

    //更改普通用户帐号的状态
    int changeUserState(String id,String state);

    //更改企业用户帐号的状态
    int changeCompanyState(String id,String state);

    //查询学位资产的列表
    JSONObject degreeCertificationList(int currentPage, int numberOfPages, String creditId);


    //查询所有用户信息（普通用户和企业用户）
    HashMap queryAllUsers(int currentPage, int numberOfPages, String searchString);

}
