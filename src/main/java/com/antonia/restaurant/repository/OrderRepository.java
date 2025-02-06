package com.antonia.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.antonia.restaurant.entity.Order;
import com.antonia.restaurant.entity.User;
import java.util.List;

/** Interfața OrderRepository este un repository care extinde JpaRepository și este utilizată pentru a gestiona operațiunile de acces la date pentru entitatea Order.
 *  Aceasta conține atât metodele standard oferite de Spring Data JPA, cât și o metodă personalizată definită pentru a găsi comenzile asociate unui anumit utilizator (User).
 * @author Andrei Antonia-Stefania
 * @version 4 Ianuarie 2025
 */


public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
