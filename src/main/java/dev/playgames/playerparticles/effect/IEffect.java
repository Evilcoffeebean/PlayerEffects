package dev.playgames.playerparticles.effect;

import fr.mrmicky.fastparticles.ParticleType;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;

public interface IEffect {

    String getIdentifier();
    double getPrice();
    ParticleType getParticleType();
    Color getColor();
    Material getMaterial();
    void execute(final Location location);
}
