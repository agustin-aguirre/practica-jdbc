package org.example.services;

import org.example.daos.EntityDao;
import org.example.dtos.MakeReservationDto;
import org.example.dtos.ReservationDto;
import org.example.entities.Reservation;
import org.example.exceptions.ReservationsSystemException;
import org.example.mappers.ReservationsMapper;
import org.example.services.filters.Filterer;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.UUID;

public class ReservationsServiceImplementation implements ReservationsService {
    private final EntityDao<Reservation> reservationsDao;
    private final Filterer<Reservation> filterer;
    private final ReservationsMapper mapper;

    public ReservationsServiceImplementation(EntityDao<Reservation> reservationsDao, Filterer<Reservation> filterer, ReservationsMapper mapper) {
        this.reservationsDao = reservationsDao;
        this.filterer = filterer;
        this.mapper = mapper;
    }

    @Override
    public ReservationDto makeReservation(MakeReservationDto makeReservationDto) {
        if (makeReservationDto.clientName().isEmpty()) {
            throw new ReservationsSystemException("El nombre del cliente no puede estar vacío");
        }
        if (makeReservationDto.totalPeople() < 1) {
            throw new ReservationsSystemException("No se puede hacer una reserva para menos de 1 persona");
        }
        if (makeReservationDto.totalPeople() > 8) {
            throw new ReservationsSystemException("No se puede hacer una reserva para más de 8 personas");
        }
        if (makeReservationDto.dateTime().isBefore(LocalDateTime.now())) {
            throw new ReservationsSystemException("No se puede hacer una reserva para una fecha pasada");
        }

        var newReservationTime = makeReservationDto.dateTime().toLocalTime();
        LocalTime oppeningTime = LocalTime.of(12, 0);
        if (oppeningTime.isAfter(newReservationTime)) {
            throw new ReservationsSystemException("No se puede hacer una reserva en una hora previa al horario de apertura");
        }
        LocalTime closeTime = LocalTime.of(23, 0);
        if (closeTime.isBefore(newReservationTime)) {
            throw new ReservationsSystemException("No se puede hacer una reserva en una hora posterior al horario de cierre");
        }

        var existingReservation = filterer.filter(r -> {
            var targetNameMatch = r.getClientName().equals(makeReservationDto.clientName());
            var dateTimeMatch = r.getDateTime().equals(makeReservationDto.dateTime());
            return targetNameMatch && dateTimeMatch;
        }).stream().findAny();
        if (existingReservation.isPresent()) {
            throw new ReservationsSystemException("Ya existe una reserva para el cliente ingresado en la fecha ingresada");
        }
        var newReservation = mapper.toEntity(makeReservationDto);
        reservationsDao.add(newReservation);

        return mapper.toDto(newReservation);
    }

    @Override
    public Collection<ReservationDto> getAllActiveReservations() {
        var currentDateTime = LocalDateTime.now();
        var activeReservations = filterer.filter((reservation) -> currentDateTime.isBefore(reservation.getDateTime()) && !reservation.isCancelled());
        return activeReservations.stream().map(mapper::toDto).toList();
    }

    @Override
    public Collection<ReservationDto> getAllReservationsFromClient(String clientName) {
        if (clientName.isEmpty()) {
            throw new ReservationsSystemException("El nombre del cliente no puede estar vacío");
        }
        var clientReservations = filterer.filter(reservation -> reservation.getClientName().equals(clientName));
        return clientReservations.stream().map(mapper::toDto).toList();
    }

    @Override
    public void cancelReservation(UUID reservationId) {
        var targetReservation = filterer.filter(reservation -> reservation.getId().equals(reservationId)).stream().findFirst();
        if (targetReservation.isEmpty()) {
            throw new ReservationsSystemException(String.format("Reservation with id %s was not found.", reservationId));
        }
        if (LocalDateTime.now().isAfter(targetReservation.get().getDateTime())) {
            throw new ReservationsSystemException("Cannot change isActive value of a past reservation");
        }
        targetReservation.get().setIsCancelled(true);
        reservationsDao.update(reservationId, targetReservation.get());
    }
}
