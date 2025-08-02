package org.example.views;

import org.example.services.ReservationsService;
import org.example.views.displays.Display;
import org.example.views.strategies.*;

import java.util.HashMap;
import java.util.Map;

public class ReservationsSystemCLI implements CLIFlow {
    private static final String heading = "---SISTEMA DE RESERVAS---";
    private static final String optionsMsg = """
        1. Registrar nueva reserva
        2. Listar reservas activas
        3. Buscar reservas por cliente
        4. Cancelar reserva
        5. Salir
    """;
    private static final String inputMsg = "Seleccione: ";
    private static final String inputNotValidMsg = "Opción inválida.";

    private final Display display;
    private final Map<Integer, CLIFlow> flows;


    public ReservationsSystemCLI(ReservationsService service, Display display) {
        this.display = display;
        flows = new HashMap<>();
        Integer id = 1;
        flows.put(id++, new MakeReservationCLIFlow(service, display));
        flows.put(id++, new ListActiveReservationsCLIFlow(service, display));
        flows.put(id++, new DisplayReservationsFromClientCLIFlow(service, display));
        flows.put(id++, new CancelReservationCLIFlow(service, display));
        flows.put(id, new QuitCLIFlow(display));
    }

    public void run() {
        var selectedOption = 0;
        while (true) {
            display.println(heading);
            display.println(optionsMsg);
            while (true) {
                selectedOption = display.readInt(inputMsg);
                if (!flows.containsKey(selectedOption)) {
                    display.println(inputNotValidMsg);
                    continue;
                }
                break;
            }
            flows.get(selectedOption).run();
        }
    }
}
