package org.runecraft.runechat.event;


import org.runecraft.runechat.RuneChat;
import org.runecraft.runechat.channel.manager.ChannelManager;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.*;

public class LocalChatEvent extends AbstractRuneChatEvent {

    public LocalChatEvent(Player sender, Text message) {
        super(ChannelManager.getChannelInstance("local").get(), sender, message, TextColors.WHITE);
        this.addTag("channel", Text.builder("[").color(TextColors.YELLOW)
                .append(Text.builder("L").color(TextColors.GOLD).build())
                .append(Text.builder("]").color(TextColors.YELLOW).build()).build());
    }

    @Override
    public Set<Player> getViewers() {
        Set<Player> viewers = new HashSet<>();
        Sponge.getServer().getOnlinePlayers().forEach(x -> {
            if(channel.canView(x, Collections.singletonList(new Context("sender", sender.getName())))) viewers.add(x);
        });
        return viewers;
    }
}
