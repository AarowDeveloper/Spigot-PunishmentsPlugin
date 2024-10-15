package dev.aarow.punishments.commands.impl;

import dev.aarow.punishments.commands.BaseCommand;
import dev.aarow.punishments.commands.CommandInfo;
import dev.aarow.punishments.data.player.Profile;
import dev.aarow.punishments.data.punishments.PunishmentType;
import dev.aarow.punishments.utility.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

@CommandInfo(name = "unblacklist", playerOnly = true, permission = "punishments.staff", complete = true)
public class UnblacklistCommand extends BaseCommand {

    @Override
    public void execute(Player player, String[] args) {
        if(args.length != 1){
            player.sendMessage(CC.translate("&7[&cCorrect Usage&7] &c/unmute <player>"));
            return;
        }

        Profile targetProfile = plugin.getProfileManager().get(Bukkit.getOfflinePlayer(args[0]).getUniqueId());

        targetProfile.unpunish(player, PunishmentType.BLACKLIST);
    }

    @Override
    public List<String> complete(Player player, String[] args) {
        if(args.length == 1){
            return plugin.getProfileManager().getProfiles().stream().filter(profile -> profile.getActivePunishment(PunishmentType.BLACKLIST) != null).map(profile -> Bukkit.getOfflinePlayer(profile.getUuid()).getName()).toList();
        }

        return List.of();
    }
}
