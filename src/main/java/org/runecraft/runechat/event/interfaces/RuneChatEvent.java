package org.runecraft.runechat.event.interfaces;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RuneChatEvent {
    Set<Player> getViewers();

    void send();

    Text formatMessage();

    Player getSender();

    Map<String, Text> getTags();

    void addTag(String key, Text value);
}
