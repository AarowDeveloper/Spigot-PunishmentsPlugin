package dev.aarow.punishments;

import dev.aarow.punishments.commands.impl.*;
import dev.aarow.punishments.handlers.impl.BanHandler;
import dev.aarow.punishments.handlers.impl.BlacklistHandler;
import dev.aarow.punishments.handlers.impl.MenuHandler;
import dev.aarow.punishments.handlers.impl.MuteHandler;
import dev.aarow.punishments.managers.impl.DatabaseManager;
import dev.aarow.punishments.managers.impl.MenuManager;
import dev.aarow.punishments.managers.impl.ProfileManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Map;

@Getter
public class PunishmentPlugin extends JavaPlugin {

    @Getter private static PunishmentPlugin instance;

    private DatabaseManager databaseManager;
    private ProfileManager profileManager;
    private MenuManager menuManager;

    @Override
    public void onEnable() {
        instance = this;

        this.registerConfiguration();

        this.databaseManager = new DatabaseManager();
        this.profileManager = new ProfileManager();
        this.menuManager = new MenuManager();

        this.registerCommands();
        this.registerHandlers();
    }

    protected void registerCommands(){
        new MuteCommand();
        new BanCommand();
        new KickCommand();
        new BlacklistCommand();
        new UnmuteCommand();
        new UnbanCommand();
        new UnblacklistCommand();
        new HistoryCommand();
    }

    protected void registerHandlers(){
        new MuteHandler();
        new BanHandler();
        new BlacklistHandler();
        new MenuHandler();
    }

    protected void registerConfiguration(){
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }
}
