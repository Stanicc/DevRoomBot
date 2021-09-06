package stanic.devroombot.commands.providers;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import stanic.devroombot.data.domain.StarboardSettings;
import stanic.devroombot.data.repository.StarboardRepository;

import java.util.Optional;

public class SetStarsRequiredCommand {

    private final StarboardRepository starboardRepository;

    public SetStarsRequiredCommand(StarboardRepository starboardRepository) {
        this.starboardRepository = starboardRepository;
    }

    public void invoke(SlashCommandEvent event) {
        try {
            Optional<Integer> amount = parseInt(event.getOption("stars").getAsString(), event);
            if (!amount.isPresent()) return;

            StarboardSettings starboardSettings = getStarboardRepository().getSetting();
            starboardSettings.setStarsRequired(amount.get());
            getStarboardRepository().editSettings(starboardSettings);

            event.reply("Stars required set to: " + amount.get()).queue();
        } catch (NullPointerException ignored) {
            event.reply(":x: - **amount** cannot be null. \n\n__Use:__ /setstarsrequired amount").queue();
        }
    }

    private Optional<Integer> parseInt(String target, SlashCommandEvent event) {
        try {
            Integer integer = Integer.parseInt(target);
            return Optional.of(integer);
        } catch (NumberFormatException ignored) {
            event.reply(":x: - Use only numbers").queue();
            return Optional.empty();
        }
    }

    public StarboardRepository getStarboardRepository() {
        return starboardRepository;
    }

}
