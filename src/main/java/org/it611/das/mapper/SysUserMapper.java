package org.it611.das.mapper;

import org.it611.das.domain.SysUser;
import org.springframework.stereotype.Component;


@Component
public interface SysUserMapper {

    SysUser findByUsername(String userId);

}