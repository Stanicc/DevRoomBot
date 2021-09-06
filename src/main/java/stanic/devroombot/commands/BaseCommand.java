package stanic.devroombot.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import stanic.devroombot.commands.providers.SetDeleteWhenRemoveReactionCommand;
import stanic.devroombot.commands.providers.SetStarboardChannelCommand;
import stanic.devroombot.commands.providers.SetStarsRequiredCommand;
import stanic.devroombot.data.repository.StarboardRepository;

public class BaseCommand extends ListenerAdapter {

    private final StarboardRepository starboardRepository;

    private SetStarboardChannelCommand setStarboardChannelCommand;
    private SetStarsRequiredCommand setStarsRequiredCommand;
    private SetDeleteWhenRemoveReactionCommand setDeleteWhenRemoveReactionCommand;

    public BaseCommand(StarboardRepository starboardRepository) {
        this.starboardRepository = starboardRepository;

        this.setStarboardChannelCommand = new SetStarboardChannelCommand(getStarboardRepository());
        this.setStarsRequiredCommand = new SetStarsRequiredCommand(getStarboardRepository());
        this.setDeleteWhenRemoveReactionCommand = new SetDeleteWhenRemoveReactionCommand(getStarboardRepository());
    }

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        switch (event.getName().toLowerCase()) {
            case "setstarboardchannel":
                getSetStarboardChannelCommand().invoke(event);
                break;
            case "setstarsrequired":
                getSetStarsRequiredCommand().invoke(event);
                break;
            // delete message when remove reaction
            case "setdmwrr":
                getSetDeleteWhenRemoveReactionCommand().invoke(event);
                break;
        }
    }

    public SetStarboardChannelCommand getSetStarboardChannelCommand() {
        return setStarboardChannelCommand;
    }
    public SetStarsRequiredCommand getSetStarsRequiredCommand() {
        return setStarsRequiredCommand;
    }
    public SetDeleteWhenRemoveReactionCommand getSetDeleteWhenRemoveReactionCommand() {
        return setDeleteWhenRemoveReactionCommand;
    }

    public StarboardRepository getStarboardRepository() {
        return starboardRepository;
    }

}
