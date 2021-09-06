package stanic.devroombot.data.domain;

public class StarboardSettings {

    String channelId;
    int starsRequired;
    boolean deleteMessageWhenRemoveReaction;

    public StarboardSettings(String channelId, int starsRequired, boolean deleteMessageWhenRemoveReaction) {
        this.channelId = channelId;
        this.starsRequired = starsRequired;
        this.deleteMessageWhenRemoveReaction = deleteMessageWhenRemoveReaction;
    }

    public String getChannelId() {
        return channelId;
    }

    public int getStarsRequired() {
        return starsRequired;
    }

    public boolean isDeleteMessageWhenRemoveReaction() {
        return deleteMessageWhenRemoveReaction;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public void setStarsRequired(int starsRequired) {
        this.starsRequired = starsRequired;
    }

    public void setDeleteMessageWhenRemoveReaction(boolean deleteMessageWhenRemoveReaction) {
        this.deleteMessageWhenRemoveReaction = deleteMessageWhenRemoveReaction;
    }

}
