package org.it611.das.mapper;

import org.it611.das.domain.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface UserMapper {

    //查询用户列表
    List<HashMap> selectUsers();

    //新增一名用户
    int insertUser(User user);

    //查询用户通过姓名
    List<HashMap> selectUsersByName(String name);

    //查询用户通过身份证
    List<HashMap> selectUsersById(String id);


}
