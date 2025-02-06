package com.antonia.restaurant.repository;

import com.antonia.restaurant.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/** Clasa este folosită pentru a realiza operațiuni de acces la date (CRUD) pentru entitatea OrderDetail.
 *  Aceasta face parte din stratul de date al aplicației și simplifică interacțiunea cu baza de date pentru această entitate.
 * @author Andrei Antonia-Stefania
 * @version 12 Ianuarie 2025
 */

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    void deleteByMenuItemId(Long id);
}
