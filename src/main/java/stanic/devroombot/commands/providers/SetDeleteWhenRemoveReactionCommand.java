package stanic.devroombot.commands.providers;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import stanic.devroombot.data.domain.StarboardSettings;
import stanic.devroombot.data.repository.StarboardRepository;

import java.util.Optional;

public class SetDeleteWhenRemoveReactionCommand {

    private final StarboardRepository starboardRepository;

    public SetDeleteWhenRemoveReactionCommand(StarboardRepository starboardRepository) {
        this.starboardRepository = starboardRepository;
    }

    public void invoke(SlashCommandEvent event) {
        try {
            Optional<Boolean> condition = parseBoolean(event.getOption("condition").getAsString(), event);
            if (!condition.isPresent()) return;

            StarboardSettings starboardSettings = getStarboardRepository().getSetting();
            starboardSettings.setDeleteMessageWhenRemoveReaction(condition.get());
            getStarboardRepository().editSettings(starboardSettings);

            event.reply("Delete message when remove reaction set to: " + condition.get()).queue();
        } catch (NullPointerException ignored) {
            event.reply(":x: - **condition** cannot be null. \n\n__Use:__ /setdmwrr true or false").queue();
        }
    }

    private Optional<Boolean> parseBoolean(String target, SlashCommandEvent event) {
        if (!target.equalsIgnoreCase("true") && !target.equalsIgnoreCase("false")) {
            event.reply(":x: - Use only **true** or **false**").queue();
            return Optional.empty();
        }

        return Optional.of(Boolean.parseBoolean(target));
    }

    public StarboardRepository getStarboardRepository() {
        return starboardRepository;
    }

}
