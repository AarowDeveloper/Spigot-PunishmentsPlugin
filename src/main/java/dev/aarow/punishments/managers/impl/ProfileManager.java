package dev.aarow.punishments.managers.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.aarow.punishments.data.player.Profile;
import dev.aarow.punishments.data.punishments.Punishment;
import dev.aarow.punishments.managers.Manager;
import dev.aarow.punishments.utility.LogUtility;
import dev.aarow.punishments.utility.serializators.PunishmentSerializator;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.*;

public class ProfileManager extends Manager {

    private Map<UUID, Profile> profiles = new HashMap<>();

    @Override
    public void setup() {
        LogUtility.log(LogUtility.LogType.INFORMATION, "Fetching punishments from database!");

        for(Document document : plugin.getDatabaseManager().getProfileCollection().find().into(new ArrayList<>())){
            Profile profile = plugin.getProfileManager().get(UUID.fromString(document.getString("uuid")));

            profile.setPunishments(PunishmentSerializator.deserialize(document.getString("punishments")));
        }

        LogUtility.log(LogUtility.LogType.SUCCESS, "Successfully imported all the punishments from the database!");
    }

    public Profile get(Player player){
        return get(player.getUniqueId());
    }

    public Profile get(UUID uuid){
        if(!this.profiles.containsKey(uuid)) this.profiles.put(uuid, new Profile(uuid));

        return this.profiles.get(uuid);
    }

    public List<Profile> getProfiles(){
        return this.profiles.values().stream().toList();
    }
}