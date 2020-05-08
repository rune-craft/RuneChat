package org.runecraft.runechat.event;

import com.google.common.reflect.TypeToken;
import com.google.inject.internal.cglib.core.$DefaultGeneratorStrategy;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.runecraft.runechat.RuneChat;
import org.runecraft.runechat.chat.Channel;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.impl.AbstractEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.*;

public class RuneChatEvent extends AbstractEvent implements Cancellable {

    private boolean cancelled;
    private Player sender;
    private Text message;
    private Channel channel;
    private Map<String, Text> tags = new HashMap<>();
    private Optional<Player> destinatary;

    public RuneChatEvent(Player sender, Text message, Channel channel){
        this.sender = sender;
        this.message = message;
        this.channel = channel;
    }

    public RuneChatEvent(Player sender, Text message, Channel channel, Player destinatary){
        this(sender,message,channel);
        this.destinatary = Optional.ofNullable(destinatary);
    }

    public Player getSender() {
        return sender;
    }

    public Text getMessage() {
        return message;
    }

    public Channel getChannel() {
        return channel;
    }

    public Map<String, Text> getTags() {
        return tags;
    }

    public Optional<Player> getDestinatary() {
        return destinatary;
    }

    public Text getFormatedText(){
        Text.Builder builder = Text.builder();
        if(!destinatary.isPresent()){
            String messagePattern = RuneChat.get().getConfig().getNode("chat").getNode("pattern").getString();
            List<String> avaibleTags = Arrays.asList(messagePattern.split(";"));
            avaibleTags.forEach(tag -> {
                tags.forEach((k,v) -> {
                    if(k.equalsIgnoreCase(tag)){
                        builder.append(v);
                    }
                });
            });
            builder.append(Text.of(" " + sender.getName()));
            builder.append(Text.builder().color(TextColors.GRAY).append(Text.of(":")).build());
            builder.append(Text.builder().color(TextColors.GRAY).append(message).build());
            return builder.build();
        }
        builder.append(Text.builder()
                .color(TextColors.GRAY).append(Text.of("[ "))
                .color(TextColors.GREEN).append(Text.of(sender.getName()))
                .color(TextColors.DARK_GREEN).append(Text.of(" ➤ "))
                .color(TextColors.GREEN).append(Text.of(destinatary.get().getName()))
                .color(TextColors.GRAY).append(Text.of(" ]: "))
                .color(TextColors.GREEN).append(message)
                .build());
        return builder.build();
    }

    public void addTag(String key, Text value){
        tags.put(key, value);
    }

    public Set<Player> getViewers() {
        Set<Player> viewers = new HashSet<>();
        for(Player p : Sponge.getGame().getServer().getOnlinePlayers()){
            List<String> ignoredUsersUid = Arrays.asList(RuneChat.get().getConfig().getNode("ignored").getNode(p.getUniqueId().toString()).getString().split(";"));
            if(!ignoredUsersUid.contains(sender.getUniqueId().toString())){
                if(p.getWorld().equals(sender.getWorld())){
                    switch(channel){
                        case GLOBAL:
                            viewers.add(p);
                            break;
                        case LOCAL:
                            if(p.getLocation().getPosition().distance(sender.getPosition()) <= 100.0){
                                viewers.add(p);
                            }
                            break;
                        case TELL:
                            viewers.add(destinatary.get());
                            break;
                    }
                }
            }
        }
        return viewers;
    }

    public void send(){
        for(Player p : getViewers()){
            if(channel == Channel.TELL){
                p.playSound(SoundTypes.BLOCK_NOTE_PLING, p.getPosition(), 1d);
            }
            p.sendMessage(getFormatedText());
        }
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public Cause getCause() {
        return null;
    }

    @Override
    public Object getSource() {
        return null;
    }

    @Override
    public EventContext getContext() {
        return null;
    }
}
