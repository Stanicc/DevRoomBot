package stanic.devroombot.database.controller.provider;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import stanic.devroombot.data.domain.StarboardMessage;
import stanic.devroombot.database.Database;

import java.util.HashMap;

public class MessagesData {

    MongoDatabase database;

    public MessagesData(MongoDatabase database) {
        this.database = database;
    }

    public HashMap<String, StarboardMessage> load() {
        HashMap<String, StarboardMessage> map = new HashMap<>();

        for (Document document : database.getCollection(Database.MESSAGES_TABLE).find()) {
            String messageId = document.getString("messageId");
            String channelId = document.getString("channelId");
            String starboardMessageId = document.getString("starboardMessageId");
            int stars = document.getInteger("stars");

            StarboardMessage model = new StarboardMessage(messageId, channelId, starboardMessageId, stars);
            model.setLoaded(true);

            map.put(messageId, model);
        }

        return map;
    }

    public void save(StarboardMessage message) {
        Document document = new Document().append("messageId", message.getId()).append("channelId", message.getChannelId()).append("starboardMessageId", message.getStarboardMessageId()).append("stars", message.getStars());
        if (message.isLoaded()) delete(message);
        database.getCollection(Database.MESSAGES_TABLE).insertOne(document);
    }

    public void delete(StarboardMessage message) {
        database.getCollection(Database.MESSAGES_TABLE).findOneAndDelete(Filters.eq("messageId", message.getId()));
    }

}
