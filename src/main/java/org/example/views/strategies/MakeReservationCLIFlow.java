package org.example.views.strategies;

import org.example.dtos.MakeReservationDto;
import org.example.exceptions.ReservationsSystemException;
import org.example.services.ReservationsService;
import org.example.views.CLIFlow;
import org.example.views.displays.Display;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MakeReservationCLIFlow implements CLIFlow {

    private final ReservationsService service;
    private final Display display;

    public MakeReservationCLIFlow(ReservationsService service, Display display) {
        this.service = service;
        this.display = display;
    }

    public void run() {
        display.println("Nueva reserva:");
        var dto = readDto();
        try {
            service.makeReservation(dto);
            display.println("Reserva creada exitosamente.");
        }
        catch (ReservationsSystemException e) {
            display.println("Hubo un error creando la reserva:");
            display.println("\t" + e.getMessage());
        }
    }


    private MakeReservationDto readDto() {
        var clientName = display.readString(" • Nombre del cliente: ");
        var totalPeople = display.readInt(" • Cantidad de personas: ");
        var dateTime = LocalDateTime.parse(
                display.readString(" • Fecha (formato yyyy-MM-dd HH:mm): "),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        );
        return new MakeReservationDto(clientName, totalPeople, dateTime);
    }
}
