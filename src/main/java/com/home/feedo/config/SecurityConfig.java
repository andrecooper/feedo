package com.home.feedo.config;


import com.home.feedo.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ADMIN = String.valueOf(Role.ADMIN);
    private static final String USER = String.valueOf(Role.USER);
    private static final String DEMO = String.valueOf(Role.DEMO);

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("demo").password("demo").roles(DEMO).and()
                .withUser("user").password("user").roles(USER).and()
                .withUser("admin").password("admin").roles(ADMIN);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/feed/**").access("hasAnyRole('" + USER + "','" + ADMIN + "','" + DEMO + "')")
                .antMatchers("/audit/**").access("hasRole('" + ADMIN + "')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/feed")
                .and()
                .httpBasic()
                .and().csrf().disable();
    }
}