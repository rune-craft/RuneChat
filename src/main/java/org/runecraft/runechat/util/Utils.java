package org.runecraft.runechat.util;

import org.runecraft.runechat.RuneChat;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Arrays;

public class Utils {
    public static boolean isIgnored(Player ignorer, Player ignored){
        String ignoredString = RuneChat.get().getConfig().getNode("ignored").getNode(ignorer.getUniqueId().toString()).getString();
        if(ignoredString == null) return false;
        if(Arrays.asList(ignoredString.split(";")).contains(ignored.getUniqueId().toString())) return true;
        return false;
    }
}
