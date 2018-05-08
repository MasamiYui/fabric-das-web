package org.it611.das.service;


import org.it611.das.vo.UserVo;

import java.util.HashMap;
import java.util.List;

public interface OwnerService {

    //查询所有用户
    HashMap<String, Object> selectUsers(int pageNum, int pageSize,String name,String id);

    //添加用户
    int addUser(UserVo userVo);


}