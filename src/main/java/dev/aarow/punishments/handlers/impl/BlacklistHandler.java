package dev.aarow.punishments.handlers.impl;

import dev.aarow.punishments.data.player.Profile;
import dev.aarow.punishments.data.punishments.Punishment;
import dev.aarow.punishments.data.punishments.PunishmentType;
import dev.aarow.punishments.handlers.HandlerAdapter;
import dev.aarow.punishments.utility.general.PunishmentUtility;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;

public class BlacklistHandler extends HandlerAdapter {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(PlayerLoginEvent event){
        Player player = event.getPlayer();
        Profile profile = plugin.getProfileManager().get(player);

        Punishment punishment = profile.getActivePunishment(PunishmentType.BLACKLIST);

        if(punishment == null) {
            punishment = getBlacklistLocalAddress(event.getAddress().getHostAddress());
            if(punishment == null) return;
        }

        event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        event.setKickMessage(PunishmentUtility.getPunishmentMessage(punishment));
    }

    public Punishment getBlacklistLocalAddress(String localAddress){
        for(Profile profile : plugin.getProfileManager().getProfiles()){
            Punishment punishment = profile.getActivePunishment(PunishmentType.BLACKLIST);

            if(punishment == null) continue;
            if(!punishment.isActive()) continue;

            if(punishment.getLocalAddress().equalsIgnoreCase(localAddress)) return punishment;
        }

        return null;
    }
}
