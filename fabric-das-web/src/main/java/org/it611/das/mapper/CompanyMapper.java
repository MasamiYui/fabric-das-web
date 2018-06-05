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

    //查询
    List<HashMap> selectCompaniesByCondtions(@Param("username") String username, @Param("companyName") String companyName, @Param("creditId") String creditId, @Param("state") Integer state);

    //查询用户总数
    Long selectTotal(@Param("username") String username, @Param("companyName") String companyName, @Param("creditId") String creditId, @Param("state") Integer state);

    //根据id查询企业记录详情
    List<HashMap> selectComanyDetailById(String id);

    //修改企业用户状态
    int changCompanyState(@Param("id") String id, @Param("state") String state);


    //根据用户名查找企业用户详情
    List<HashMap> queryCompanyUsers(@Param("searchString") String searchString);

    //根据用户名查找企业用户数量
    long  selectCompanyUsersTotal(@Param("searchString")String searchString);


}
