package dev.aarow.punishments.handlers.impl;

import dev.aarow.punishments.data.player.Profile;
import dev.aarow.punishments.data.punishments.Punishment;
import dev.aarow.punishments.data.punishments.PunishmentType;
import dev.aarow.punishments.handlers.HandlerAdapter;
import dev.aarow.punishments.utility.general.PunishmentUtility;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;

public class BanHandler extends HandlerAdapter {

    @EventHandler
    public void onLogin(PlayerLoginEvent event){
        Player player = event.getPlayer();
        Profile profile = plugin.getProfileManager().get(player);

        Punishment punishment = profile.getActivePunishment(PunishmentType.BAN);

        if(punishment == null) return;

        event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        event.setKickMessage(PunishmentUtility.getPunishmentMessage(punishment));
    }
}
