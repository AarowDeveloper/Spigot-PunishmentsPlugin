package dev.aarow.punishments.utility.general;

import dev.aarow.punishments.data.punishments.Punishment;
import dev.aarow.punishments.utility.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PunishmentUtility {

    public static void handleGlobalMessage(Punishment punishment){
        Bukkit.broadcastMessage(CC.translate("&7&m-----------------------------"));

        switch(punishment.getPunishmentType()){
            case MUTE:
                Bukkit.broadcastMessage(CC.translate("&6&lMute"));
                Bukkit.broadcastMessage(CC.translate(""));
                Bukkit.broadcastMessage(CC.translate(" &7&l• &ePlayer&7: &f" + Bukkit.getOfflinePlayer(punishment.getUuid()).getName()));
                Bukkit.broadcastMessage(CC.translate(" &7&l• &eExecutor&7: &f" + punishment.getPunishedBy()));
                Bukkit.broadcastMessage(CC.translate(" &7&l• &eReason&7: &f" + punishment.getReason()));
                if(punishment.getDuration() != -1){
                    Bukkit.broadcastMessage(CC.translate(" &7&l• &eDuration&7: &f" + DurationUtility.getDurationFromLong(punishment.getDuration())));
                }
                break;
            case BAN:
                Bukkit.broadcastMessage(CC.translate("&6&lBan"));
                Bukkit.broadcastMessage(CC.translate(""));
                Bukkit.broadcastMessage(CC.translate(" &7&l• &ePlayer&7: &f" + Bukkit.getOfflinePlayer(punishment.getUuid()).getName()));
                Bukkit.broadcastMessage(CC.translate(" &7&l• &eExecutor&7: &f" + punishment.getPunishedBy()));
                Bukkit.broadcastMessage(CC.translate(" &7&l• &eReason&7: &f" + punishment.getReason()));
                if(punishment.getDuration() != -1) {
                    Bukkit.broadcastMessage(CC.translate(" &7&l• &eDuration&7: &f" + DurationUtility.getDurationFromLong(punishment.getDuration())));
                }
                break;
            case KICK:
                Bukkit.broadcastMessage(CC.translate("&6&lKick"));
                Bukkit.broadcastMessage(CC.translate(""));
                Bukkit.broadcastMessage(CC.translate(" &7&l• &ePlayer&7: &f" + Bukkit.getOfflinePlayer(punishment.getUuid()).getName()));
                Bukkit.broadcastMessage(CC.translate(" &7&l• &eExecutor&7: &f" + punishment.getPunishedBy()));
                Bukkit.broadcastMessage(CC.translate(" &7&l• &eReason&7: &f" + punishment.getReason()));
                break;
            case BLACKLIST:
                Bukkit.broadcastMessage(CC.translate("&6&lBlacklist"));
                Bukkit.broadcastMessage(CC.translate(""));
                Bukkit.broadcastMessage(CC.translate(" &7&l• &ePlayer&7: &f" + Bukkit.getOfflinePlayer(punishment.getUuid()).getName()));
                Bukkit.broadcastMessage(CC.translate(" &7&l• &eExecutor&7: &f" + punishment.getPunishedBy()));
                Bukkit.broadcastMessage(CC.translate(" &7&l• &eReason&7: &f" + punishment.getReason()));
                break;
        }

        Bukkit.broadcastMessage(CC.translate("&7&m-----------------------------"));
    }

    public static String getPunishmentMessage(Punishment punishment){
        List<String> lines = new ArrayList<>();

        switch(punishment.getPunishmentType()){
            case MUTE:
                return CC.translate(punishment.getDuration() != -1 ? "&cYou were muted by " + punishment.getPunishedBy() + ", you can chat again in " + DurationUtility.getExpiresIn(punishment.getPunishedAt(), punishment.getDuration()) : "&cYou are permanently muted by " + punishment.getPunishedBy() + ".");
            case BAN:
                lines.add("&7&m-----------------------------");
                lines.add("&cYou are currently banned from this server!");
                lines.add("");
                lines.add(" &7&l• &eExecutor&7: &f" + punishment.getPunishedBy());
                lines.add(" &7&l• &eReason&7: &f" + punishment.getReason());
                lines.add(" &7&l• &eExpires At&7: &f" + (punishment.getDuration() != -1 ? DurationUtility.getExpireDate(punishment.getPunishedAt(), punishment.getDuration()) : "Permanent"));
                lines.add("&7&m-----------------------------");
                return CC.translate(String.join("\n", lines));
            case KICK:
                lines.add("&7&m-----------------------------");
                lines.add("&cYou have been kicked from the server by " + punishment.getPunishedBy() + "!");
                lines.add("&7&m-----------------------------");
                return CC.translate(String.join("\n", lines));
            case BLACKLIST:
                lines.add("&7&m-----------------------------");
                lines.add("&cYou are currently blacklisted from this server!");
                lines.add("");
                lines.add(" &7&l• &eExecutor&7: &f" + punishment.getPunishedBy());
                lines.add(" &7&l• &eReason&7: &f" + punishment.getReason());
                lines.add("&7&m-----------------------------");
                return CC.translate(String.join("\n", lines));
        }

        return null;
    }

    public static void handlePlayer(Punishment punishment){
        Player punished = Bukkit.getPlayer(punishment.getUuid());

        if(punished == null) return;

        String punishmentMessage = getPunishmentMessage(punishment);

        switch(punishment.getPunishmentType()){
            case BAN: case KICK: case BLACKLIST:
                punished.kickPlayer(punishmentMessage);
                break;
            case MUTE:
                punished.sendMessage(punishmentMessage);
                break;
        }
    }
}
