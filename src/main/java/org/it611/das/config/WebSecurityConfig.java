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

		http.authorizeRequests()
						.anyRequest().authenticated()
						.and()
						.formLogin()
							.loginPage("/login")
							.failureUrl("/login?error")
							.permitAll() //5
						.and()
						.logout().permitAll();
	}

/*	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//将验证过程交给自定义验证工具
		auth.authenticationProvider(provider);
	}*/
}
