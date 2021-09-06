package stanic.devroombot.manager;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import stanic.devroombot.data.domain.StarboardMessage;
import stanic.devroombot.data.domain.StarboardSettings;
import stanic.devroombot.data.repository.StarboardRepository;

public class StarboardManager {

    private final StarboardRepository starboardRepository;
    private JDA jda;

    public StarboardManager(StarboardRepository starboardRepository) {
        this.starboardRepository = starboardRepository;
    }

    public void handleMessageStarsChange(StarboardMessage starboardMessage, Message message) {
        System.out.println(starboardMessage.getStars());
        if (starboardMessage.getStars() >= getStarboardRepository().getSetting().getStarsRequired()) pinMessage(starboardMessage, message);
        else unpinMessage(starboardMessage);
    }

    public void pinMessage(StarboardMessage starboardMessage, Message message) {
        StarboardSettings settings = getStarboardRepository().getSetting();
        if (settings.getChannelId() == null) return;

        TextChannel channel = getJda().getTextChannelById(settings.getChannelId());
        MessageEmbed embedBuilder = new EmbedBuilder()
                .setTitle("Starboard")
                .setDescription("**Content:** " + message.getContentRaw() + " \n\n**-** Stars: " + starboardMessage.getStars())
                .build();
        if (starboardMessage.getStarboardMessageId() == null) {
            channel.sendMessageEmbeds(embedBuilder).queue(it -> {
                starboardMessage.setStarboardMessageId(it.getId());
                getStarboardRepository().saveMessage(starboardMessage);
            });
        } else channel.retrieveMessageById(starboardMessage.getStarboardMessageId()).complete().editMessageEmbeds(embedBuilder).queue(it -> {
            starboardMessage.setStarboardMessageId(it.getId());
            getStarboardRepository().saveMessage(starboardMessage);
        });
    }
    public void unpinMessage(StarboardMessage starboardMessage) {
        if (getStarboardRepository().getMessage(starboardMessage.getId()) == null) return;

        StarboardSettings settings = getStarboardRepository().getSetting();
        if (settings.getChannelId() == null) return;

        TextChannel channel = getJda().getTextChannelById(starboardMessage.getChannelId());
        channel.retrieveMessageById(starboardMessage.getId()).complete().delete().queue();
        getStarboardRepository().removeMessage(starboardMessage);
    }

    public StarboardRepository getStarboardRepository() {
        return starboardRepository;
    }

    public JDA getJda() {
        return jda;
    }

    public void setJda(JDA jda) {
        this.jda = jda;
    }

}
