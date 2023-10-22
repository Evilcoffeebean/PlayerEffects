package dev.playgames.playerparticles.effect;

import fr.mrmicky.fastparticles.ParticleData;
import fr.mrmicky.fastparticles.ParticleType;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.util.Vector;

import java.util.Random;

@Getter
public class EffectBase {

    private final Random random = new Random();

    public Vector getRandomVector() {
        double x, y, z;
        x = random.nextDouble() * 2 - 1;
        y = random.nextDouble() * 2 - 1;
        z = random.nextDouble() * 2 - 1;

        return new Vector(x, y, z).normalize();
    }

    public Vector getRandomCircleVector() {
        double rnd, x, z;
        rnd = random.nextDouble() * 2 * Math.PI;
        x = Math.cos(rnd);
        z = Math.sin(rnd);

        return new Vector(x, 0, z);
    }

    public void displayParticle(final ParticleType particle, final Location location) {
        displayParticle(particle, location, 1);
    }

    public void displayParticle(final ParticleType particle, final Location location, final int count) {
        Bukkit.getOnlinePlayers().forEach(player -> particle.spawn(player, location, count));
    }

    public void displayDust(final Color color, final Location location) {
        displayDust(color, location, 1, 1);
    }

    public void displayDust(final Color color, final Location location, final int count, final int size) {
        Bukkit.getOnlinePlayers().forEach(player -> ParticleType.of("REDSTONE").spawn(player, location, count, ParticleData.createDustOptions(color, size)));
    }

    public void displayBlockCrack(final Material material, final Location location) {
        displayBlockCrack(material, location, 1);
    }

    public void displayBlockCrack(final Material material, final Location location, final int count) {
        Bukkit.getOnlinePlayers().forEach(player -> ParticleType.of("BLOCK_CRACK").spawn(player, location, count, ParticleData.createBlockData(material)));
    }
}
