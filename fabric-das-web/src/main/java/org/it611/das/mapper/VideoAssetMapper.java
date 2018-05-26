package org.it611.das.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface VideoAssetMapper {

    //条件查询(以后可增加其他查询条件)
    List<HashMap> selectVideoAssetByConditions(@Param("title") String title);

    //查询记录数
    Long selectTotal(@Param("title") String title);

    HashMap<String, Object> selectVideoAssetDetailById(@Param("id") String id);

    //根据主键修改transaction和state
    int updateStateAndTransaction(@Param("id") String id, @Param("transactionId") String transactionId, @Param("state") String state);//id主键

    //根据主键修改state
    int updateState(@Param("id") String id, @Param("state") String state);//id主键

    //根据主键id查询记录的state
    String selectStateById(String id);
}
