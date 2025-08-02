package org.example.daos;

import org.example.conections.SQLConnectionProvider;
import org.example.entities.Reservation;

import java.sql.*;
import java.util.*;

public class SQLDBRepository implements EntityDao<Reservation> {

    private final static String STORE_NEW_COMMAND = "INSERT INTO reservations (client_name, total_people, date_time, is_cancelled) VALUES (?, ?, ?, ?)";
    private final static String DELETE_COMMAND = "DELETE FROM reservations WHERE id = ?";
    private final static String UPDATE_COMMAND = "UPDATE reservations SET client_name = ?, total_people = ?, date_time = ?, is_cancelled = ? WHERE id = ?";
    private final static String GET_BY_ID_QUERY = "SELECT * FROM reservations WHERE id = ?";
    private final static String GET_ALL_QUERY = "SELECT * FROM reservations";

    private final SQLConnectionProvider connProvider;

    public SQLDBRepository(SQLConnectionProvider connProvider) {
        this.connProvider = connProvider;
    }

    @Override
    public void add(Reservation reservation) {
        try (Connection conn = connProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(STORE_NEW_COMMAND)) {

            stmt.setString(1, reservation.getClientName());
            stmt.setInt(2, reservation.getTotalPeople());
            stmt.setTimestamp(3, Timestamp.valueOf(reservation.getDateTime()));
            stmt.setBoolean(4, reservation.isCancelled());

            stmt.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(UUID id) {
        try (Connection conn = connProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_COMMAND)) {

            stmt.setString(1, id.toString());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(UUID id, Reservation updatedReservation) {
        try (Connection conn = connProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_COMMAND)) {

            stmt.setString(1, updatedReservation.getClientName());
            stmt.setInt(2, updatedReservation.getTotalPeople());
            stmt.setTimestamp(3, Timestamp.valueOf(updatedReservation.getDateTime()));
            stmt.setBoolean(4, updatedReservation.isCancelled());
            stmt.setObject(5, id, java.sql.Types.OTHER);    // para comparar tipos UUID en postgres

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Reservation> get(UUID id) {
        Optional<Reservation> result = Optional.empty();
        try (Connection conn = connProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_BY_ID_QUERY)) {

            stmt.setString(1, id.toString());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Reservation reservation = new Reservation(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("client_name"),
                        rs.getInt("total_people"),
                        rs.getTimestamp("date_time").toLocalDateTime(),
                        rs.getBoolean("is_cancelled")
                );
                result = Optional.of(reservation);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Collection<Reservation> getAll() {
        List<Reservation> reservations = new ArrayList<>();

        try (Connection conn = connProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL_QUERY)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reservation reservation = new Reservation(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("client_name"),
                        rs.getInt("total_people"),
                        rs.getTimestamp("date_time").toLocalDateTime(),
                        rs.getBoolean("is_cancelled")
                );
                reservations.add(reservation);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }
}
