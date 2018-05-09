package org.it611.das.service;


import org.it611.das.vo.CompanyVO;
import org.it611.das.vo.UserVo;

import java.util.HashMap;
import java.util.List;

public interface OwnerService {

    //查询用户
    HashMap<String, Object> selectUsers(int pageNum, int pageSize,String name,String id);

    //添加用户
    int addUser(UserVo userVo);

    //删除用户
    int deleteUser(String id);

    //查询公司
    HashMap<String, Object> selectCompanies(int pageNum, int pageSize,String name,String id);

    //添加公司
    int addComany(CompanyVO companyVO);


}
