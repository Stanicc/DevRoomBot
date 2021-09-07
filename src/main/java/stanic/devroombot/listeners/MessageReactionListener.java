package stanic.devroombot.listeners;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import stanic.devroombot.data.domain.StarboardMessage;
import stanic.devroombot.data.repository.StarboardRepository;
import stanic.devroombot.manager.StarboardManager;
import stanic.devroombot.utils.EmoteUtils;

public class MessageReactionListener extends ListenerAdapter {

    private final StarboardRepository starboardRepository;
    private final StarboardManager starboardManager;

    public MessageReactionListener(StarboardRepository starboardRepository, StarboardManager starboardManager) {
        this.starboardRepository = starboardRepository;
        this.starboardManager = starboardManager;
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        if (new EmoteUtils().isStar(event.getReactionEmote())) {
            StarboardMessage starboardMessage = getStarboardRepository().getMessage(event.getMessageId());
            Message message = event.retrieveMessage().complete();
            if (message.getAuthor().getId().equals(event.getUser().getId())) return;

            int stars = (int) message.getReactions().stream().filter(it -> it != null && new EmoteUtils().isStar(it.getReactionEmote())).count();

            if (starboardMessage != null) starboardMessage.setStars(stars + 1);
            else starboardMessage = new StarboardMessage(event.getMessageId(), message.getChannel().getId(), null, stars);

            getStarboardManager().handleMessageStarsChange(starboardMessage, message);
        }
    }

    @Override
    public void onMessageReactionRemove(@NotNull MessageReactionRemoveEvent event) {
        if (new EmoteUtils().isStar(event.getReactionEmote())) {
            StarboardMessage starboardMessage = getStarboardRepository().getMessage(event.getMessageId());
            Message message = event.retrieveMessage().complete();
            if (message.getAuthor().getId().equals(event.getUser().getId())) return;

            int stars = (int) message.getReactions().stream().filter(it -> it != null && new EmoteUtils().isStar(it.getReactionEmote())).count();

            if (starboardMessage != null) starboardMessage.setStars(stars - 1);
            else starboardMessage = new StarboardMessage(event.getMessageId(), message.getChannel().getId(), null, stars);

            getStarboardManager().handleMessageStarsChange(starboardMessage, message);
        }
    }

    @Override
    public void onGuildMessageDelete(@NotNull GuildMessageDeleteEvent event) {
        if (getStarboardRepository().getMessage(event.getMessageId()) != null)
            getStarboardManager().unpinMessage(getStarboardRepository().getMessage(event.getMessageId()));
    }

    public StarboardRepository getStarboardRepository() {
        return starboardRepository;
    }

    public StarboardManager getStarboardManager() {
        return starboardManager;
    }

}
