package dev.aarow.punishments.commands.impl;

import dev.aarow.punishments.commands.BaseCommand;
import dev.aarow.punishments.commands.CommandInfo;
import dev.aarow.punishments.data.player.Profile;
import dev.aarow.punishments.data.punishments.Punishment;
import dev.aarow.punishments.data.punishments.PunishmentType;
import dev.aarow.punishments.utility.chat.CC;
import dev.aarow.punishments.utility.general.DurationUtility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CommandInfo(name = "ban", playerOnly = true, permission = "punishments.staff", complete = true)
public class BanCommand extends BaseCommand {

    @Override
    public void execute(Player player, String[] args) {
        if(args.length == 1){
            Profile targetProfile = plugin.getProfileManager().get(Bukkit.getOfflinePlayer(args[0]).getUniqueId());

            targetProfile.punish(new Punishment(targetProfile.getUuid(), PunishmentType.BAN, "Unspecified", System.currentTimeMillis(), -1, player.getName(), true));
            return;
        }
        if(args.length == 2){
            Profile targetProfile = plugin.getProfileManager().get(Bukkit.getOfflinePlayer(args[0]).getUniqueId());
            long duration = DurationUtility.getDurationFromString(args[1]);

            if(duration == -1){
                player.sendMessage(CC.translate("&7[&6&lBan&7] &cInvalid duration. &7&o(ex. 1y1w2m3s)"));
                return;
            }

            targetProfile.punish(new Punishment(targetProfile.getUuid(), PunishmentType.BAN, "Unspecified", System.currentTimeMillis(), duration, player.getName(), true));
            return;
        }
        if(args.length >= 3){
            Profile targetProfile = plugin.getProfileManager().get(Bukkit.getOfflinePlayer(args[0]).getUniqueId());
            long duration = DurationUtility.getDurationFromString(args[1]);

            if(duration == -1){
                player.sendMessage(CC.translate("&7[&6&lBan&7] &cInvalid duration. &7&o(ex. 1y1w2m3s)"));
                return;
            }

            String reason = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

            targetProfile.punish(new Punishment(targetProfile.getUuid(), PunishmentType.BAN, reason, System.currentTimeMillis(), duration, player.getName(), true));
            return;
        }

        player.sendMessage(CC.translate("&7[&cCorrect Usage&7] &c/ban <player> <duration> <reason>"));
    }

    @Override
    public List<String> complete(Player player, String[] args) {
        if(args.length == 1){
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        }
        if(args.length == 2){
            return List.of("duration");
        }
        if(args.length == 3){
            return List.of("reason");
        }

        return new ArrayList<>();
    }
}
