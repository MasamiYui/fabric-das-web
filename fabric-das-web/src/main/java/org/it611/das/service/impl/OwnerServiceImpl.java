package org.it611.das.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.apache.log4j.Logger;
import org.it611.das.domain.Company;
import org.it611.das.domain.OwnerFile;
import org.it611.das.domain.User;
import org.it611.das.mapper.CompanyMapper;
import org.it611.das.mapper.DegreeCertificationMapper;
import org.it611.das.mapper.OwnerFileMapper;
import org.it611.das.mapper.UserMapper;
import org.it611.das.service.OwnerService;
import org.it611.das.util.ResponseUtil;
import org.it611.das.util.State;
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

    @Autowired
    private DegreeCertificationMapper degreeCertificationMapper;

    //pageNum:页码  pageSize:记录数
    @Transactional
    @Override
    public HashMap<String, Object> selectUsers(int pageNum, int pageSize, String name, String idcard,String state) {

        HashMap data = new HashMap<String, Object>();
        PageHelper.startPage(pageNum, pageSize);
        List<HashMap> resultData = userDao.selectUsersByNameAndIdCard(name, idcard,state);
        long total = userDao.selectTotal(name, idcard,state);
        data.put("rows", resultData);
        data.put("total", total);
        return data;
    }

    @Transactional
    @Override
    public int addUser(UserVo userVo) {
        Map<String, Object> data = parseInputFileForm.parseUserFileForm(userVo);
        User user = (User) data.get("user");

        int r2 = -1;
        if (data.containsKey("ownerFileList")) {
            ArrayList<OwnerFile> ownerFileList = (ArrayList<OwnerFile>) data.get("ownerFileList");
            r2 = ownerFileMapper.insertOwnerFile(ownerFileList);
        }

        int r1 = userDao.insertUser(user);

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
    public int deleteUser(String id) {

        if (userDao.deleteUserById(id) > 0) {
            return State.SUCCESS;
        }
        return State.FALSE;
    }

    @Override
    public HashMap<String, Object> selectCompanies(int pageNum, int pageSize, String username, String companyName, String creditId, String state) {

        HashMap dataMap = new HashMap<String, Object>();
        PageHelper.startPage(pageNum, pageSize);
        List<HashMap> resultData = companyDao.selectCompaniesByCondtions(username, companyName, creditId, Integer.valueOf(state));
        long total = companyDao.selectTotal(username, companyName, creditId, Integer.valueOf(state));
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

    @Transactional
    @Override
    public int changeCompanyState(String id, String state) {
        int result = companyDao.changCompanyState(id, state);
        if (result > 0) {
            return result;
        }
        return 0;
    }

    @Transactional
    @Override
    public int changeUserState(String id, String state) {
        int result = userDao.changUserState(id, state);
        if (result > 0) {
            return result;
        }
        return 0;
    }

    @Override
    public JSONObject degreeCertificationList(int pageNum, int pageSize, String creditId) {

        Map<String, Object> data = new HashMap<>();
        PageHelper.startPage(pageNum, pageSize);
        List<HashMap> resultData = degreeCertificationMapper.selectDegreeCertificationByConditions(creditId);
        long total = degreeCertificationMapper.selectTotal(creditId);
        data.put("rows", resultData);
        data.put("total", total);
        if (resultData != null) {
            return ResponseUtil.constructResponse(200, "ok", data);
        }
        return ResponseUtil.constructResponse(400, "select failed.", null);

    }


    @Override
    public HashMap queryAllUsers(int currentPage, int numberOfPages, String searchString) {

        HashMap dataMap = new HashMap<String, Object>();
        PageHelper.startPage(currentPage, numberOfPages);
        List<HashMap> result = new ArrayList<HashMap>();
        List<HashMap> CallBackResult = new ArrayList<HashMap>();
        Long resultTotal;


        //企业用户类型为0
        List<HashMap> resultCompanyUsers = companyDao.queryCompanyUsers(searchString);
        long totalCompanyUsers = companyDao.selectCompanyUsersTotal(searchString);
        HashMap tmp = new HashMap();
        for (int i = 0; i < resultCompanyUsers.size(); i++) {
            resultCompanyUsers.get(i).put("userType", "0");
        }

        //普通用户类型为1
        List<HashMap> resultCommonUsers = userDao.queryCommonUsers(searchString);
        long totalCommonUsers = userDao.selectCommonUsersTotal(searchString);
        for (int i = 0; i < resultCommonUsers.size(); i++) {
            resultCommonUsers.get(i).put("userType", "1");
        }

        result.addAll(resultCommonUsers);
        result.addAll(resultCompanyUsers);
        resultTotal = totalCommonUsers + totalCompanyUsers;

        //截取返回数据
        int resultSize = result.size()-1;
        int start=(currentPage - 1) * numberOfPages;
        for(int i=0;i<numberOfPages;i++){
            if((start+i)>resultSize) break;
            CallBackResult.add(result.get(start+i));
        }

        dataMap.put("rows", CallBackResult);
        dataMap.put("total", resultTotal);
        return dataMap;
    }
}

