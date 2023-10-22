package dev.playgames.playerparticles.effect.type;

import dev.playgames.playerparticles.effect.EffectBase;
import dev.playgames.playerparticles.effect.IEffect;
import fr.mrmicky.fastparticles.ParticleType;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

public class Shield extends EffectBase implements IEffect {

    final double radius = 3;
    final int particles = 50;
    final boolean sphere = false;

    @Override
    public String getIdentifier() {
        return "shield";
    }

    @Override
    public double getPrice() {
        return 175.25;
    }

    @Override
    public ParticleType getParticleType() {
        return ParticleType.of("BLOCK_CRACK");
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public Material getMaterial() {
        return Material.REDSTONE_BLOCK;
    }

    @Override
    public void execute(Location location) {
        for (int i = 0; i < particles; i++) {
            Vector vector = getRandomVector().multiply(radius);
            if (!sphere)
                vector.setY(Math.abs(vector.getY()));

            location.add(vector);
            displayBlockCrack(getMaterial(), location);
            location.subtract(vector);
        }
    }
}
