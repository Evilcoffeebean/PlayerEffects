package dev.playgames.playerparticles.database;

import dev.playgames.playerparticles.Core;
import dev.playgames.playerparticles.util.FileUtil;

import java.io.File;
import java.io.IOException;

public class DatabaseConfig extends FileUtil {

    public DatabaseConfig() {
        super(new File(Core.getCore().getDataFolder(), "database.yml"));

        try {
            set("database.host", "localhost", false);
            set("database.port", 3306, false);
            set("database.username", "root", false);
            set("database.password", "", false);
            set("database.database-name", "player_effects_db", false);
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    public String getHost() {
        return getString("database.host");
    }

    public int getPort() {
        return getInteger("database.port");
    }

    public String getUsername() {
        return getString("database.username");
    }

    public String getPassword() {
        return getString("database.password");
    }

    public String getDatabase() {
        return getString("database.database-name");
    }
}
