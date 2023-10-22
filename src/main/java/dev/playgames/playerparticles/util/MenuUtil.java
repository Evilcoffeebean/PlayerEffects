package dev.playgames.playerparticles.util;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Stream;

@Getter
public abstract class MenuUtil {

    private final Inventory inventory;
    private final String name;
    private final int slots;

    public MenuUtil(final String name, final int slots) {
        this.name = name;
        this.slots = (slots % 9) == 0 ? slots : 54;
        this.inventory = Bukkit.createInventory(null, slots, ChatColor.translateAlternateColorCodes('&', name));
    }

    public abstract String[] getDesign();

    public void setItem(final int slot, final ItemStack item) {
        inventory.setItem(slot, item);
    }

    public void build(final Player caller) {
        caller.openInventory(inventory);
    }

    public void close(final Player caller) {
        caller.closeInventory();
    }

    public ItemStack buildItem(final Material material, final boolean glow, final String display, final String... description) {
        final ItemStack item = new ItemStack(material);
        final ItemMeta meta = item.getItemMeta();
        final List<String> lore = Lists.newArrayList();

        if (meta != null) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', display));
            Stream.of(description).forEach(line -> lore.add(ChatColor.translateAlternateColorCodes('&', line)));

            if (glow) {
                meta.addEnchant(Enchantment.LURE, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }
}