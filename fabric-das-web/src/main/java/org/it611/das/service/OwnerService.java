package org.it611.das.service;


import org.it611.das.vo.UserVo;

import java.util.HashMap;
import java.util.List;

public interface OwnerService {

    List<HashMap> selectUsers(int pageNum, int pageSize);

    int addUser(UserVo userVo);

}
