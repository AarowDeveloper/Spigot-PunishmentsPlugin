package dev.aarow.punishments.commands;

import dev.aarow.punishments.PunishmentPlugin;
import dev.aarow.punishments.utility.chat.CC;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BaseCommand implements CommandExecutor, TabCompleter {
    private final CommandInfo commandInfo;

    public PunishmentPlugin plugin = PunishmentPlugin.getInstance();

    public FileConfiguration config = plugin.getConfig();

    public BaseCommand() {
        this.commandInfo = this.getClass().getDeclaredAnnotation(CommandInfo.class);
        this.plugin.getCommand(this.commandInfo.name()).setExecutor(this);
        this.plugin.getCommand(this.commandInfo.name()).setTabCompleter(this);
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!this.commandInfo.permission().isEmpty() && !commandSender.hasPermission(this.commandInfo.permission())) {
            commandSender.sendMessage(CC.translate("&cNo permission."));
            return true;
        } else if (this.commandInfo.playerOnly()) {
            if (commandSender instanceof ConsoleCommandSender) {
                commandSender.sendMessage(CC.translate("&cOnly players can execute this command!"));
                return true;
            } else {
                this.execute((Player)commandSender, strings);
                return true;
            }
        } else {
            this.execute(commandSender, strings);
            return true;
        }
    }

    public void execute(CommandSender sender, String[] args) {}

    public void execute(Player player, String[] args) {}

    public List<String> complete(CommandSender sender, String[] args){return null;}
    public List<String> complete(Player player, String[] args){return null;}

    @Override
    public @Nullable List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(this.commandInfo.complete()) {
            if(this.commandInfo.playerOnly() && commandSender instanceof Player){
                return this.complete((Player) commandSender, strings);
            }

            if(!commandInfo.playerOnly()) return this.complete(commandSender, strings);
        }

        return new ArrayList<>();
    }
}