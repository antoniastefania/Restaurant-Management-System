package com.antonia.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.antonia.restaurant.entity.User;
import java.util.Optional;

/**Interfața UserRepository este un repository care extinde JpaRepository și este utilizată pentru a efectua operațiuni de acces la date pentru entitatea User.
 * Aceasta include atât metode implicite moștenite din JpaRepository, cât și o metodă personalizată pentru a căuta un utilizator după email.
 * @author Andrei Antonia-Stefania
 * @version 4 Ianuarie 2025
 */

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
