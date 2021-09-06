package stanic.devroombot.data.dao;

import stanic.devroombot.data.domain.StarboardMessage;
import stanic.devroombot.data.domain.StarboardSettings;
import stanic.devroombot.database.Database;

import java.util.HashMap;

public class StarboardDao {

    private final HashMap<String, StarboardMessage> messages = new HashMap<>();
    private StarboardSettings settings = null;

    private final Database database;

    public StarboardDao(Database database) {
        this.database = database;
    }

    public HashMap<String, StarboardMessage> loadMessages() {
        messages.putAll(getDatabase().getDataController().getMessagesData().load());
        return messages;
    }
    public StarboardSettings loadSettings() {
        settings = getDatabase().getDataController().getSettingsData().load();
        return settings;
    }

    public void saveMessage(StarboardMessage message) {
        messages.putIfAbsent(message.getId(), message);
        getDatabase().getDataController().getMessagesData().save(message);
    }
    public void deleteMessage(StarboardMessage message) {
        messages.remove(message.getId());
        getDatabase().getDataController().getMessagesData().delete(message);
    }

    public void saveSettings(StarboardSettings settings) {
        this.settings = settings;
        getDatabase().getDataController().getSettingsData().save(settings);
    }

    public HashMap<String, StarboardMessage> getMessages() {
        return messages;
    }
    public StarboardSettings getSettings() {
        return settings;
    }

    public Database getDatabase() {
        return database;
    }

}
