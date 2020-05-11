package org.runecraft.runechat.channel.manager;

import org.runecraft.runechat.RuneChat;
import org.runecraft.runechat.channel.TextChannel;

import javax.xml.soap.Text;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ChannelManager {

    private static HashMap<String, TextChannel> channels = new HashMap<>();

    public static void registerChannel(String id, Class<? extends TextChannel> channelClass, boolean showTags) {
        channels.remove(id);
        try{
            Constructor channelConstructor = channelClass.getConstructor(String.class, boolean.class);
            channels.put(id, (TextChannel) channelConstructor.newInstance(id, showTags));
        }catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException ex){
            RuneChat.get().getPluginContainer().getLogger().error("Error while registering channel " + id + ": " + ex.getMessage());
        }
    }

    public static Optional<TextChannel> getChannelInstance(String id){
        for(Map.Entry<String, TextChannel> entry : channels.entrySet()){
            if(entry.getKey().equalsIgnoreCase(id)){
                return Optional.ofNullable(entry.getValue());
            }
        }
        return Optional.empty();
    }
}
