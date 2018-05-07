package org.it611.das.service.impl;

import com.github.pagehelper.PageHelper;
import org.apache.log4j.Logger;
import org.it611.das.domain.User;
import org.it611.das.mapper.UserMapper;
import org.it611.das.service.OwnerService;
import org.it611.das.util.State;
import org.it611.das.util.Vo2PoUtil;
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


    @Transactional
    @Override
    public List<HashMap> selectUsers(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<HashMap> resultData = userDao.selectUsers();
        logger.info("inert data to mysql:"+resultData.toString());
        return resultData;
    }

    @Transactional
    @Override
    public int addUser(UserVo userVo) {
        User user = Vo2PoUtil.UserVo2Po(userVo);
        int result = userDao.insertUser(user);
        if(result >0) {
            return State.SUCCESS;
        }
        return State.FALSE;
    }
}
