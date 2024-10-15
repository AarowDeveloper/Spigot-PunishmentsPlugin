package dev.aarow.punishments.data.punishments;

import dev.aarow.punishments.PunishmentPlugin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.util.UUID;

@Getter @Setter
public class Punishment {

    private UUID uuid;
    private PunishmentType punishmentType;
    private String reason;
    private long punishedAt;
    private long duration;
    private String localAddress;
    private String punishedBy;
    private boolean active;

    public Punishment(UUID uuid, PunishmentType punishmentType, String reason, long punishedAt, long duration, String punishedBy, boolean active){
        this.uuid = uuid;
        this.punishmentType = punishmentType;
        this.reason = reason;
        this.punishedAt = punishedAt;
        this.duration = duration;
        this.punishedBy = punishedBy;
        this.active = active;
    }

    public Punishment setLocalAddress(String localAddress){
        this.localAddress = localAddress;
        return this;
    }

    public Punishment refresh(){
        if(duration == -1) return this;
        if((punishedAt + duration) > System.currentTimeMillis()) return this;

        this.active = false;
        PunishmentPlugin.getInstance().getProfileManager().get(uuid).save();
        return this;
    }
}
