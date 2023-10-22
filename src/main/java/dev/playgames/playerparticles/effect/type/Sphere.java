package dev.playgames.playerparticles.effect.type;

import dev.playgames.playerparticles.effect.EffectBase;
import dev.playgames.playerparticles.effect.IEffect;
import fr.mrmicky.fastparticles.ParticleType;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

public class Sphere extends EffectBase implements IEffect {

    double radius = 1.2;
    final double yOffset = 0.5;
    final int particles = 50;

    @Override
    public String getIdentifier() {
        return "sphere";
    }

    @Override
    public double getPrice() {
        return 220.47;
    }

    @Override
    public ParticleType getParticleType() {
        return ParticleType.of("REDSTONE");
    }

    @Override
    public Color getColor() {
        return Color.AQUA;
    }

    @Override
    public Material getMaterial() {
        return null;
    }

    @Override
    public void execute(Location location) {
        location.add(0, yOffset, 0);
        for (int i = 0; i < particles; i++) {
            Vector vector = getRandomVector().multiply(radius);
            location.add(vector);
            displayDust(getColor(), location);
            location.subtract(vector);
        }
    }
}
