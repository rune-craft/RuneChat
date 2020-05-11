package org.runecraft.runechat.listener;

import org.runecraft.runechat.event.AbstractRuneChatEvent;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyle;
import org.spongepowered.api.text.format.TextStyles;

import java.awt.*;

public class RankTagListener implements EventListener<AbstractRuneChatEvent> {

    @Override
    public void handle(AbstractRuneChatEvent event) throws Exception {
        Player player = event.getSender();
        if(player.hasPermission("group.duque")){
            event.addTag("vipTag",
                    Text.builder("[").color(TextColors.BLUE).style(TextStyles.BOLD)
                            .append(Text.builder("❖ Duque").color(TextColors.BLUE).style(TextStyles.BOLD).build())
                            .append(Text.builder("]").color(TextColors.BLUE).style(TextStyles.BOLD).build()).build());
        }
        if(player.hasPermission("group.conde")){
            event.addTag("vipTag",
                    Text.builder("[").color(TextColors.DARK_GREEN).style(TextStyles.BOLD)
                            .append(Text.builder("✪ Conde").color(TextColors.DARK_GREEN).style(TextStyles.BOLD).build())
                            .append(Text.builder("]").color(TextColors.DARK_GREEN).style(TextStyles.BOLD).build()).build());
        }
        if(player.hasPermission("group.cavaleiro")){
            event.addTag("vipTag",
                    Text.builder("[").color(TextColors.RED).style(TextStyles.BOLD)
                            .append(Text.builder("✠ Cavaleiro").color(TextColors.RED).style(TextStyles.BOLD).build())
                            .append(Text.builder("]").color(TextColors.RED).style(TextStyles.BOLD).build()).build());
        }
    }
}
