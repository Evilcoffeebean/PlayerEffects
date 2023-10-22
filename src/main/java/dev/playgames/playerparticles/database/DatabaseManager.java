package dev.playgames.playerparticles.database;

import dev.playgames.playerparticles.util.TaskUtil;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {

    public static enum EffectType {
        FLAME, SHIELD, SPHERE
    }

    private final MySQL database;
    final String SETUP_TABLE = "CREATE TABLE IF NOT EXISTS accounts (id INT NOT NULL AUTO_INCREMENT, uuid VARCHAR(36), flame BOOLEAN, shield BOOLEAN, sphere BOOLEAN, PRIMARY KEY(id));";
    final String EXISTS_PLAYER = "SELECT * FROM accounts WHERE uuid=?;";
    final String REGISTER_PLAYER = "INSERT INTO accounts (uuid, flame, shield, sphere) VALUES (?, ?, ?, ?);";
    final String GRANT_FLAME = "UPDATE accounts SET flame=? WHERE uuid=?;";
    final String GRANT_SHIELD = "UPDATE accounts SET shield=? WHERE uuid=?;";
    final String GRANT_SPHERE = "UPDATE accounts SET sphere=? WHERE uuid=?;";
    final String HAS_FLAME = "SELECT flame FROM accounts WHERE uuid=?;";
    final String HAS_SHIELD = "SELECT shield FROM accounts WHERE uuid=?;";
    final String HAS_SPHERE = "SELECT sphere FROM accounts WHERE uuid=?;";

    public DatabaseManager(final MySQL database) {
        this.database = database;
        setupTable();
    }

    private void setupTable() {
        TaskUtil.async(() -> {
            try (final PreparedStatement statement = database.getConnection().prepareStatement(SETUP_TABLE)) {
                statement.executeUpdate();
            } catch (SQLException e) {
                e.fillInStackTrace();
            }
        });
    }

    public boolean existsPlayer(final Player player) {
        try (final PreparedStatement statement = database.getConnection().prepareStatement(EXISTS_PLAYER)) {
            statement.setString(1, player.getUniqueId().toString());
            return statement.executeQuery().next();
        } catch (SQLException e) {
            e.fillInStackTrace();
            return false;
        }
    }

    public void registerPlayer(final Player player) {
        TaskUtil.async(() -> {
            try (final PreparedStatement statement = database.getConnection().prepareStatement(REGISTER_PLAYER)) {
                statement.setString(1, player.getUniqueId().toString());
                statement.setBoolean(2, false);
                statement.setBoolean(3, false);
                statement.setBoolean(4, false);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.fillInStackTrace();
            }
        });
    }

    public void grantAccess(final Player player, final EffectType type) {
        TaskUtil.async(() -> {
            switch (type) {
                case FLAME -> {
                    try (final PreparedStatement statement = database.getConnection().prepareStatement(GRANT_FLAME)) {
                        statement.setBoolean(1, true);
                        statement.setString(2, player.getUniqueId().toString());
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        e.fillInStackTrace();
                    }
                }

                case SHIELD -> {
                    try (final PreparedStatement statement = database.getConnection().prepareStatement(GRANT_SHIELD)) {
                        statement.setBoolean(1, true);
                        statement.setString(2, player.getUniqueId().toString());
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        e.fillInStackTrace();
                    }
                }

                case SPHERE -> {
                    try (final PreparedStatement statement = database.getConnection().prepareStatement(GRANT_SPHERE)) {
                        statement.setBoolean(1, true);
                        statement.setString(2, player.getUniqueId().toString());
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        e.fillInStackTrace();
                    }
                }
            }
        });
    }

    public boolean hasEffect(final Player player, final EffectType type) {
        switch (type) {
            case FLAME -> {
                try (final PreparedStatement statement = database.getConnection().prepareStatement(HAS_FLAME)) {
                    statement.setString(1, player.getUniqueId().toString());
                    final ResultSet result = statement.executeQuery();
                    return result.next() && result.getBoolean("flame");
                } catch (SQLException e) {
                    e.fillInStackTrace();
                    return false;
                }
            }

            case SHIELD -> {
                try (final PreparedStatement statement = database.getConnection().prepareStatement(HAS_SHIELD)) {
                    statement.setString(1, player.getUniqueId().toString());
                    final ResultSet result = statement.executeQuery();
                    return result.next() && result.getBoolean("shield");
                } catch (SQLException e) {
                    e.fillInStackTrace();
                    return false;
                }
            }

            case SPHERE -> {
                try (final PreparedStatement statement = database.getConnection().prepareStatement(HAS_SPHERE)) {
                    statement.setString(1, player.getUniqueId().toString());
                    final ResultSet result = statement.executeQuery();
                    return result.next() && result.getBoolean("sphere");
                } catch (SQLException e) {
                    e.fillInStackTrace();
                    return false;
                }
            }
        }
        return false;
    }
}
