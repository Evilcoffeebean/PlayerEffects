package dev.playgames.playerparticles.effect;

import dev.playgames.playerparticles.Core;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EffectTask extends BukkitRunnable {

    private final IEffect effect;
    private final Player player;

    public EffectTask(final IEffect effect, final Player player) {
        this.effect = effect;
        this.player = player;
    }

    @Override
    public void run() {
        effect.execute(player.getLocation());
        if (!Core.getCore().getEffectManager().hasActiveEffect(player))
            this.cancel();
    }
}
