package org.runecraft.runechat.event;

import org.runecraft.runechat.RuneChat;
import org.runecraft.runechat.channel.manager.ChannelManager;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.*;

public class GlobalChatEvent extends AbstractRuneChatEvent {


    public GlobalChatEvent(Player sender, Text message) {
        super(ChannelManager.getChannelInstance("global").get(), sender, message, TextColors.GRAY);
        addTag("channel", Text.builder("[").color(TextColors.DARK_PURPLE)
                .append(Text.builder("G").color(TextColors.LIGHT_PURPLE).build())
                .append(Text.builder("]").color(TextColors.DARK_PURPLE).build()).build());
    }

    @Override
    public Set<Player> getViewers() {
        Set<Player> viewers = new HashSet<>();
        Sponge.getGame().getServer().getOnlinePlayers()
                .forEach(x -> {
                    if(channel.canView(x, Collections.singletonList(new Context("sender", sender.getName())))) viewers.add(x);
                });
        return viewers;
    }
}
