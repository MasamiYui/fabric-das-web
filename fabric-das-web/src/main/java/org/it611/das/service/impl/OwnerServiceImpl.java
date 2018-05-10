package org.it611.das.service.impl;

import com.github.pagehelper.PageHelper;
import org.apache.log4j.Logger;
import org.it611.das.domain.User;
import org.it611.das.mapper.CompanyMapper;
import org.it611.das.mapper.UserMapper;
import org.it611.das.service.OwnerService;
import org.it611.das.util.State;
import org.it611.das.util.Vo2PoUtil;
import org.it611.das.vo.CompanyVO;
import org.it611.das.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;


@Service
public class OwnerServiceImpl implements OwnerService {

    private static Logger logger = Logger.getLogger(OwnerServiceImpl.class);

    @Autowired
    private UserMapper userDao;

    @Autowired
    private CompanyMapper companyDao;

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
        if (userDao.insertUser(Vo2PoUtil.UserVo2Po(userVo))>0) {
            return State.SUCCESS;
        }
        return State.FALSE;
    }

    @Transactional
    @Override
    public int deleteUser(String id) {

        if (userDao.deleteUserById(id)>0) {
            return State.SUCCESS;
        }
        return State.FALSE;
    }

    @Override
    public HashMap<String, Object> selectCompanies(int pageNum, int pageSize, String name, String id) {

        HashMap dataMap = new HashMap<String, Object>();
        PageHelper.startPage(pageNum, pageSize);
        List<HashMap> resultData = companyDao.selectCompaniesByNameAndId(name, id);
        long total = companyDao.selectTotal(name, id);
        dataMap.put("rows", resultData);
        dataMap.put("total", total);
        return dataMap;
    }

    @Transactional
    @Override
    public int addComany(CompanyVO companyVO) {

        if(companyDao.insertCompany(Vo2PoUtil.CompanyVo2Po(companyVO))>0) {
          return State.SUCCESS;
        }
        return State.FALSE;
    }

    @Transactional
    @Override
    public List<HashMap> getRecordDetail(String id) {
       List<HashMap>  result = userDao.getRecordDetail(id);
        return result;
    }
}

