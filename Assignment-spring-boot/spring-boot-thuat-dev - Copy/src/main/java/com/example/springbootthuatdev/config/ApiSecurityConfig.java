package com.example.springbootthuatdev.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // them filter cho login
        ApiAuthenticationFilter apiAuthenticationFilter = new ApiAuthenticationFilter(authenticationManagerBean());
        apiAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
        http.cors().and().csrf().disable();
        // tha cac api dang ki, dang nhap danh cho guest
        http.authorizeRequests()
                .antMatchers("/api/v1/products",
                        "/api/v1/login",
                        "/api/v1/register",
                        "/api/v1/accounts/login",
                        "/api/v1/accounts/register")
                .permitAll();
        http.authorizeRequests()
                .antMatchers("/api/v1/users").hasAnyAuthority("user");
        http.authorizeRequests()
                .antMatchers("/api/v1/admins").hasAnyAuthority("admin");
        http.addFilter(apiAuthenticationFilter);
        http.addFilterBefore(new ApiAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
