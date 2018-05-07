package org.it611.das.security;

import org.it611.das.domain.SysUser;
import org.it611.das.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class CustomUserService implements UserDetailsService {

	@Autowired
	SysUserMapper sysUserDao;

	@Override
	public UserDetails loadUserByUsername(String username) {

		/**
		 * 身份查询
		 */
		SysUser user = sysUserDao.findByUsername(username);

		if(user == null){
			throw new UsernameNotFoundException("用户名不存在");
		}
		return user;
	}
}
