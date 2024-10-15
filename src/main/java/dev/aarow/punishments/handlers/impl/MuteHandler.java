package dev.aarow.punishments.handlers.impl;

import dev.aarow.punishments.data.player.Profile;
import dev.aarow.punishments.data.punishments.Punishment;
import dev.aarow.punishments.data.punishments.PunishmentType;
import dev.aarow.punishments.handlers.HandlerAdapter;
import dev.aarow.punishments.utility.general.PunishmentUtility;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MuteHandler extends HandlerAdapter {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        Profile profile = plugin.getProfileManager().get(player);

        Punishment punishment = profile.getActivePunishment(PunishmentType.MUTE);

        if(punishment == null) return;

        PunishmentUtility.handlePlayer(punishment);

        event.setCancelled(true);
    }
}
