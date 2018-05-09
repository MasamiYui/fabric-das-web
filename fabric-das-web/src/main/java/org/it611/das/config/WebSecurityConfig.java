package org.it611.das.config;

import org.it611.das.security.LoginAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.it611.das.security.CustomUserService;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private LoginAuthenticationProvider provider;//自定义验证
	
	@Bean
	UserDetailsService customUserService(){
		return new CustomUserService(); 
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(provider);
		//auth.userDetailsService(customUserService());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//防止iframe无法显示
		http.headers().frameOptions().disable();
		http.authorizeRequests()
						//.antMatchers("/static/**","/css/**","/image/*").permitAll()
                        .antMatchers("**").permitAll()//开发环境下，允许所有请求
                        .anyRequest().authenticated()
						.and()
						.formLogin()
							.loginPage("/login")
							.failureUrl("/login?error")
							.permitAll()
						.and()
						.logout().permitAll();
        http.csrf().disable();
	}
}
