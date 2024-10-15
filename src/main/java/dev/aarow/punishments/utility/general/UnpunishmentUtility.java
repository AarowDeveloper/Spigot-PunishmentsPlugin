package dev.aarow.punishments.utility.general;

import dev.aarow.punishments.data.punishments.Punishment;
import dev.aarow.punishments.utility.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UnpunishmentUtility {

    public static void handleExecutor(Player unpunisher, Punishment punishment){
        String name = Bukkit.getOfflinePlayer(punishment.getUuid()).getName();

        switch(punishment.getPunishmentType()){
            case MUTE:
                unpunisher.sendMessage(CC.translate("&aYou un-muted " + name + "."));
                break;
            case BAN:
                unpunisher.sendMessage(CC.translate("&aYou unbanned " + name + "."));
                break;
            case BLACKLIST:
                unpunisher.sendMessage(CC.translate("&aYou unblacklisted " + name + "."));
                break;
        }
    }
}
