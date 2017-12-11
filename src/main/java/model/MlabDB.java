package model;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import java.util.Collections;

public class MlabDB {

    private final MongoClient mongo;
    private final MongoDatabase db;

    // This is a free database, take it if you want ;)
    private final String host = "ds133296.mlab.com";
    private final int port = 33296;
    private final String databaseName = "richrail";
    private final String username = "admin";
    private final String password = "W7LeP3gvQ6H+296";

    private static MlabDB instance;

    private MlabDB() {
        this.mongo = new MongoClient(new ServerAddress(host, port), Collections.singletonList(MongoCredential.createCredential(username, databaseName, password.toCharArray())));
        this.db = mongo.getDatabase(databaseName);
    }

    public MongoClient getMongo() {
        return mongo;
    }

    public MongoDatabase getDatabase() {
        return db;
    }

    public static MlabDB getInstance() {
        if (instance == null)
            instance = new MlabDB();
        return instance;
    }

}
