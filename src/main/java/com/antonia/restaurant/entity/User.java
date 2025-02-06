package com.antonia.restaurant.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.antonia.restaurant.validator.ValidPassword;
import lombok.*;

/** Clasa reprezintă utilizatorii din baza de date a aplicației.
 *  Aceasta este utilizată pentru maparea unui tabel din baza de date (în acest caz, tabelul se numește users) și pentru a defini structura și comportamentul datelor despre utilizatori.
 * @author Andrei Antonia-Stefania
 * @version 3 Ianuarie 2025
 */



@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    @Column(nullable = false, unique = true)
    private String email;

    @ValidPassword
    @Column(nullable = false)
    private String password;

    private String fullName;

    @Column(nullable = false)
    private String role;  // "USER" or "ADMIN"

    @Column(nullable = false)
    private boolean enabled = true;
}
