package com.antonia.restaurant.controller;

import com.antonia.restaurant.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.antonia.restaurant.entity.MenuItem;

/** Clasa gestionează afișarea și căutarea articolelor din meniu.
 * Este responsabilă pentru logica legată de afișarea paginată a meniului, aplicarea filtrelor de căutare și trimiterea rezultatelor către vizualizările front-end.
 * @author Andrei Antonia-Stefania
 * @version 3 Ianuarie 2025
 */



@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * Displays the menu page with pagination support.
     *
     * @param model    Model object
     * @param page     Current page number (starting from 0)
     * @param size     Number of items per page
     * @param keyword  Search keyword
     * @param category Filter by category
     * @param minPrice Minimum price filter
     * @param maxPrice Maximum price filter
     * @return The menu page view
     */
    @GetMapping
    public String viewMenu(
            Model model,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice
    ) {
        // Create pageable request object
        Pageable pageable = PageRequest.of(page, size);

        // Retrieve paginated menu items
        Page<MenuItem> menuPage = menuService.findMenuItems(keyword, category, minPrice, maxPrice, pageable);

        // Add pagination results and other parameters to the model
        model.addAttribute("menuPage", menuPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", menuPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("size", size);

        return "menu";
    }

    /**
     *  Searches for menu items with pagination support.
     *
     * @param model    Model object
     * @param page     Current page number (starting from 0)
     * @param size     Number of items per page
     * @param keyword  Search keyword
     * @param category Filter by category
     * @param minPrice Minimum price filter
     * @param maxPrice Maximum price filter
     * @return The menu page view
     */
    @GetMapping("/search")
    public String searchMenu(
            Model model,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice
    ) {
        // Create pageable request object
        Pageable pageable = PageRequest.of(page, size);

        // Retrieve paginated search results
        Page<MenuItem> menuPage = menuService.findMenuItems(keyword, category, minPrice, maxPrice, pageable);

        // Add pagination results and other parameters to the model
        model.addAttribute("menuPage", menuPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", menuPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("size", size);

        return "menu";
    }
}
