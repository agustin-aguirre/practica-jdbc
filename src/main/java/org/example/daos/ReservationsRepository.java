package org.example.daos;

import org.example.entities.Reservation;

import java.util.*;

public class ReservationsRepository implements EntityDao<Reservation> {

    private final Map<UUID, Reservation> reservations;

    public ReservationsRepository() {
        reservations = new HashMap<>();
    }

    public ReservationsRepository(Map<UUID, Reservation> originalData) {
        reservations = originalData;
    }


    @Override
    public void add(Reservation newReservation) {
        var newUUID = UUID.randomUUID();
        newReservation.setId(newUUID);
        reservations.put(newUUID, newReservation);
    }

    @Override
    public void delete(UUID id) {
        reservations.remove(id);
    }

    @Override
    public void update(UUID id, Reservation updatedReservation) {
        var target = get(id);
        if (target.isEmpty()) return;
        target.get().setId(updatedReservation.getId());
        target.get().setClientName(updatedReservation.getClientName());
        target.get().setTotalPeople(updatedReservation.getTotalPeople());
        target.get().setDateTime(updatedReservation.getDateTime());
        target.get().setIsCancelled(updatedReservation.isCancelled());
    }

    @Override
    public Optional<Reservation> get(UUID id) {
        return Optional.of(reservations.getOrDefault(id, null));
    }

    @Override
    public Collection<Reservation> getAll() {
        return reservations.values();
    }
}
