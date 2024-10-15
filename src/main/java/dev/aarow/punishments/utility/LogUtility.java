package dev.aarow.punishments.utility;

import dev.aarow.punishments.utility.chat.CC;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class LogUtility {

    public static void log(LogType logType, String message){
        Bukkit.getConsoleSender().sendMessage(CC.translate("&7[" + logType.getChatColor() + logType.name() + "&7] &f" + message));
    }

    @Getter @AllArgsConstructor
    public enum LogType {
        INFORMATION(ChatColor.YELLOW), SUCCESS(ChatColor.GREEN), WARNING(ChatColor.GOLD), ERROR(ChatColor.RED);

        private ChatColor chatColor;
    }
}
