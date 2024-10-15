package dev.aarow.punishments.managers.impl;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dev.aarow.punishments.managers.Manager;
import dev.aarow.punishments.utility.data.MongoAuthentication;
import lombok.Getter;
import org.bson.Document;

import java.util.ArrayList;

@Getter
public class DatabaseManager extends Manager {

    private MongoCollection<Document> profileCollection;

    @Override
    public void setup() {
        MongoAuthentication mongoAuthentication = new MongoAuthentication();

        MongoClient mongoClient = mongoAuthentication.get();
        MongoDatabase database = mongoClient.getDatabase(mongoAuthentication.getDatabase());

        this.profileCollection = getCollection(database, "profiles");
    }

    public MongoCollection<Document> getCollection(MongoDatabase mongoDatabase, String name){
        if(!mongoDatabase.listCollectionNames().into(new ArrayList<>()).contains(name)) mongoDatabase.createCollection(name);

        return mongoDatabase.getCollection(name);
    }
}
