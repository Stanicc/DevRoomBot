package stanic.devroombot.database.controller.provider;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import stanic.devroombot.data.domain.StarboardSettings;
import stanic.devroombot.database.Database;

public class SettingsData {

    MongoDatabase database;

    public SettingsData(MongoDatabase database) {
        this.database = database;
    }

    public StarboardSettings load() {
        StarboardSettings settings = new StarboardSettings(null, 1, false);

        for (Document document : database.getCollection(Database.SETTINGS_TABLE).find()) {
            String channelId = document.getString("channelId");
            int starsRequired = document.getInteger("starsRequired");
            boolean deleteMessageWhenRemoveReaction = document.getBoolean("deleteMessageWhenRemoveReaction");

            settings = new StarboardSettings(channelId, starsRequired, deleteMessageWhenRemoveReaction);
        }

        return settings;
    }

    public void save(StarboardSettings settings) {
        Document document = new Document()
                .append("settingId", 1)
                .append("channelId", settings.getChannelId())
                .append("starsRequired", settings.getStarsRequired())
                .append("deleteMessageWhenRemoveReaction", settings.isDeleteMessageWhenRemoveReaction());

        delete(settings);
        database.getCollection(Database.SETTINGS_TABLE).insertOne(document);
    }

    private void delete(StarboardSettings settings) {
        database.getCollection(Database.SETTINGS_TABLE).findOneAndDelete(Filters.eq("settingId", 1));
    }

}
