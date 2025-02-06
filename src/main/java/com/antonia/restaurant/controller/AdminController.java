package com.antonia.restaurant.controller;

import com.antonia.restaurant.entity.MenuItem;
import com.antonia.restaurant.service.MenuService;
import com.antonia.restaurant.service.OrderService;
import com.antonia.restaurant.service.ReservationService;
import com.antonia.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/** Clasa pentru gestionarea funcționalităților specifice panoului de administrare a aplicației.
 * @author Andrei Antonia-Stefania
 * @version 2 Ianuarie 2025
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public String adminDashboard(Model model) {
        // 1) Get current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 2) If not logged in or an anonymous user => Prompt to log in.
        if (auth == null || "anonymousUser".equals(auth.getName())) {
            model.addAttribute("errorMsg", "You are not logged in. Please log in first.");
            return "error/not-admin";
        }

        // 3) Check the role
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if (!isAdmin) {
            // If not an administrator.
            model.addAttribute("errorMsg", "You are not an admin, can't view this page.");
            return "error/not-admin";
        }


        return "admin/dashboard";
    }

    // ============== Manage Menu ==============
    @GetMapping("/menu")
    public String manageMenu(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            Model model
    ) {
        // Validate pagination parameters to prevent invalid values.
        if (page < 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 5;
        }

        // Create a pagination request object.
        Pageable pageable = PageRequest.of(page, size);

        // Get the paginated menu items.
        Page<MenuItem> menuPage = menuService.getAllMenuItems(pageable);

        // Add the paginated results and other parameters to the model.
        model.addAttribute("menuPage", menuPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", menuPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("newItem", new MenuItem()); // for adding new item

        return "admin/manage-menu";
    }

    @PostMapping("/menu/add")
    public String addMenuItem(@ModelAttribute("newItem") MenuItem newItem) {
        menuService.saveMenuItem(newItem);
        return "redirect:/admin/menu";
    }

    @GetMapping("/menu/edit")
    public String editMenuItem(@RequestParam Long id, Model model) {
        Optional<MenuItem> item = menuService.getMenuItemById(id);
        if (item.isPresent()) {
            model.addAttribute("menuItem", item.get());
            return "admin/edit-menu-item";
        } else {
            return "redirect:/admin/menu";
        }
    }

    @PostMapping("/menu/update")
    public String updateMenuItem(@ModelAttribute("menuItem") MenuItem menuItem) {
        menuService.saveMenuItem(menuItem);
        return "redirect:/admin/menu";
    }

    @PostMapping("/menu/delete")
    public String deleteMenuItem(@RequestParam Long id) {
        // Call the menu service to delete the menu item.
        menuService.deleteMenuItem(id);
        // Redirect back to the menu management page after deletion.
        return "redirect:/admin/menu";
    }

    // ============== Manage Orders ==============
    @GetMapping("/orders")
    public String manageOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "admin/manage-orders";
    }

    @PostMapping("/update-order-status")
    public String updateOrderStatus(@RequestParam Long orderId, @RequestParam String status) {
        orderService.updateOrderStatus(orderId, status);
        return "redirect:/admin/orders";
    }

    // ============== Manage Users ==============
    @GetMapping("/users")
    public String manageUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/manage-users";
    }

    @PostMapping("/users/enable")
    public String enableUser(@RequestParam Long userId, @RequestParam boolean enabled) {
        userService.enableUser(userId, enabled);
        return "redirect:/admin/users";
    }

    // ============== Manage Reservations ==============
    @GetMapping("/reservations")
    public String manageReservations(Model model) {
        model.addAttribute("reservations", reservationService.getAllReservations());
        return "admin/manage-reservations";
    }

    @PostMapping("/reservations/update-status")
    public String updateReservationStatus(@RequestParam Long reservationId,
                                          @RequestParam String status) {
        reservationService.updateReservationStatus(reservationId, status);
        return "redirect:/admin/reservations";
    }
}
