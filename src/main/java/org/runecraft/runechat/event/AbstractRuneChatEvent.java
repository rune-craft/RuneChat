package org.runecraft.runechat.event;

import org.runecraft.runechat.RuneChat;
import org.runecraft.runechat.event.interfaces.RuneChatEvent;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.impl.AbstractEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.*;

public abstract class AbstractRuneChatEvent extends AbstractEvent implements Cancellable, RuneChatEvent {

    protected boolean cancelled;
    protected Player sender;
    protected Text message;
    protected Map<String, Text> tags = new HashMap<>();

    public AbstractRuneChatEvent(Player sender, Text message){
        this.sender = sender;
        this.message = message;
    }

    public abstract Set<Player> getViewers();

    public Player getSender() {
        return sender;
    }

    public Text getMessage() {
        return message;
    }

    public Map<String, Text> getTags() {
        return tags;
    }

    public Text formatedText(){
        Text.Builder builder = Text.builder();
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

    public void addTag(String key, Text value){
        tags.put(key, value);
    }

    public void send(){
        for(Player p : getViewers()){
            p.sendMessage(formatedText());
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
