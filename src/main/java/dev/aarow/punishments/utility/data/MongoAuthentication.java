package dev.aarow.punishments.utility.data;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import dev.aarow.punishments.PunishmentPlugin;

import java.util.Collections;

public class MongoAuthentication {

    private String host;
    private int port;
    private String database;
    private boolean authentication;
    private String username;
    private String password;

    public MongoAuthentication(){
        this.host = PunishmentPlugin.getInstance().getConfig().getString("MONGODB.HOST");
        this.port = PunishmentPlugin.getInstance().getConfig().getInt("MONGODB.PORT");
        this.database = PunishmentPlugin.getInstance().getConfig().getString("MONGODB.DATABASE");
        this.authentication = PunishmentPlugin.getInstance().getConfig().getBoolean("MONGODB.AUTHENTICATION.ENABLED");
        this.username = PunishmentPlugin.getInstance().getConfig().getString("MONGODB.AUTHENTICATION.USERNAME");
        this.password = PunishmentPlugin.getInstance().getConfig().getString("MONGODB.AUTHENTICATION.PASSWORD");
    }

    public String getDatabase() {
        return database;
    }

    public MongoClient get(){
        if(!authentication){
            return new MongoClient(host, port);
        }

        MongoCredential mongoCredential = MongoCredential.createCredential(username, database, password.toCharArray());

        return new MongoClient(new ServerAddress(host, port), Collections.singletonList(mongoCredential));
    }
}
