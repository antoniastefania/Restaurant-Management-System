package com.antonia.restaurant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.antonia.restaurant.service.CustomUserDetailsService;


/** Clasa pentru configurarea securității aplicației web utilizând Spring Security.
 *  Aceasta definește cum utilizatorii sunt autentificați, ce permisiuni au și cum sunt gestionate rutele aplicației.
 * @author Andrei Antonia-Stefania
 * @version 2 Ianuarie 2025
 */




@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          BCryptPasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Use our custom user detail service + bcrypt
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // Public pages
                .antMatchers(
                        "/",
                        "/index",
                        "/register",
                        "/login",
                        "/static/**",
                        "/css/**",
                        "/js/**",
                        "/images/**"
                ).permitAll()
                // Admin pages
                .antMatchers("/admin/**").permitAll()
                // User pages
                .antMatchers("/menu/**", "/cart/**", "/orders/**", "/reservation/**").hasAnyAuthority("USER","ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
