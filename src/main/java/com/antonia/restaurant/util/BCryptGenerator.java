package com.antonia.restaurant.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



/** Clasa pentru generarea parolelor criptate folosind BCryptPasswordEncoder. Clasa folosește algoritmul de criptare BCrypt pentru a cripta o parolă simplă și o afișează pe ecran.
 * @author Andrei Antonia-Stefania
 * @version 12 Ianuarie 2025
 */


public class BCryptGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "123A*";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println(encodedPassword);
    }
}
