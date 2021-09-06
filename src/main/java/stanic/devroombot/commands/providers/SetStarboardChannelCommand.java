package stanic.devroombot.commands.providers;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import stanic.devroombot.data.domain.StarboardSettings;
import stanic.devroombot.data.repository.StarboardRepository;

import java.util.Objects;

public class SetStarboardChannelCommand {

    private final StarboardRepository starboardRepository;

    public SetStarboardChannelCommand(StarboardRepository starboardRepository) {
        this.starboardRepository = starboardRepository;
    }

    public void invoke(SlashCommandEvent event) {
        try {
            TextChannel channel = Objects.requireNonNull(event.getGuild().getTextChannelById(event.getOption("channel").getAsMessageChannel().getId()));
            StarboardSettings starboardSettings = getStarboardRepository().getSetting();
            starboardSettings.setChannelId(channel.getId());

            event.reply("Starboard channel set to: " + channel.getAsMention()).queue();
        } catch (NullPointerException ignored) {
            event.reply(":x: - **channel** cannot be null. \n\n__Use:__ /setstarboardchannel #channel").queue();
        }
    }

    public StarboardRepository getStarboardRepository() {
        return starboardRepository;
    }

}
