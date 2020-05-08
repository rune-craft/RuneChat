package org.runecraft.runechat.event;

import org.runecraft.runechat.RuneChat;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.*;

public class GlobalChatEvent extends AbstractRuneChatEvent {


    public GlobalChatEvent(Player sender, Text message) {
        super(sender, message);
    }

    @Override
    public Set<Player> getViewers() {
        Set<Player> viewers = new HashSet<>();
        for(Player p : Sponge.getGame().getServer().getOnlinePlayers()) {
            List<String> ignoredUsersUid = Arrays.asList(RuneChat.get().getConfig().getNode("ignored").getNode(p.getUniqueId().toString()).getString().split(";"));
            if (!ignoredUsersUid.contains(getSender().getUniqueId().toString())) {
                if (p.getWorld().equals(sender.getWorld())) {
                    viewers.add(p);
                }
            }
        }
        return viewers;
    }

    @Override
    public void addTag(String key, Text value) {

    }

    @Override
    public void send() {

    }

    @Override
    public Text formatMessage() {
        return null;
    }

    @Override
    public Player getSender() {
        return null;
    }

    @Override
    public Map<String, Text> getTags() {
        return null;
    }
}
