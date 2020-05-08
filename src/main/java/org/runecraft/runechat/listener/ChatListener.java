package org.runecraft.runechat.listener;

import org.runecraft.runechat.chat.Channel;
import org.runecraft.runechat.event.RuneChatEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.message.MessageChannelEvent;

public class ChatListener implements EventListener<MessageChannelEvent.Chat> {

    @Override
    public void handle(MessageChannelEvent.Chat event) throws Exception {
        event.setCancelled(true);
        RuneChatEvent runeChatEvent = new RuneChatEvent(event.getCause().<Player>first(Player.class).get(), event.getMessage(), Channel.LOCAL);
        Sponge.getEventManager().post(runeChatEvent);
        if(runeChatEvent.isCancelled()){
            runeChatEvent.send();
        }
    }

}
