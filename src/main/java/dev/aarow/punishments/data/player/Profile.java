package dev.aarow.punishments.data.player;

import com.google.gson.Gson;
import com.mongodb.client.model.UpdateOptions;
import dev.aarow.punishments.PunishmentPlugin;
import dev.aarow.punishments.data.punishments.Punishment;
import dev.aarow.punishments.data.punishments.PunishmentType;
import dev.aarow.punishments.utility.chat.CC;
import dev.aarow.punishments.utility.general.PunishmentUtility;
import dev.aarow.punishments.utility.general.UnpunishmentUtility;
import dev.aarow.punishments.utility.other.Task;
import dev.aarow.punishments.utility.serializators.PunishmentSerializator;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class Profile {

    public PunishmentPlugin plugin = PunishmentPlugin.getInstance();

    private UUID uuid;

    private List<Punishment> punishments = new ArrayList<>();

    public Profile(UUID uuid){
        this.uuid = uuid;
    }

    public void punish(Punishment punishment){
        this.punishments.stream().filter(otherPunishment -> otherPunishment.getPunishmentType() == punishment.getPunishmentType()).forEach(otherPunishment -> otherPunishment.setActive(false));

        this.punishments.add(punishment);

        this.save();

        PunishmentUtility.handleGlobalMessage(punishment);
        PunishmentUtility.handlePlayer(punishment);
    }

    public void unpunish(Player unpunisher, PunishmentType punishmentType){
        Punishment punishment = getActivePunishment(punishmentType);

        if(punishment == null){
            unpunisher.sendMessage(CC.translate("&cThis user doesn't have this kind of punishment active..."));
            return;
        }

        punishment.setActive(false);

        this.save();

        UnpunishmentUtility.handleExecutor(unpunisher, punishment);

        if(punishmentType == PunishmentType.MUTE && getPlayer() != null){
            getPlayer().sendMessage(CC.translate("&aYou have been un-muted by " + unpunisher.getName() + "."));
        }
    }

    public Punishment getActivePunishment(PunishmentType punishmentType){
        return this.punishments
                .stream()
                .filter(otherPunishment -> otherPunishment.getPunishmentType() == punishmentType)
                .map(Punishment::refresh)
                .filter(Punishment::isActive).findFirst().orElse(null);
    }

    public void save(){
        Task.runASync(() -> {
            Document document = new Document();

            document.put("uuid", uuid.toString());
            document.put("punishments", PunishmentSerializator.serialize(this.punishments));

            if(plugin.getDatabaseManager().getProfileCollection().find(new Document("uuid", uuid.toString())).first() != null){
                plugin.getDatabaseManager().getProfileCollection().replaceOne(new Document("uuid", uuid.toString()), document, new UpdateOptions().upsert(true));
            }else{
                plugin.getDatabaseManager().getProfileCollection().insertOne(document);
            }
        });
    }

    public Player getPlayer(){
        return Bukkit.getPlayer(uuid);
    }
}
