package stanic.devroombot.database.controller;

import com.mongodb.client.MongoClient;
import org.bson.Document;
import stanic.devroombot.data.domain.StarboardMessage;
import stanic.devroombot.data.domain.StarboardSettings;
import stanic.devroombot.database.Database;
import stanic.devroombot.database.controller.provider.MessagesData;
import stanic.devroombot.database.controller.provider.SettingsData;
import stanic.devroombot.manager.StarboardManager;

import java.util.HashMap;

public class DataController {

    private final Database database;
    private final StarboardManager starboardManager;

    private MessagesData messagesData;
    private SettingsData settingsData;

    public DataController(Database database, StarboardManager starboardManager) {
        this.database = database;
        this.starboardManager = starboardManager;
    }

    public DataController load() {
        messagesData = new MessagesData(getDatabase().getMessagesDatabase());
        settingsData = new SettingsData(getDatabase().getSettingsDatabase());

        return this;
    }

    public MessagesData getMessagesData() {
        return messagesData;
    }

    public SettingsData getSettingsData() {
        return settingsData;
    }

    public Database getDatabase() {
        return database;
    }

    public MongoClient getClient() {
        return database.getClient();
    }

    public StarboardManager getStarboardManager() {
        return starboardManager;
    }

}
