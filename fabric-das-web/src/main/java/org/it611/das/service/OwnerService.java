package org.it611.das.service;


import org.it611.das.vo.UserVo;

import java.util.HashMap;
import java.util.List;

public interface OwnerService {

    //查询所有用户
    List<HashMap> selectUsers(int pageNum, int pageSize);

    //添加用户
    int addUser(UserVo userVo);

    //查询用户通过名字
    List<HashMap> selectUsersByName(int pageNum, int pageSize, String name);

    //查询用户通过id
    List<HashMap> selectUsersById(int pageNum, int pageSize, String id);

    //查询用户通过名字和id
    List<HashMap> selectUsersByNameAndId(int pageNum, int pageSize, String name, String id);

}
