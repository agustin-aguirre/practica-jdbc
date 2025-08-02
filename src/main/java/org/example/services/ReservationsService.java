package org.example.services;

import org.example.dtos.MakeReservationDto;
import org.example.dtos.ReservationDto;

import java.util.Collection;
import java.util.UUID;

public interface ReservationsService {
    ReservationDto makeReservation(MakeReservationDto makeReservationDto);

    Collection<ReservationDto> getAllActiveReservations();

    Collection<ReservationDto> getAllReservationsFromClient(String clientName);

    void cancelReservation(UUID reservationId);
}
