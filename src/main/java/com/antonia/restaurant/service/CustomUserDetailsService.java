package com.antonia.restaurant.service;

import com.antonia.restaurant.entity.User;
import com.antonia.restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/*
 * CustomUserDetailsService: used by Spring Security to load user details
 */


/** Clasa pentru  a încărca informațiile despre utilizatorul autenticat pe baza adresei de email. Este implementarea personalizată a interfeței UserDetailsService, care este folosită pentru autentificarea utilizatorilor.
 * @author Andrei Antonia-Stefania
 * @version 6 Ianuarie 2025
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // This method is used by Spring Security for authentication
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Build authorities based on user role
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole()));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                authorities);
    }
}
