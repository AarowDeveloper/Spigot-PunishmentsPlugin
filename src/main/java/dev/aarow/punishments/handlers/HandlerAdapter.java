package dev.aarow.punishments.handlers;

import dev.aarow.punishments.PunishmentPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class HandlerAdapter implements Listener {

    public PunishmentPlugin plugin = PunishmentPlugin.getInstance();

    public HandlerAdapter(){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
}
