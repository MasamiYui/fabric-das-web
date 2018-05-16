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
    List<HashMap> selectCompaniesByNameAndId(@Param("companyName") String companyName, @Param("creditId") String creditId, @Param("companyAddress") String companyAddress, @Param("representative") String legalRepresentative);

    //查询用户总数
    Long selectTotal(@Param("companyName") String companyName, @Param("creditId") String creditId, @Param("companyAddress") String companyAddress, @Param("representative") String representative);

    //根据id查询企业记录详情
    List<HashMap> selectComanyDetailById(String id);

}
