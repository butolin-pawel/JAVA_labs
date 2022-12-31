package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostgresConnection {

    private Connection connection;

    private PostgresConnection() { }

    public static PostgresConnection connect(String jdbcUrl) throws SQLException {
        PostgresConnection postgresConnection = new PostgresConnection();
        postgresConnection.connection = DriverManager.getConnection(jdbcUrl);
        return postgresConnection;
    }

    public PreparedStatement preparedStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }

    public void close() throws SQLException {
        connection.close();
    }
}
