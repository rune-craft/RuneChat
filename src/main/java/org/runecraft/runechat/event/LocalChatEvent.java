package org.runecraft.runechat.event;


import org.runecraft.runechat.RuneChat;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LocalChatEvent extends AbstractRuneChatEvent {

    public LocalChatEvent(Player sender, Text message) {
        super(sender, message);
    }

    @Override
    public Set<Player> getViewers() {
        Set<Player> viewers = new HashSet<>();
        for(Player p : Sponge.getGame().getServer().getOnlinePlayers()) {
            List<String> ignoredUsersUid = Arrays.asList(RuneChat.get().getConfig().getNode("ignored").getNode(p.getUniqueId().toString()).getString().split(";"));
            if (!ignoredUsersUid.contains(getSender().getUniqueId().toString())) {
                if (p.getWorld().equals(sender.getWorld())) {
                    if(p.getPosition().distance(sender.getPosition()) <= 100){
                        viewers.add(p);
                    }
                }
            }
        }
        return viewers;
    }

    @Override
    public Text formatMessage() {
        return null;
    }
}
