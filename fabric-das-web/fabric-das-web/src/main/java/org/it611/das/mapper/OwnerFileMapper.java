package org.it611.das.mapper;

import org.apache.ibatis.annotations.Param;
import org.it611.das.domain.OwnerFile;
import org.it611.das.domain.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface OwnerFileMapper {

    //新增
    int insertOwnerFile(@Param("fileList") List<OwnerFile>  ownerFileList);

    //根据id查询记录(对应多个文件)
    List<HashMap> getOwnerFile(@Param("owner_id") String id);

}
