package com.cfg.shop.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;


/**
 * Created by Administrator on 2017/7/2.
 */
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  RESTAuthenticationEntryPoint restAuthenticationEntryPoint;

  @Autowired
  RESTAuthenticationSuccessHandler restAuthenticationSuccessHandler;

  @Autowired
  RESTAuthenticationFailureHandler restAuthenticationFailureHandler;

  @Autowired
  UserDetailsService userDetailsService;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/**").permitAll()
        .anyRequest().authenticated();
    http.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint);
    http.csrf().disable();
    http.formLogin()
        .successHandler(restAuthenticationSuccessHandler)
        .failureHandler(restAuthenticationFailureHandler)
        .permitAll();
  }


}