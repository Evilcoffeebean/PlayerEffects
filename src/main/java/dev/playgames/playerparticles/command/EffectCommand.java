package dev.playgames.playerparticles.command;

import dev.playgames.playerparticles.effect.menu.EffectMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EffectCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getLabel().equalsIgnoreCase("playereffects")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("Client-sided command only.");
                return true;
            }

            new EffectMenu().buildAndOpen(player);
            return true;
        }
        return false;
    }
}
