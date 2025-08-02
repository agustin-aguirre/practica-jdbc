package org.example.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationDto (
        UUID id,
        String clientName,
        int totalPeople,
        LocalDateTime dateTime,
        boolean isCancelled
) { }
