package com.antonia.restaurant.repository;

import com.antonia.restaurant.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import com.antonia.restaurant.entity.User;
import java.util.List;

/** Interfața ReservationRepository este un repository care extinde JpaRepository și este utilizată pentru a gestiona operațiunile de acces la date pentru entitatea Reservation.
 * Aceasta include atât metode implicite moștenite din JpaRepository, cât și o metodă personalizată pentru a găsi rezervările asociate unui anumit utilizator (User).
 * @author Andrei Antonia-Stefania
 * @version 12 Ianuarie 2025
 */

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(User user);
}
