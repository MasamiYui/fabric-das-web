package org.it611.das.mapper;

import org.it611.das.domain.Company;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public interface CompanyMapper {

    int addCompany(Company company);

    HashMap<String, Object> findCompanyByCreditId(String creditId);

    HashMap<String, Object> findCompanyByUsername(String username);

    HashMap<String, Object> findCompanyByPhone(String phone);

    HashMap<String, Object> findCompanyByEmail(String email);
}
