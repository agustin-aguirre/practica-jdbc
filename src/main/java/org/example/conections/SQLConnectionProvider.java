package org.example.conections;

import java.sql.Connection;
import java.sql.SQLException;

public interface SQLConnectionProvider {
    Connection getConnection() throws SQLException;
}
