package stanic.devroombot.data.domain;

public class StarboardMessage {

    final String id;
    final String channelId;
    String starboardMessageId;
    int stars;

    private boolean loaded;

    public StarboardMessage(String id, String channelId, String starboardMessageId, int stars) {
        this.id = id;
        this.channelId = channelId;
        this.starboardMessageId = starboardMessageId;
        this.stars = stars;
    }

    public String getId() {
        return id;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getStarboardMessageId() {
        return starboardMessageId;
    }

    public void setStarboardMessageId(String starboardMessageId) {
        this.starboardMessageId = starboardMessageId;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

}
