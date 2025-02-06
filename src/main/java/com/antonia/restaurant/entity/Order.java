package com.antonia.restaurant.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import lombok.*;

/** Clasa pentru a reprezenta o comandă plasată de un utilizator și poate include mai multe detalii despre produsele comandate.
 * @author Andrei Antonia-Stefania
 * @version 3 Ianuarie 2025
 */


@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many orders can belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Double totalAmount;

    private String status; // "PENDING", "CONFIRMED", "DELIVERING", "COMPLETED", "CANCELLED"

    private LocalDateTime createdTime;

    // One order can have many OrderDetail
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;
}
