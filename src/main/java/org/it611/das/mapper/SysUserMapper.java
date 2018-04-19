package org.it611.das.mapper;

import org.it611.das.domain.SysUser;

import java.util.List;


public interface SysUserMapper {

    SysUser findByUsername(String userId);

}