package org.runecraft.runechat;

import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.runecraft.runechat.listener.ChatListener;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.plugin.Plugin;

import java.io.File;
import java.io.IOException;

@Plugin(
        id = "runechat",
        name = "RuneChat",
        description = "RuneCraft chat plugin.",
        authors = {
                "Azure"
        }
)
public class RuneChat {

    public static RuneChat instance;

    @Inject
    @ConfigDir(sharedRoot = true)
    private File config;

    @Inject
    @DefaultConfig(sharedRoot = true)
    private ConfigurationLoader<CommentedConfigurationNode> loader;

    private CommentedConfigurationNode configNode;

    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        instance = this;
        Sponge.getEventManager().registerListener(this, MessageChannelEvent.Chat.class, new ChatListener());
    }

    @Listener
    public void onInit(GameInitializationEvent event){
        try {
            if(!config.exists()) {
                config.createNewFile();
                configNode = loader.load();
                loader.save(configNode);
            }
            configNode = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RuneChat get() {return instance;}

    public CommentedConfigurationNode getConfig(){ return configNode; }

}
