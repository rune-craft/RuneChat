package org.runecraft.runechat.event;

import org.runecraft.runecharacters.Character;
import org.runecraft.runecharacters.elements.HiddenElement;
import org.runecraft.runecharacters.elements.PrimalElement;
import org.runecraft.runechat.RuneChat;
import org.runecraft.runechat.channel.TextChannel;
import org.runecraft.runechat.event.interfaces.RuneChatEvent;
import org.runecraft.runecore.User;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.impl.AbstractEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

import java.util.*;

public abstract class AbstractRuneChatEvent extends AbstractEvent implements Cancellable, RuneChatEvent {

    protected boolean cancelled;
    protected TextChannel channel;
    protected Text.Builder messageBuilder;
    protected Player sender;
    private Text message;
    private TextColor messageColor;
    protected Map<String, Text> tags = new HashMap<>();

    public AbstractRuneChatEvent(TextChannel channel, Player sender, Text message, TextColor messageColor){
        this.sender = sender;
        this.channel = channel;
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
        if(channel.showTags()){
            addTags();
        }
        messageBuilder.append(Text.builder(sender.getName()).color(TextColors.WHITE).onHover(TextActions.showText(getSenderInfos())).build());
        messageBuilder.append(Text.builder().color(TextColors.LIGHT_PURPLE).append(Text.of(" ❱ ")).build());
        messageBuilder.append(Text.builder().color(messageColor).append(message).build());
        return messageBuilder.build();
    }

    private void addTags() {
        String messagePattern = RuneChat.get().getConfig().getNode("chat").getNode("tagsPattern").getString();
        List<String> avaibleTags = Arrays.asList(messagePattern.split(";"));
        avaibleTags.forEach(tag -> {
            tags.forEach((k,v) -> {
                if(k.equalsIgnoreCase(tag)){
                    messageBuilder.append(v).append(Text.builder(" ").build());
                }
            });
        });
    }

    private Text getSenderInfos(){
        Character character = Character.getCurrentCharacter(User.by(sender).get()).get();
        PrimalElement primalElement = character.getPrimalElement();
        Optional<HiddenElement> hiddenElement = character.getHiddenElement();
        Text.Builder senderInfos = Text.builder();
        senderInfos.append(Text.builder(sender.getName() + " | ")
                .color(TextColors.GREEN).build())
                .append(Text.builder(character.getCharacterClass().getName() + "Nível " + character.getLevel())
                    .color(TextColors.YELLOW).build());
        senderInfos.append(Text.NEW_LINE).append(Text.NEW_LINE);
        senderInfos.append(Text.builder("Elemento Principal: ").color(TextColors.GRAY)
                .append(Text.builder(primalElement.getName()).color(primalElement.getColor()).build()).build());
        if(hiddenElement.isPresent()){
            senderInfos.append(Text.builder("Elemento Oculto: ").color(TextColors.GRAY)
                    .append(Text.builder(hiddenElement.get().getName()).color(TextColors.YELLOW).build())
                    .append(Text.builder(" (").color(TextColors.GRAY).build())
                    .append(Text.builder(hiddenElement.get().getFirstElement().getName()).color(hiddenElement.get().getFirstElement().getColor()).build())
                    .append(Text.builder(" + ").color(TextColors.GRAY).build())
                    .append(Text.builder(hiddenElement.get().getSecondElement().getName()).color(hiddenElement.get().getSecondElement().getColor()).build())
                    .append(Text.builder(")").color(TextColors.GRAY).build())
                    .build());
        }else{
            senderInfos.append(Text.builder("Elemento Oculto: Não desbloqueado ainda").color(TextColors.GRAY).build());
        }
        return senderInfos.build();
    }

    public void addTag(String key, Text value){
        tags.remove(key);
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
