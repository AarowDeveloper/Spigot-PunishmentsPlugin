package dev.aarow.punishments.commands.impl;

import dev.aarow.punishments.commands.BaseCommand;
import dev.aarow.punishments.commands.CommandInfo;
import dev.aarow.punishments.data.player.Profile;
import dev.aarow.punishments.menus.PunishmentHistoryMenu;
import dev.aarow.punishments.utility.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

@CommandInfo(name = "history", playerOnly = true, permission = "punishments.staff", complete = true)
public class HistoryCommand extends BaseCommand {

    @Override
    public void execute(Player player, String[] args) {
        if(args.length != 1){
            player.sendMessage(CC.translate("&7[&cCorrect Usage&7] &c/history <player>"));
            return;
        }

        Profile targetProfile = plugin.getProfileManager().get(Bukkit.getOfflinePlayer(args[0]).getUniqueId());
        if(targetProfile.getPunishments().isEmpty()){
            player.sendMessage(CC.translate("&cThis user doesn't have any punishment history..."));
            return;
        }

        new PunishmentHistoryMenu(player).targetProfile(targetProfile).open(0);
    }

    @Override
    public List<String> complete(Player player, String[] args) {
        return args.length == 1 ? Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()) : List.of();
    }
}
