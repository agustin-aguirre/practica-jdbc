package org.example.dtos;

import java.time.LocalDateTime;

public record MakeReservationDto (String clientName, int totalPeople, LocalDateTime dateTime)
{ }
