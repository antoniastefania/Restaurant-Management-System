package com.antonia.restaurant.entity;

import javax.persistence.*;
import lombok.*;


/** Clasa pentru informațiile despre un produs specific dintr-o comandă
 * @author Andrei Antonia-Stefania
 * @version 3 Ianuarie 2025
 */

@Entity
@Table(name = "order_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Belongs to one Order
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // References one MenuItem
    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    private int quantity;

    private double price; // single item price or subtotal
}
