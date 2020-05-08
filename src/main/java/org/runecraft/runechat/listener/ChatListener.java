package org.runecraft.runechat.listener;

import org.runecraft.runechat.event.LocalChatEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.message.MessageChannelEvent;

public class ChatListener implements EventListener<MessageChannelEvent.Chat> {

    @Override
    public void handle(MessageChannelEvent.Chat event) throws Exception {
        event.setCancelled(true);
        LocalChatEvent runeChatEvent = new LocalChatEvent(event.getCause().first(Player.class).get(), event.getMessage());
        Sponge.getEventManager().post(runeChatEvent);
        if(runeChatEvent.isCancelled()){
            runeChatEvent.send();
        }
    }

}
