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

@CommandInfo(name = "kick", playerOnly = true, permission = "punishments.staff", complete = true)
public class KickCommand extends BaseCommand {

    @Override
    public void execute(Player player, String[] args) {
        if(args.length == 1){
            Player target = Bukkit.getPlayer(args[0]);
            if(target == null){
                player.sendMessage(CC.translate("&7[&6&lBan&7] &cPlayer is offline."));
                return;
            }
            Profile targetProfile = plugin.getProfileManager().get(target);

            targetProfile.punish(new Punishment(targetProfile.getUuid(), PunishmentType.KICK, "Unspecified", System.currentTimeMillis(), -1, player.getName(), true));
            return;
        }
        if(args.length >= 2){
            Player target = Bukkit.getPlayer(args[0]);
            if(target == null){
                player.sendMessage(CC.translate("&7[&6&lBan&7] &cPlayer is offline."));
                return;
            }
            Profile targetProfile = plugin.getProfileManager().get(target);

            String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

            targetProfile.punish(new Punishment(targetProfile.getUuid(), PunishmentType.KICK, reason, System.currentTimeMillis(), -1, player.getName(), true));
            return;
        }

        player.sendMessage(CC.translate("&7[&cCorrect Usage&7] &c/kick <player> <reason>"));

    }

    @Override
    public List<String> complete(Player player, String[] args) {
        if(args.length == 1){
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        }
        if(args.length == 2){
            return List.of("reason");
        }

        return new ArrayList<>();
    }
}
