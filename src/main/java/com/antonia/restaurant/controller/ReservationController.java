package com.antonia.restaurant.controller;

import com.antonia.restaurant.entity.Reservation;
import com.antonia.restaurant.entity.User;
import com.antonia.restaurant.service.ReservationService;
import com.antonia.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/** Clasa gestionează logica pentru rezervare a utilizatorului.
 * Această clasă permite utilizatorilor să creeze, vizualizeze și să anuleze rezervări.
 * @author Andrei Antonia-Stefania
 * @version 3 Ianuarie 2025
 */


@Controller
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

    // Display the "Make a Reservation" form page
    @GetMapping
    public String showReservationPage(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "reservation"; // direct to reservation.html
    }

    // Create Reservation
    @PostMapping("/create")
    public String createReservation(
            @RequestParam String reservationDate,
            @RequestParam String reservationTime,
            @RequestParam int numberOfPeople
    ) {
        // Get the currently authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return "redirect:/login";
        }

        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        // Manually parse date and time
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(reservationDate, dateFormatter);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(reservationTime, timeFormatter);

        // Build the Reservation object
        Reservation reservation = Reservation.builder()
                .user(user)
                .reservationDate(date)
                .reservationTime(time)
                .numberOfPeople(numberOfPeople)
                .status("PENDING")
                .build();

        // Save the reservation
        reservationService.createReservation(reservation);

        //Redirect to the "My Reservations" page
        return "redirect:/reservation/list";
    }

    // View all reservations of the current user
    @GetMapping("/list")
    public String listUserReservations(Model model) {
        // Get the currently authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return "redirect:/login";
        }

        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        // Get all reservations of the user
        List<Reservation> reservations = reservationService.getReservationsByUser(user);
        model.addAttribute("reservations", reservations);
        return "reservation-list"; // direct to reservation-list.html
    }

    // Cancel reservation (POST)
    @PostMapping("/cancel")
    public String cancelReservation(@RequestParam Long id) {
        reservationService.cancelReservation(id);
        return "redirect:/reservation/list";
    }
}
