package dev.aarow.punishments.utility.other;

import dev.aarow.punishments.PunishmentPlugin;
import org.bukkit.Bukkit;

public class Task {

    public static void runLater(Call call){
        Bukkit.getScheduler().runTaskLater(PunishmentPlugin.getInstance(), call::call, 1L);
    }

    public static void runASync(Call call){
        Bukkit.getScheduler().runTaskAsynchronously(PunishmentPlugin.getInstance(), call::call);
    }

    public interface Call {
        void call();
    }
}
