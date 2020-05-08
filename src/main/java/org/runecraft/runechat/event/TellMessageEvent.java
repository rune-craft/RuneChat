package org.runecraft.runechat.event;

import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TellMessageEvent extends AbstractRuneChatEvent {

    private Player destinatary;

    public TellMessageEvent(Player sender, Text message, Player destinatary) {
        super(sender, message);
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
        return new HashSet<>(Collections.singletonList(destinatary));
    }

    @Override
    public Text formatedText(){
        Text.Builder builder = Text.builder();

        builder.append(Text.builder()
                .color(TextColors.GRAY).append(Text.of("[ "))
                .color(TextColors.GREEN).append(Text.of(sender.getName()))
                .color(TextColors.DARK_GREEN).append(Text.of(" ➤ "))
                .color(TextColors.GREEN).append(Text.of(destinatary.getName()))
                .color(TextColors.GRAY).append(Text.of(" ]: "))
                .color(TextColors.GREEN).append(getMessage())
                .build());

        return builder.build();
    }

    @Override
    public Text formatMessage() {
        return null;
    }

}
