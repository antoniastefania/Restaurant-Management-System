package com.antonia.restaurant.service;

import com.antonia.restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.antonia.restaurant.entity.User;

import java.util.*;
/** Clasa gestionează logica de afaceri legată de utilizatori, inclusiv crearea unui nou utilizator, căutarea utilizatorilor, activarea/dezactivarea unui utilizator și gestionarea rolurilor acestora. Este o componentă esențială într-o aplicație care include autentificare și autorizare a utilizatorilor.
 * @author Andrei Antonia-Stefania
 * @version 12 Ianuarie 2025
 */


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Register a new user (with "USER" role by default)
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        user.setEnabled(true);
        return userRepository.save(user);
    }

    // Find user by email
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Find user by id
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // List all users (for admin)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Enable or disable user
    public void enableUser(Long userId, boolean enabled) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setEnabled(enabled);
            userRepository.save(user);
        }
    }
}

