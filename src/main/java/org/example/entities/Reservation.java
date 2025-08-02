package org.example.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Reservation {
    private UUID id;
    private String clientName;
    private int totalPeople;
    private LocalDateTime dateTime;
    private boolean isCancelled;

    public Reservation(UUID id, String clientName, int totalPeople, LocalDateTime dateTime, boolean isCancelled) {
        setId(id);
        setClientName(clientName);
        setTotalPeople(totalPeople);
        setDateTime(dateTime);
        setIsCancelled(isCancelled);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getTotalPeople() {
        return totalPeople;
    }

    public void setTotalPeople(int totalPeople) {
        this.totalPeople = totalPeople;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}