package com.icd.wksh.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true )
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
    @Autowired
    private UserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private CustomRequestFilter customRequestFilter;
    @Autowired
    private CustomAccessDenyHandler customAccessDenyHandler;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .cors().and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterAfter(customRequestFilter, UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//                .exceptionHandling().accessDeniedHandler(customAccessDenyHandler).and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/hello/**").permitAll()
                .antMatchers("/not-secure/**").permitAll()
                .antMatchers("/files/**").permitAll()
                .antMatchers("/users/**").permitAll()
//                .antMatchers("/books/**").permitAll()
                .antMatchers(
                        "/v2/api-docs",           // swagger
                        "/webjars/**",            // swagger-ui webjars
                        "/swagger-resources/**",  // swagger-ui resources
                        "/configuration/**",      // swagger configuration
                        "/*.html").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Use BCryptPasswordEncoder for match password
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
