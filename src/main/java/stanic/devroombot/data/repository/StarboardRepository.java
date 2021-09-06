package stanic.devroombot.data.repository;

import stanic.devroombot.data.dao.StarboardDao;
import stanic.devroombot.data.domain.StarboardMessage;
import stanic.devroombot.data.domain.StarboardSettings;

public class StarboardRepository {

    private final StarboardDao starboardDao;

    public StarboardRepository(StarboardDao starboardDao) {
        this.starboardDao = starboardDao;
    }

    public StarboardMessage getMessage(String messageId) {
        return getStarboardDao().getMessages().get(messageId);
    }
    public StarboardSettings getSetting() {
        return getStarboardDao().getSettings();
    }

    public void saveMessage(StarboardMessage message) {
        getStarboardDao().saveMessage(message);
    }
    public void removeMessage(StarboardMessage message) {
        getStarboardDao().deleteMessage(message);
    }

    public void editSettings(StarboardSettings settings) {
        getStarboardDao().saveSettings(settings);
    }

    private StarboardDao getStarboardDao() {
        return starboardDao;
    }

}
