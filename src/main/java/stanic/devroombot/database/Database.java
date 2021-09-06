package stanic.devroombot.database;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import stanic.devroombot.database.controller.DataController;
import stanic.devroombot.manager.StarboardManager;

import java.util.ArrayList;
import java.util.Collections;

public class Database {

    public static final String DATABASE = "starboard";
    public static final String MESSAGES_TABLE = "messages";
    public static final String SETTINGS_TABLE = "settings";

    private final String hostname = "localhost";
    private final int port = 27017;

    MongoClient client;
    MongoDatabase messagesDatabase;
    MongoDatabase settingsDatabase;

    private DataController dataController;

    public MongoClient start(StarboardManager starboardManager) {
        client = MongoClients.create(
                MongoClientSettings.builder().applyToClusterSettings(cluster -> {
                    cluster.hosts(Collections.singletonList(new ServerAddress(hostname, port)));
                }).build()
        );

        messagesDatabase = createDatabase(MESSAGES_TABLE);
        settingsDatabase = createDatabase(SETTINGS_TABLE);

        dataController = new DataController(this, starboardManager).load();
        return client;
    }

    public MongoDatabase createDatabase(String table) {
        MongoDatabase mongoDatabase = client.getDatabase(DATABASE);

        if (!mongoDatabase.listCollectionNames().into(new ArrayList<>()).contains(table))
            mongoDatabase.createCollection(table);

        return mongoDatabase;
    }

    public MongoDatabase getMessagesDatabase() {
        return messagesDatabase;
    }

    public MongoDatabase getSettingsDatabase() {
        return settingsDatabase;
    }

    public MongoClient getClient() {
        return client;
    }

    public DataController getDataController() {
        return dataController;
    }

}
