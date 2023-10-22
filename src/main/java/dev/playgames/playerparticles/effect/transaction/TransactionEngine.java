package dev.playgames.playerparticles.effect.transaction;

import dev.playgames.playerparticles.Core;
import dev.playgames.playerparticles.database.DatabaseManager;
import dev.playgames.playerparticles.effect.EffectTask;
import dev.playgames.playerparticles.effect.type.Flame;
import dev.playgames.playerparticles.effect.type.Shield;
import dev.playgames.playerparticles.effect.type.Sphere;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class TransactionEngine {

    private final Player player;

    public TransactionEngine(final Player player) {
        this.player = player;
    }

    private boolean hasFunds(double price) {
        return Core.getCore().getEconomy().getBalance(player) >= price;
    }

    private void deductFunds(double amount) {
        Core.getCore().getEconomy().withdrawPlayer(player, amount);
    }

    private boolean hasFlame() {
        return Core.getCore().getDatabaseManager().hasEffect(player, DatabaseManager.EffectType.FLAME);
    }

    private void grantFlame() {
        Core.getCore().getDatabaseManager().grantAccess(player, DatabaseManager.EffectType.FLAME);
    }

    private boolean hasShield() {
        return Core.getCore().getDatabaseManager().hasEffect(player, DatabaseManager.EffectType.SHIELD);
    }

    private void grantShield() {
        Core.getCore().getDatabaseManager().grantAccess(player, DatabaseManager.EffectType.SHIELD);
    }

    private boolean hasSphere() {
        return Core.getCore().getDatabaseManager().hasEffect(player, DatabaseManager.EffectType.SPHERE);
    }

    private void grantSphere() {
        Core.getCore().getDatabaseManager().grantAccess(player, DatabaseManager.EffectType.SPHERE);
    }

    private void activateFlame() {
        Flame flame = (Flame) Core.getCore().getEffectManager().getEffect("flame");
        Core.getCore().getEffectManager().activateEffect(player, flame);
        new EffectTask(flame, player).runTaskTimerAsynchronously(Core.getCore(), 1L, 1L);
    }

    private void activateShield() {
        Shield shield = (Shield) Core.getCore().getEffectManager().getEffect("shield");
        Core.getCore().getEffectManager().activateEffect(player, shield);
        new EffectTask(shield, player).runTaskTimerAsynchronously(Core.getCore(), 1L, 1L);
    }

    private void activateSphere() {
        Sphere sphere = (Sphere) Core.getCore().getEffectManager().getEffect("sphere");
        Core.getCore().getEffectManager().activateEffect(player, sphere);
        new EffectTask(sphere, player).runTaskTimerAsynchronously(Core.getCore(), 1L, 1L);
    }

    private void deactivateEffect() {
        Core.getCore().getEffectManager().deactivateEffect(player);
    }

    private boolean hasActiveEffect() {
        return Core.getCore().getEffectManager().hasActiveEffect(player);
    }

    public void execute(final int slot) {
        switch (slot) {
            case 11 -> {
                Flame flame = (Flame) Core.getCore().getEffectManager().getEffect("flame");
                if (!hasFlame()) {
                    if (!hasFunds(flame.getPrice())) {
                        player.closeInventory();
                        player.sendMessage(ChatColor.RED + "Insufficient funds.");
                        return;
                    }

                    if (hasActiveEffect()) {
                        deactivateEffect();
                        return;
                    }

                    player.closeInventory();
                    deductFunds(flame.getPrice());
                    grantFlame();
                    activateFlame();
                    player.sendMessage(ChatColor.GREEN + "Flame purchased and activated.");
                    return;
                }

                if (hasActiveEffect()) {
                    deactivateEffect();
                    player.sendMessage(ChatColor.GREEN + "Previous effect deactivated.");
                    return;
                }

                player.closeInventory();
                activateFlame();
                player.sendMessage(ChatColor.GREEN + "Flame activated.");
            }

            case 13 -> {
                Shield shield = (Shield) Core.getCore().getEffectManager().getEffect("shield");
                if (!hasShield()) {
                    if (!hasFunds(shield.getPrice())) {
                        player.closeInventory();
                        player.sendMessage(ChatColor.RED + "Insufficient funds.");
                        return;
                    }

                    if (hasActiveEffect()) {
                        deactivateEffect();
                        return;
                    }

                    player.closeInventory();
                    deductFunds(shield.getPrice());
                    grantShield();
                    activateShield();
                    player.sendMessage(ChatColor.GREEN + "Shield purchased and activated.");
                    return;
                }

                if (hasActiveEffect()) {
                    deactivateEffect();
                    player.sendMessage(ChatColor.GREEN + "Previous effect deactivated.");
                    return;
                }

                player.closeInventory();
                activateShield();
                player.sendMessage(ChatColor.GREEN + "Shield activated.");
            }

            case 15 -> {
                Sphere sphere = (Sphere) Core.getCore().getEffectManager().getEffect("sphere");
                if (!hasSphere()) {
                    if (!hasFunds(sphere.getPrice())) {
                        player.closeInventory();
                        player.sendMessage(ChatColor.RED + "Insufficient funds.");
                        return;
                    }

                    if (hasActiveEffect()) {
                        deactivateEffect();
                        return;
                    }

                    player.closeInventory();
                    deductFunds(sphere.getPrice());
                    grantSphere();
                    activateSphere();
                    player.sendMessage(ChatColor.GREEN + "Sphere purchased and activated.");
                    return;
                }

                if (hasActiveEffect()) {
                    deactivateEffect();
                    player.sendMessage(ChatColor.GREEN + "Previous effect deactivated.");
                    return;
                }

                player.closeInventory();
                activateSphere();
                player.sendMessage(ChatColor.GREEN + "Sphere activated.");
            }
        }
    }
}
