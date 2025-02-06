package com.antonia.restaurant.controller;

import com.antonia.restaurant.entity.OrderDetail;
import com.antonia.restaurant.service.OrderService;
import com.antonia.restaurant.service.UserService;
import com.antonia.restaurant.entity.MenuItem;
import com.antonia.restaurant.entity.Order;
import com.antonia.restaurant.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/** Clasa pentru gestionarea funcționalităților legate de comenzi din aplicatie.
 Aceasta permite utilizatorilor să creeze, vizualizeze, anuleze și plătească comenzi.
 * @author Andrei Antonia-Stefania
 * @version 3 Ianuarie 2025
 */


@Controller
@RequestMapping("/orders")
@SessionAttributes("cart")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    // ================== 1) Submit a new order. ==================
    @PostMapping("/create")
    public String createOrder(@ModelAttribute("cart") List<MenuItem> cart) {
        // Get the currently logged-in user.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || "anonymousUser".equals(auth.getName())) {
            return "redirect:/login";
        }

        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        // Build order details.
        List<OrderDetail> details = new ArrayList<>();
        double total = 0;
        for (MenuItem item : cart) {
            OrderDetail od = new OrderDetail();
            od.setMenuItem(item);
            od.setQuantity(1);
            od.setPrice(item.getPrice());
            details.add(od);
            total += item.getPrice();
        }

        // Create order.
        orderService.createOrder(user, details, total);
        // Empty shopping cart
        cart.clear();

        return "redirect:/orders";
    }

    // ================== 2) View the current user's order list. ==================
    @GetMapping
    public String listUserOrders(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || "anonymousUser".equalsIgnoreCase(auth.getName())) {
            return "redirect:/login";
        }

        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        List<Order> orders = orderService.getOrdersByUser(user);
        model.addAttribute("orders", orders);
        return "orders"; // Direct to orders.html
    }

    // ================== 3) Cancel order ==================
    @PostMapping("/cancel")
    public String cancelOrder(@RequestParam Long orderId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || "anonymousUser".equals(auth.getName())) {
            return "redirect:/login";
        }

        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        orderService.cancelOrder(orderId, user);
        return "redirect:/orders";
    }

    // ================== 4) Pay order (new)==================
    @PostMapping("/pay")
    public String payOrder(@RequestParam Long orderId) {
        // 1) Get the currently logged-in user.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || "anonymousUser".equals(auth.getName())) {
            return "redirect:/login";
        }

        // 2) Find user.
        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        // 3) Call service to perform payment operation (change order status).
        orderService.payOrder(orderId, user);

        // 4) Return to the order list.
        return "redirect:/orders";
    }
}
