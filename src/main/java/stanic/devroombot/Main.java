package stanic.devroombot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import stanic.devroombot.commands.BaseCommand;
import stanic.devroombot.data.dao.StarboardDao;
import stanic.devroombot.data.repository.StarboardRepository;
import stanic.devroombot.database.Database;
import stanic.devroombot.listeners.MessageReactionListener;
import stanic.devroombot.manager.StarboardManager;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) throws LoginException, InterruptedException {
        Database database = new Database();
        StarboardDao starboardDao = new StarboardDao(database);
        StarboardRepository starboardRepository = new StarboardRepository(starboardDao);
        StarboardManager starboardManager = new StarboardManager(starboardRepository);

        database.start(starboardManager);
        starboardDao.loadMessages();
        starboardDao.loadSettings();

        JDA jda = JDABuilder.createDefault(args[0])
                .setActivity(Activity.playing("starboard"))
                .addEventListeners(new BaseCommand(starboardRepository), new MessageReactionListener(starboardRepository, starboardManager))
                .build()
                .awaitReady();
        registerCommands(jda);
        starboardManager.setJda(jda);
    }

    private static void registerCommands(JDA jda) {
        jda.upsertCommand("setstarboardchannel", "Set the starboard channel")
                .addOption(OptionType.CHANNEL, "channel", "The starboard channel")
                .queue();
        jda.upsertCommand("setstarsrequired", "Set the stars required to pin a message")
                .addOption(OptionType.INTEGER, "stars", "The amount of stars")
                .queue();
        jda.upsertCommand("setdmwrr", "Set the delete message when remove message condition")
                .addOption(OptionType.BOOLEAN, "condition", "The condition (true or false)")
                .queue();
    }

}