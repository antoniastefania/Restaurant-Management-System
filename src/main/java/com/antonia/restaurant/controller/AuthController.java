package com.antonia.restaurant.controller;

import com.antonia.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.antonia.restaurant.entity.User;

import javax.validation.Valid;
/** Clasa pentru interacționa cu datele utilizatorilor și pentru a efectua acțiuni legate de autentificare și înregistrare.
 * @author Andrei Antonia-Stefania
 * @version 3 Ianuarie 2025
 */

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; //  login.html
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") @Valid User user,
                                  BindingResult result,
                                  Model model) {
        // Validate email format and password complexity .
        if (result.hasErrors()) {
            return "register";
        }

        // Check if the email already exists.
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            result.rejectValue("email", null, "Email is already taken");
            return "register";
        }

        // Register user.
        userService.registerUser(user);
        return "redirect:/login?registered";
    }

    @GetMapping("/logout")
    public String logout() {
        // Spring Security will handle logout automatically, here we only perform page redirection
        return "redirect:/login?logout";
    }
}
