package org.it611.das.mapper;

import org.apache.ibatis.annotations.Param;
import org.it611.das.domain.User;
import org.it611.das.vo.UserVo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface UserMapper {

    //查询用户列表
    List<HashMap> selectUsers();

    //新增一名用户
    int insertUser(User user);

    //查询用户通过姓名和身份证
    List<HashMap> selectUsersByNameAndIdCard(@Param("name")String name, @Param("idcard")String idcard);

    //查询用户总数
    Long selectTotal(@Param("name")String name, @Param("idcard")String id);

    //删除用户通过Id
    Integer deleteUserById(@Param("id")String id);

    //根据id查询记录(对应多个文件)
    List<HashMap> getRecordDetail(@Param("id")String id);

    //根据id设置用户帐号的状态
    int changUserState(@Param("id") String id, @Param("state") String state);

    //根据用户名查找普通用户详情
    List<HashMap> queryCommonUsers(@Param("searchString")String searchString);

    //根据用户名查找用户数量
     long  selectCommonUsersTotal(@Param("searchString")String searchString);

}
