package dev.playgames.playerparticles.effect.type;

import dev.playgames.playerparticles.effect.EffectBase;
import dev.playgames.playerparticles.effect.IEffect;
import fr.mrmicky.fastparticles.ParticleType;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

public class Flame extends EffectBase implements IEffect {

    @Override
    public String getIdentifier() {
        return "flame";
    }

    @Override
    public double getPrice() {
        return 122.5;
    }

    @Override
    public ParticleType getParticleType() {
        return ParticleType.of("REDSTONE");
    }

    @Override
    public Color getColor() {
        return Color.YELLOW;
    }

    @Override
    public Material getMaterial() {
        return null;
    }

    @Override
    public void execute(Location location) {
        for (int i = 0; i < 10; i++) {
            Vector vector = getRandomCircleVector().multiply(getRandom().nextDouble() * 1.2d);
            vector.setY(getRandom().nextDouble() * 2.6);
            location.add(vector);
            displayDust(getColor(), location);
            location.subtract(vector);
        }
    }
}
