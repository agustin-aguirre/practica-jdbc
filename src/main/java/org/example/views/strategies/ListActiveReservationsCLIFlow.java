package org.example.views.strategies;

import org.example.services.ReservationsService;
import org.example.views.CLIFlow;
import org.example.views.displays.Display;

public class ListActiveReservationsCLIFlow implements CLIFlow {

    private final ReservationsService service;
    private final Display display;

    public ListActiveReservationsCLIFlow(ReservationsService service, Display display) {
        this.service = service;
        this.display = display;
    }

    public void run() {
        var activeReservations = service.getAllActiveReservations();
        if (activeReservations.isEmpty()) {
            display.println("<< No hay resultados>>");
        }
        else {
            display.println("Listando las reservas activas:");
            activeReservations.forEach(rDto -> {
                display.println(rDto.toString());
            });
        }
    }
}
