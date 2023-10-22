package dev.playgames.playerparticles.effect;

import com.google.common.collect.Maps;
import dev.playgames.playerparticles.effect.type.Flame;
import dev.playgames.playerparticles.effect.type.Shield;
import dev.playgames.playerparticles.effect.type.Sphere;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public final class EffectManager {

    private static final Map<String, IEffect> effects = Maps.newConcurrentMap();
    private static final Map<UUID, IEffect> active = Maps.newConcurrentMap();

    public EffectManager() {
        final Flame flame = new Flame();
        final Shield shield = new Shield();
        final Sphere sphere = new Sphere();


        effects.put(flame.getIdentifier(), flame);
        effects.put(shield.getIdentifier(), shield);
        effects.put(sphere.getIdentifier(), sphere);
    }

    public IEffect getEffect(final String identifier) {
        return effects.get(identifier);
    }

    public boolean hasActiveEffect(final Player player) {
        return active.containsKey(player.getUniqueId());
    }

    public void activateEffect(final Player player, final IEffect effect) {
        active.put(player.getUniqueId(), effect);
    }

    public void deactivateEffect(final Player player) {
        active.remove(player.getUniqueId());
    }

    public IEffect getActiveEffect(final Player player) {
        return active.get(player.getUniqueId());
    }

    public void clearEffects() {
        effects.clear();
    }

    public void clearActive() {
        active.clear();
    }
}
