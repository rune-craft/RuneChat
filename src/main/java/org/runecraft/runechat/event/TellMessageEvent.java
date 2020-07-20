package org.runecraft.runechat.event;

import org.runecraft.runechat.channel.manager.ChannelManager;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.*;

public class TellMessageEvent extends AbstractRuneChatEvent {

    private Player destinatary;

    public TellMessageEvent(Player sender, Text message, Player destinatary) {
        super(ChannelManager.getChannelInstance("tell").get(), sender, message, TextColors.LIGHT_PURPLE);
        this.destinatary = destinatary;
    }

    public Player getDestinatary(){
        return destinatary;
    }

    @Override
    public void send(){
        destinatary.playSound(SoundTypes.BLOCK_NOTE_PLING, destinatary.getPosition(), 1d);
        destinatary.sendMessage(formatedText());
    }

    @Override
    public Set<Player> getViewers() {
        List<Context> contexts = new ArrayList<>();
        contexts.add(new Context("sender", sender.getName()));
        contexts.add(new Context("destinatary", destinatary.getName()));

        for(Player p : Sponge.getServer().getOnlinePlayers()){
            if(channel.canView(p, contexts)) return new HashSet<>(Arrays.asList(sender, destinatary));
        }
        return new HashSet<>(Collections.singletonList(sender));
    }

    @Override
    public Text formatedText(){
        Text.Builder builder = Text.builder();

        builder.append(Text.builder()
                .color(TextColors.DARK_GREEN).append(Text.of("["))
                .color(TextColors.GREEN).append(Text.of(sender.getName()))
                .color(TextColors.DARK_GREEN).append(Text.of(" ➡ "))
                .color(TextColors.GREEN).append(Text.of(destinatary.getName()))
                .color(TextColors.DARK_GREEN).append(Text.of("]: "))
                .color(TextColors.GREEN).append(getMessage())
                .build());

        return builder.build();
    }

}
