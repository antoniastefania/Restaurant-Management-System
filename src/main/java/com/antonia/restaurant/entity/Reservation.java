package com.antonia.restaurant.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.*;
import lombok.*;

/** Clasa pentru rezervare
 * @author Andrei Antonia-Stefania
 * @version 3 Ianuarie 2025
 */



@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Multiple reservations belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Not using @DateTimeFormat because the front end sends the date in "yyyy-MM-dd" format
    private LocalDate reservationDate;
    private LocalTime reservationTime;
    private int numberOfPeople;

    private String status; // "PENDING", "CONFIRMED", "CANCELLED", ...
}
