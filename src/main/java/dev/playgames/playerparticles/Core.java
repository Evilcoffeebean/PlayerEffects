package dev.playgames.playerparticles;

import dev.playgames.playerparticles.command.EffectCommand;
import dev.playgames.playerparticles.database.DatabaseConfig;
import dev.playgames.playerparticles.database.DatabaseManager;
import dev.playgames.playerparticles.database.MySQL;
import dev.playgames.playerparticles.effect.EffectManager;
import dev.playgames.playerparticles.effect.transaction.TransactionEngine;
import dev.playgames.playerparticles.util.TaskUtil;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Core extends JavaPlugin implements Listener {

    @Getter
    private static Core core;
    private Economy economy;
    private EffectManager effectManager;
    private DatabaseConfig databaseConfig;
    private MySQL database;
    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        core = this;
        effectManager = new EffectManager();
        databaseConfig = new DatabaseConfig();

        TaskUtil.async(() -> {
            database = new MySQL(
                    databaseConfig.getHost(),
                    databaseConfig.getPort(),
                    databaseConfig.getUsername(),
                    databaseConfig.getPassword(),
                    databaseConfig.getDatabase());
            databaseManager = new DatabaseManager(database);
        });

        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getServer().getPluginManager().registerEvents(this, this);
        getCommand("playereffects").setExecutor(new EffectCommand());
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        effectManager.clearEffects();
        effectManager.clearActive();
        database.closeConnection();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onJoin(final PlayerJoinEvent event) {
        if (!databaseManager.existsPlayer(event.getPlayer()))
            databaseManager.registerPlayer(event.getPlayer());
    }

    @EventHandler (ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onQuit(final PlayerQuitEvent event) {
        effectManager.deactivateEffect(event.getPlayer());
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onClick(final InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player))
            return;
        if (event.getClickedInventory() == null)
            return;
        if (event.getCurrentItem() == null)
            return;

        if (event.getView().getTitle().contains("EFFECTS")) {
            event.setCancelled(true);
            new TransactionEngine(player).execute(event.getSlot());
        }
    }
}
