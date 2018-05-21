package org.it611.das.mapper;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface DegreeCertificationMapper {

    //条件查询(以后可增加其他查询条件)
    List<HashMap> selectDegreeCertificationByConditions(String certId);

    //查询记录数
    Long selectTotal(String certId);


}
