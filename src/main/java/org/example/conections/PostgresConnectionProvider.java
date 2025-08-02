package org.example.conections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnectionProvider implements SQLConnectionProvider {

    private final String url;
    private final String user;
    private final String password;

    public PostgresConnectionProvider(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        if (url == null || user == null || password == null) {
            throw new IllegalStateException("La configuración de DB no fue inicializada.");
        }
        return DriverManager.getConnection(url, user, password);
    }
}
