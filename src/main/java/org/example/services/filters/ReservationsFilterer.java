package org.example.services.filters;

import org.example.daos.EntityDao;
import org.example.entities.Reservation;

import java.util.Collection;
import java.util.function.Predicate;

public class ReservationsFilterer implements Filterer<Reservation> {

    private final EntityDao<Reservation> data;

    public ReservationsFilterer(EntityDao<Reservation> data) {
        this.data = data;
    }

    @Override
    public Collection<Reservation> filter(Predicate<Reservation> condition) {
        return data.getAll().stream()
                .filter(condition)
                .toList();
    }
}
