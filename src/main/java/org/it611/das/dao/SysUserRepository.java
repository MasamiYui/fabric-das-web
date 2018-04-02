package org.it611.das.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.it611.das.domain.SysUser;

public interface SysUserRepository extends JpaRepository<SysUser, Long>{
	
	SysUser findByUsername(String username);

}
