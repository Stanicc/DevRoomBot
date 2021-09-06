package stanic.devroombot.utils;

import net.dv8tion.jda.api.entities.MessageReaction;

public class EmoteUtils {

    public boolean isStar(MessageReaction.ReactionEmote emote) {
        String STAR_CODEPOINTS = "U+2b50";
        return emote.getAsCodepoints().equalsIgnoreCase(STAR_CODEPOINTS);
    }

}