package org.runecraft.runechat.listener;

import org.runecraft.runechat.event.AbstractRuneChatEvent;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

public class StaffTagListener implements EventListener<AbstractRuneChatEvent> {

    @Override
    public void handle(AbstractRuneChatEvent event) throws Exception {
        Player sender = event.getSender();
        if(sender.hasPermission("group.helper")){
            event.addTag("staffTag", Text.builder("[").color(TextColors.AQUA).style(TextStyles.BOLD)
                    .append(Text.builder("Ajudante").color(TextColors.AQUA).style(TextStyles.BOLD).build())
                    .append(Text.builder("]").color(TextColors.AQUA).style(TextStyles.BOLD).build()).build());
        }
        if(sender.hasPermission("group.moderador")){
            event.addTag("staffTag", Text.builder("[").color(TextColors.GREEN).style(TextStyles.BOLD)
                    .append(Text.builder("Moderador").color(TextColors.GREEN).style(TextStyles.BOLD).build())
                    .append(Text.builder("]").color(TextColors.GREEN).style(TextStyles.BOLD).build()).build());
        }
        if(sender.hasPermission("group.supervisor")){
            event.addTag("staffTag", Text.builder("[").color(TextColors.YELLOW).style(TextStyles.BOLD)
                    .append(Text.builder("Supervisor").color(TextColors.YELLOW).style(TextStyles.BOLD).build())
                    .append(Text.builder("]").color(TextColors.YELLOW).style(TextStyles.BOLD).build()).build());
        }
        if(sender.hasPermission("group.admin")){
            event.addTag("staffTag", Text.builder("[").color(TextColors.LIGHT_PURPLE).style(TextStyles.BOLD)
                    .append(Text.builder("Administrador").color(TextColors.LIGHT_PURPLE).style(TextStyles.BOLD).build())
                    .append(Text.builder("]").color(TextColors.LIGHT_PURPLE).style(TextStyles.BOLD).build()).build());
        }
        if(sender.hasPermission("group.coordenador")){
            event.addTag("staffTag", Text.builder("[").color(TextColors.DARK_BLUE).style(TextStyles.BOLD)
                    .append(Text.builder("Coordenador").color(TextColors.DARK_BLUE).style(TextStyles.BOLD).build())
                    .append(Text.builder("]").color(TextColors.DARK_BLUE).style(TextStyles.BOLD).build()).build());
        }
        if(sender.hasPermission("group.fundador")){
            event.addTag("staffTag", Text.builder("[").color(TextColors.RED).style(TextStyles.BOLD)
                    .append(Text.builder("Fundador").color(TextColors.RED).style(TextStyles.BOLD).build())
                    .append(Text.builder("]").color(TextColors.RED).style(TextStyles.BOLD).build()).build());
        }
    }
}
