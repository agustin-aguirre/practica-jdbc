package org.example.views.strategies;

import org.example.dtos.ReservationDto;
import org.example.exceptions.ReservationsSystemException;
import org.example.services.ReservationsService;
import org.example.views.CLIFlow;
import org.example.views.displays.Display;

import java.util.Collection;

public class DisplayReservationsFromClientCLIFlow implements CLIFlow {

    private final ReservationsService service;
    private final Display display;

    public DisplayReservationsFromClientCLIFlow(ReservationsService service, Display display) {
        this.service = service;
        this.display = display;
    }

    public void run() {
        Collection<ReservationDto> clientReservations;
        try {
            var clientName = display.readString("Ingrese el nombre de cliente: ");
            clientReservations = service.getAllReservationsFromClient(clientName);
            if (clientReservations.isEmpty()) {
                display.println("<< No hay resultados >>");
            }
            else {
                display.println("Listando las reservas del cliente:");
                clientReservations.forEach(rDto -> {
                    display.println(rDto.toString());
                });
            }
        }
        catch (ReservationsSystemException e) {
            display.println(e.getMessage());
        }
    }
}
