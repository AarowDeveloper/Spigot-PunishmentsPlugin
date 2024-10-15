package dev.aarow.punishments.commands.impl;

import dev.aarow.punishments.commands.BaseCommand;
import dev.aarow.punishments.commands.CommandInfo;
import dev.aarow.punishments.data.player.Profile;
import dev.aarow.punishments.data.punishments.Punishment;
import dev.aarow.punishments.data.punishments.PunishmentType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CommandInfo(name = "blacklist", playerOnly = true, permission = "punishments.staff", complete = true)
public class BlacklistCommand extends BaseCommand {

    @Override
    public void execute(Player player, String[] args) {
        if(args.length == 1){
            Profile targetProfile = plugin.getProfileManager().get(Bukkit.getOfflinePlayer(args[0]).getUniqueId());

            targetProfile.punish(new Punishment(targetProfile.getUuid(), PunishmentType.BLACKLIST, "Unspecified", System.currentTimeMillis(), -1, player.getName(), true).setLocalAddress(player.getAddress().getAddress().getHostAddress()));
        }
        if(args.length >= 2){
            Profile targetProfile = plugin.getProfileManager().get(Bukkit.getOfflinePlayer(args[0]).getUniqueId());

            String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

            targetProfile.punish(new Punishment(targetProfile.getUuid(), PunishmentType.BLACKLIST, reason, System.currentTimeMillis(), -1, player.getName(), true).setLocalAddress(player.getAddress().getAddress().getHostAddress()));
        }
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
