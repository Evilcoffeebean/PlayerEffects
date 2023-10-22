package dev.playgames.playerparticles.util;

import dev.playgames.playerparticles.Core;

public final class TaskUtil {

    private TaskUtil() {}

    public static void sync(Runnable runnable) {
        Core.getCore().getServer().getScheduler().runTask(Core.getCore(), runnable);
    }

    public static void async(Runnable runnable) {
        Core.getCore().getServer().getScheduler().runTaskAsynchronously(Core.getCore(), runnable);
    }

    public static void syncDelay(long delay, final Runnable runnable) {
        Core.getCore().getServer().getScheduler().runTaskLater(Core.getCore(), runnable, delay);
    }

    public static void syncRepeat(long delay, long repeat, Runnable runnable) {
        Core.getCore().getServer().getScheduler().runTaskTimer(Core.getCore(), runnable, delay, repeat);
    }

    public static void asyncDelay(long delay, final Runnable runnable) {
        Core.getCore().getServer().getScheduler().runTaskLaterAsynchronously(Core.getCore(), runnable, delay);
    }

    public static void asyncRepeat(long delay, long repeat, Runnable runnable) {
        Core.getCore().getServer().getScheduler().runTaskTimerAsynchronously(Core.getCore(), runnable, delay, repeat);
    }
}
