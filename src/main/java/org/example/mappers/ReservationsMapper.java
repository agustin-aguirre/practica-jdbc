package org.example.mappers;

import org.example.dtos.MakeReservationDto;
import org.example.dtos.ReservationDto;
import org.example.entities.Reservation;

import java.util.UUID;

public class ReservationsMapper {
    public ReservationDto toDto(Reservation reservation) {
        return new ReservationDto(
            reservation.getId(),
            reservation.getClientName(),
            reservation.getTotalPeople(),
            reservation.getDateTime(),
            reservation.isCancelled()
        );
    }

    public Reservation toEntity(ReservationDto reservationDto) {
        return new Reservation(
            reservationDto.id(),
            reservationDto.clientName(),
            reservationDto.totalPeople(),
            reservationDto.dateTime(),
            reservationDto.isCancelled()
        );
    }

    public Reservation toEntity(MakeReservationDto makeReservationDto) {
        return new Reservation(
            new UUID(0L, 0L),
            makeReservationDto.clientName(),
            makeReservationDto.totalPeople(),
            makeReservationDto.dateTime(),
            false
        );
    }
}
