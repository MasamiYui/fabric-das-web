package org.it611.das.mapper;

import org.apache.ibatis.annotations.Param;
import org.it611.das.domain.Company;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface CompanyMapper {

    //查询用户列表
    List<HashMap> selectCompanies();

    //新增一名用户
    int insertCompany(Company company);


    //查询用户通过姓名和身份证
    List<HashMap> selectCompaniesByNameAndId(@Param("name") String name, @Param("id") String id, @Param("address") String address, @Param("legalRepresentative") String legalRepresentative);

    //查询用户总数
    Long selectTotal(@Param("name") String name, @Param("id") String id, @Param("address") String address, @Param("legalRepresentative") String legalRepresentative);

    //根据id查询企业记录详情
    List<HashMap> selectComanyDetailById(String id);

}
