package com.antonia.restaurant.service;

import com.antonia.restaurant.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.antonia.restaurant.entity.MenuItem;

import java.util.Optional;

/** Clasa gestionează logicile de afaceri asociate cu elementele din meniul unui restaurant, inclusiv adăugarea, ștergerea, căutarea și filtrarea acestora.
 * Folosește MenuItemRepository pentru a interacționa cu baza de date și OrderService pentru a gestiona ordinele legate de elementele de meniu.
 * @author Andrei Antonia-Stefania
 * @version 12 Ianuarie 2025
 */

@Service
public class MenuService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    public Page<MenuItem> getAllMenuItems(Pageable pageable) {
        return menuItemRepository.findAll(pageable);
    }

    public MenuItem saveMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    public Optional<MenuItem> getMenuItemById(Long id) {
        return menuItemRepository.findById(id);
    }

    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }

    // Search/Filter and Paginate
    public Page<MenuItem> findMenuItems(String keyword, String category, Double minPrice, Double maxPrice, Pageable pageable) {
        boolean hasCategory = (category != null && !category.isEmpty());
        boolean hasPriceRange = (minPrice != null && maxPrice != null);
        boolean hasKeyword = (keyword != null && !keyword.isEmpty());

        if (hasKeyword) {
            return menuItemRepository.searchByNameOrDescription(keyword, pageable);
        } else if (hasCategory) {
            return menuItemRepository.findByCategoryIgnoreCase(category, pageable);
        } else if (hasPriceRange) {
            return menuItemRepository.findByPriceBetween(minPrice, maxPrice, pageable);
        } else {
            return menuItemRepository.findAll(pageable);
        }
    }
}
