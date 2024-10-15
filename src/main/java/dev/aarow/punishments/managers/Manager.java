package dev.aarow.punishments.managers;

import dev.aarow.punishments.PunishmentPlugin;
import dev.aarow.punishments.utility.other.Task;

public abstract class Manager {

    public PunishmentPlugin plugin = PunishmentPlugin.getInstance();

    public Manager(){
        Task.runLater(this::setup);
    }

    public abstract void setup();
}
