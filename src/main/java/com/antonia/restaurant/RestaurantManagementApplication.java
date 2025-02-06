package com.antonia.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/** Clasa principala
 * @author Andrei Antonia-Stefania
 * @version 12 Ianuarie 2025
 */


@SpringBootApplication
public class RestaurantManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantManagementApplication.class, args);
    }

    // For password encryption
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
