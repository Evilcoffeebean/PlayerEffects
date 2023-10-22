package dev.playgames.playerparticles.effect.menu;

import dev.playgames.playerparticles.Core;
import dev.playgames.playerparticles.database.DatabaseManager;
import dev.playgames.playerparticles.util.MenuUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EffectMenu extends MenuUtil {

    private final DatabaseManager databaseManager;

    public EffectMenu() {
        super("&b&lEFFECTS", 27);
        this.databaseManager = Core.getCore().getDatabaseManager();
    }

    @Override
    public String[] getDesign() {
        return new String[] {
                "x", "x", "x", "x", "x", "x", "x", "x", "x",
                "x", "", "1", "", "2", "", "3", "", "x",
                "x", "x", "x", "x", "x", "x", "x", "x", "x"
        };
    }

    private ItemStack getFlameItem(final Player player) {
        boolean hasEffect = databaseManager.hasEffect(player, DatabaseManager.EffectType.FLAME);
        double price = Core.getCore().getEffectManager().getEffect("flame").getPrice();
        return buildItem(Material.FIRE_CHARGE, hasEffect, "&6&lFlame", hasEffect ? "&d&nEffect Obtained." : "&7Price: &f$" + price);
    }

    private ItemStack getShieldItem(final Player player) {
        boolean hasEffect = databaseManager.hasEffect(player, DatabaseManager.EffectType.SHIELD);
        double price = Core.getCore().getEffectManager().getEffect("shield").getPrice();
        return buildItem(Material.SHIELD, hasEffect, "&c&lShield", hasEffect ? "&d&nEffect Obtained." : "&7Price: &f$" + price);
    }

    private ItemStack getSphereItem(final Player player) {
        boolean hasEffect = databaseManager.hasEffect(player, DatabaseManager.EffectType.SPHERE);
        double price = Core.getCore().getEffectManager().getEffect("sphere").getPrice();
        return buildItem(Material.FIRE_CHARGE, hasEffect, "&b&lSphere", hasEffect ? "&d&nEffect Obtained." : "&7Price: &f$" + price);
    }

    public void buildAndOpen(final Player player) {
        for (int i = 0; i < getDesign().length; i++) {
            switch (getDesign()[i].toLowerCase()) {
                case "x" -> setItem(i, buildItem(Material.RED_STAINED_GLASS_PANE, false, "", ""));
                case "1" -> setItem(i, getFlameItem(player));
                case "2" -> setItem(i, getShieldItem(player));
                case "3" -> setItem(i, getSphereItem(player));
            }
        }
        build(player);
    }
}
