package com.everis.d4i.tutorial.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.everis.d4i.tutorial.exceptions.SimpleAccessDeniedHandler;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
    protected void configure(HttpSecurity http) throws Exception 
    {
        http
         .csrf().disable()
         .authorizeRequests()
         .antMatchers(HttpMethod.POST).hasRole("ADMIN")
         .antMatchers(HttpMethod.PATCH).hasRole("ADMIN")
         .antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
         .antMatchers(HttpMethod.GET).hasRole("USER")
         .anyRequest().authenticated()
         .and()
         .httpBasic()
         .and()
         .exceptionHandling()
         //403
         //.authenticationEntryPoint(new AuthenticationExceptionHandler())
         //401
         .accessDeniedHandler(new SimpleAccessDeniedHandler());
        
    }
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) 
            throws Exception 
    {
        auth.inMemoryAuthentication()
            .withUser("user").password("{noop}password").roles("USER")
            .and()
            .withUser("admin").password("{noop}password").roles("USER", "ADMIN");
    }
	
}
