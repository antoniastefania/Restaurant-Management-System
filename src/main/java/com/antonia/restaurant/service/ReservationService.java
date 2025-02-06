package com.antonia.restaurant.service;

import com.antonia.restaurant.entity.Reservation;
import com.antonia.restaurant.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.antonia.restaurant.entity.User;

import java.util.List;

/** Clasa gestionează logica de afaceri asociată cu rezervările într-un restaurant. Aceasta oferă funcționalități pentru crearea, obținerea și anularea rezervărilor, precum și pentru actualizarea statusului acestora de către administratori.
 * @author Andrei Antonia-Stefania
 * @version 12 Ianuarie 2025
 */

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    // Create a reservation
    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    // Retrieve all reservations for a specific user
    public List<Reservation> getReservationsByUser(User user) {
        return reservationRepository.findByUser(user);
    }

    // Retrieve all reservations (for administrators)
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // Cancel a reservation (set status to CANCELLED)
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        if (reservation != null && !reservation.getStatus().equalsIgnoreCase("CANCELLED")) {
            reservation.setStatus("CANCELLED");
            reservationRepository.save(reservation);
        }
    }

    // Administrator updates reservation status
    public void updateReservationStatus(Long reservationId, String newStatus) {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        if (reservation != null) {
            reservation.setStatus(newStatus);
            reservationRepository.save(reservation);
        }
    }
}
