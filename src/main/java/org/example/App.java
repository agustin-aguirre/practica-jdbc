package org.example;

import org.example.conections.PostgresConnectionProvider;
import org.example.daos.EntityDao;
import org.example.daos.SQLDBRepository;
import org.example.entities.Reservation;
import org.example.mappers.ReservationsMapper;
import org.example.services.ReservationsService;
import org.example.services.ReservationsServiceImplementation;
import org.example.services.filters.ReservationsFilterer;
import org.example.views.ReservationsSystemCLI;
import org.example.views.displays.ConsoleDisplay;

public class App 
{
    public static void main( String[] args ) {
        var connProvider = new PostgresConnectionProvider(
                System.getenv("DB_URL"),
                System.getenv("DB_USER"),
                System.getenv("DB_PASSWORD")
        );
        EntityDao<Reservation> repo = new SQLDBRepository(connProvider);
        ReservationsService service = new ReservationsServiceImplementation(repo, new ReservationsFilterer(repo), new ReservationsMapper());
        ReservationsSystemCLI reservationsSystem = new ReservationsSystemCLI(service, new ConsoleDisplay());
        reservationsSystem.run();
    }
}
