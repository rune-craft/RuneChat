package org.runecraft.runechat;

import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.runecraft.runechat.channel.GlobalChannel;
import org.runecraft.runechat.channel.LocalChannel;
import org.runecraft.runechat.channel.TellChannel;
import org.runecraft.runechat.channel.manager.ChannelManager;
import org.runecraft.runechat.command.chat.GlobalCommand;
import org.runecraft.runechat.command.chat.TellCommand;
import org.runecraft.runechat.event.AbstractRuneChatEvent;
import org.runecraft.runechat.listener.ChatListener;
import org.runecraft.runechat.listener.RankTagListener;
import org.runecraft.runechat.listener.StaffTagListener;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

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

    private static RuneChat instance;

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

        ChannelManager.registerChannel("global", GlobalChannel.class, true);
        ChannelManager.registerChannel("local", LocalChannel.class, true);
        ChannelManager.registerChannel("tell", TellChannel.class, false);

        Sponge.getEventManager().registerListener(this, MessageChannelEvent.Chat.class, new ChatListener());
        Sponge.getEventManager().registerListener(this, AbstractRuneChatEvent.class, new RankTagListener());
        Sponge.getEventManager().registerListener(this, AbstractRuneChatEvent.class, new StaffTagListener());

        CommandSpec tell = CommandSpec.builder()
                .arguments(GenericArguments.string(Text.of("destinatario")),
                        GenericArguments.remainingJoinedStrings(Text.of("message")))
                .executor(new TellCommand())
                .build();

        CommandSpec global = CommandSpec.builder()
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("message")))
                .executor(new GlobalCommand())
                .build();

        CommandSpec ignore = CommandSpec.builder()
                .arguments(GenericArguments.string(Text.of("message")))
                .executor(new GlobalCommand())
                .build();

        Sponge.getCommandManager().register(this, tell, "tell", "msg", "message");
        Sponge.getCommandManager().register(this, global, "g", "global");
        Sponge.getCommandManager().register(this, ignore, "ignore", "ignorar");
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
            if(configNode.getNode("chat").getNode("tagsPattern").getValue() == null){
                configNode.getNode("chat").getNode("tagsPattern").setValue(";");
                loader.save(configNode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RuneChat get() {return instance;}

    public PluginContainer getPluginContainer() {
        return Sponge.getPluginManager().fromInstance(this).get();
    }

    public CommentedConfigurationNode getConfig(){ return configNode; }

}
