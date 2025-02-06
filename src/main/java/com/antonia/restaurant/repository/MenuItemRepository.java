package com.antonia.restaurant.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import com.antonia.restaurant.entity.MenuItem;

/** Clasa pentru pentru a permite manipularea entității MenuItem.
 *  Ea definește metode pentru interacționarea cu baza de date în mod eficient, inclusiv căutări paginabile.
 * @author Andrei Antonia-Stefania
 * @version 4 Ianuarie 2025
 */

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    Page<MenuItem> findByCategoryIgnoreCase(String category, Pageable pageable);

    Page<MenuItem> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);

    @Query("SELECT m FROM MenuItem m " +
            "WHERE lower(m.name) LIKE lower(concat('%', :keyword, '%')) " +
            "   OR lower(m.description) LIKE lower(concat('%', :keyword, '%'))")
    Page<MenuItem> searchByNameOrDescription(@Param("keyword") String keyword, Pageable pageable);
}
