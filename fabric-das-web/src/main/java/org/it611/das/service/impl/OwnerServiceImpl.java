package org.it611.das.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.apache.log4j.Logger;
import org.it611.das.domain.Company;
import org.it611.das.domain.OwnerFile;
import org.it611.das.domain.User;
import org.it611.das.mapper.CompanyMapper;
import org.it611.das.mapper.OwnerFileMapper;
import org.it611.das.mapper.UserMapper;
import org.it611.das.service.OwnerService;
import org.it611.das.util.ResponseUtil;
import org.it611.das.util.State;
import org.it611.das.util.Vo2PoUtil;
import org.it611.das.util.parseInputFileForm;
import org.it611.das.vo.CompanyVO;
import org.it611.das.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class OwnerServiceImpl implements OwnerService {

    private static Logger logger = Logger.getLogger(OwnerServiceImpl.class);

    @Autowired
    private UserMapper userDao;

    @Autowired
    private CompanyMapper companyDao;

    @Autowired
    private OwnerFileMapper ownerFileMapper;

    //pageNum:页码  pageSize:记录数
    @Transactional
    @Override
    public HashMap<String, Object> selectUsers(int pageNum, int pageSize, String name, String id) {

        HashMap data = new HashMap<String, Object>();
        PageHelper.startPage(pageNum, pageSize);
        List<HashMap> resultData = userDao.selectUsersByNameAndId(name, id);
        long total = userDao.selectTotal(name, id);
        data.put("rows", resultData);
        data.put("total", total);
        return data;
    }

    @Transactional
    @Override
    public int addUser(UserVo userVo) {
        if (userDao.insertUser(Vo2PoUtil.UserVo2Po(userVo)) > 0) {
            return State.SUCCESS;
        }
        return State.FALSE;
    }

    @Transactional
    @Override
    public int deleteUser(String id) {

        if (userDao.deleteUserById(id) > 0) {
            return State.SUCCESS;
        }
        return State.FALSE;
    }

    @Override
    public HashMap<String, Object> selectCompanies(int pageNum, int pageSize, String name, String id, String address, String legalRepresentative) {

        HashMap dataMap = new HashMap<String, Object>();
        PageHelper.startPage(pageNum, pageSize);
        List<HashMap> resultData = companyDao.selectCompaniesByNameAndId(name, id, address, legalRepresentative);
        long total = companyDao.selectTotal(name, id, address, legalRepresentative);
        dataMap.put("rows", resultData);
        dataMap.put("total", total);
        return dataMap;
    }

    @Transactional
    @Override
    public int addComany(CompanyVO companyVO) {

        Map<String, Object> data = parseInputFileForm.parseCompanyFileFomr(companyVO);
        Company company = (Company) data.get("company");

        int r2 = -1;
        if (data.containsKey("ownerFileList")) {
            ArrayList<OwnerFile> ownerFileList = (ArrayList<OwnerFile>) data.get("ownerFileList");
            r2 = ownerFileMapper.insertOwnerFile(ownerFileList);
        }

        int r1 = companyDao.insertCompany(company);

        if (r2 != -1) {
            if (r1 > 0 && r2 > 0) {
                return State.SUCCESS;
            }
        } else {
            if (r1 > 0) {
                return State.SUCCESS;
            }
        }

        return State.FALSE;

    }

    @Transactional
    @Override
    public List<HashMap> getCompanyDetail(String id) {
        List<HashMap> result = companyDao.selectComanyDetailById(id);
        return result;
    }

    @Transactional
    @Override
    public List<HashMap> getRecordDetail(String id) {
        List<HashMap> result = userDao.getRecordDetail(id);
        return result;
    }
}

