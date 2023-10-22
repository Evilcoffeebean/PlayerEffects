package dev.playgames.playerparticles.database;

import lombok.Getter;

import java.sql.Connection;
import java.sql.SQLException;

@Getter
public abstract class Database {

    protected Connection connection;

    public Database() {
        this.connection = null;
    }

    public abstract Connection openConnection() throws SQLException, ClassNotFoundException;

    public boolean checkConnection() {
        try {
            return this.connection != null && !this.connection.isClosed();
        } catch (SQLException e) {
            e.fillInStackTrace();
            return false;
        }
    }

    public boolean closeConnection() {
        try {
            if (this.connection == null)
                return false;

            this.connection.close();
            return true;
        } catch (SQLException e) {
            e.fillInStackTrace();
            return false;
        }
    }
}
