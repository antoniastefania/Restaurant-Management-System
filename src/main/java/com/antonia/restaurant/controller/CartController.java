package com.antonia.restaurant.controller;

import com.antonia.restaurant.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.antonia.restaurant.entity.MenuItem;

import java.util.ArrayList;
import java.util.List;

/** Clasa care gestionează funcționalitatea coșului de cumpărături.
 *  Aceasta permite adăugarea, vizualizarea și eliminarea articolelor din coș.
 * @author Andrei Antonia-Stefania
 * @version 3 Ianuarie 2025
 */


@Controller
@RequestMapping("/cart")
@SessionAttributes("cart")
public class CartController {

    @Autowired
    private MenuService menuService;

    // Initialize cart if not present
    @ModelAttribute("cart")
    public List<MenuItem> createCart() {
        return new ArrayList<>();
    }

    @PostMapping("/add")
    public String addToCart(@ModelAttribute("cart") List<MenuItem> cart,
                            @RequestParam Long menuItemId) {
        // find item from DB
        MenuItem item = menuService.getMenuItemById(menuItemId).orElse(null);
        if (item != null) {
            cart.add(item);
        }
        return "redirect:/cart";
    }

    @GetMapping
    public String viewCart(@ModelAttribute("cart") List<MenuItem> cart, Model model) {
        double total = cart.stream().mapToDouble(MenuItem::getPrice).sum();
        model.addAttribute("total", total);
        return "cart"; // cart.html
    }

    @PostMapping("/remove")
    public String removeFromCart(@ModelAttribute("cart") List<MenuItem> cart,
                                 @RequestParam Long menuItemId) {
        cart.removeIf(item -> item.getId().equals(menuItemId));
        return "redirect:/cart";
    }
}
