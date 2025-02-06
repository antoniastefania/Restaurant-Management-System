package com.antonia.restaurant.service;

import com.antonia.restaurant.entity.Order;
import com.antonia.restaurant.entity.OrderDetail;
import com.antonia.restaurant.entity.User;
import com.antonia.restaurant.repository.OrderDetailRepository;
import com.antonia.restaurant.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/** Clasa gestionează logicile de afaceri asociate cu comenzile unui restaurant, inclusiv crearea comenzilor, obținerea acestora, actualizarea statutului, anularea comenzilor și procesarea plății. Aceasta interacționează cu repository-urile OrderRepository și OrderDetailRepository pentru a efectua operațiuni asupra comenzilor și detaliilor acestora.
 * @author Andrei Antonia-Stefania
 * @version 12 Ianuarie 2025
 */


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public Order createOrder(User user, List<OrderDetail> orderDetails, Double totalAmount) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus("PENDING");
        order.setCreatedTime(LocalDateTime.now());
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        // Save order details
        for (OrderDetail detail : orderDetails) {
            detail.setOrder(savedOrder);
            orderDetailRepository.save(detail);
        }
        return savedOrder;
    }

    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(status);
            orderRepository.save(order);
        }
    }

    // Cancel order
    public void cancelOrder(Long orderId, User currentUser) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            // only if it's the same user & status is PENDING
            if (order.getUser().getId().equals(currentUser.getId())
                    && "PENDING".equalsIgnoreCase(order.getStatus())) {
                order.setStatus("CANCELLED");
                orderRepository.save(order);
            }
        }
    }

    public void payOrder(Long orderId, User currentUser) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            // Verify if the order belongs to currentUser
            if (order.getUser().getId().equals(currentUser.getId())
                    && "PENDING".equalsIgnoreCase(order.getStatus())) {
                // Change to PAID (you can define statuses such as "PAYING," "PAID," "COMPLETE," etc.)
                order.setStatus("PAID");
                orderRepository.save(order);
            }
        }
    }

    public void deleteOrdersByMenuItemId(Long id) {
        orderDetailRepository.deleteByMenuItemId(id);
    }
}
