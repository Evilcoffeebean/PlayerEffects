package dev.playgames.playerparticles.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class MySQL extends Database {

    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String database;

    public MySQL(final String host, final int port, final String username, final String password, final String database) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;

        openConnection();
    }

    @Override
    public Connection openConnection() {
        try {
            if (checkConnection())
                return getConnection();

            final String URL = String.format("jdbc:mysql://%s:%d/%s", host, port, database);
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, username, password);

            return getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.fillInStackTrace();
            return null;
        }
    }
}
