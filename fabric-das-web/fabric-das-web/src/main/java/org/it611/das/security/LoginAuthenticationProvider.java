package org.it611.das.security;


import org.it611.das.domain.SysUser;
import org.it611.das.mapper.SysUserMapper;
import org.it611.das.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.Collection;

@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SysUserMapper userService;

    /**
     * 自定义验证方式
     */
    @Override
    public Authentication authenticate(Authentication authentication) {
       // String username = authentication.getName();
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        SysUser user = (SysUser) userService.findByUsername(username);
        if(user == null){
            throw new BadCredentialsException("Username not found.");
        }

        //自定义登陆过程
        if (!MD5Util.verify(password,user.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

}