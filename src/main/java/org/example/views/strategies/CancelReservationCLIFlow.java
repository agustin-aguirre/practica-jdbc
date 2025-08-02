package org.example.views.strategies;

import org.example.exceptions.ReservationsSystemException;
import org.example.services.ReservationsService;
import org.example.views.CLIFlow;
import org.example.views.displays.Display;

import java.util.UUID;

public class CancelReservationCLIFlow implements CLIFlow {

    private final ReservationsService service;
    private final Display display;

    public CancelReservationCLIFlow(ReservationsService service, Display display) {
        this.service = service;
        this.display = display;
    }

    public void run() {
        UUID id = readUUID();
        try {
            service.cancelReservation(id);
            display.println("Reserva cancelada");
        }
        catch (ReservationsSystemException e) {
            display.println(e.getMessage());
        }
    }

    private UUID readUUID() {
        while (true) {
            try {
                String input = display.readString("Ingrese el id a cancelar: ");
                return UUID.fromString(input);
            }
            catch (ReservationsSystemException e) {
                display.println(e.getMessage());
            }
        }
    }
}
