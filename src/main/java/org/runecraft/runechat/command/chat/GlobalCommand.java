package org.runecraft.runechat.command.chat;

import jdk.nashorn.internal.objects.Global;
import org.runecraft.runechat.event.GlobalChatEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Collection;
import java.util.Optional;

public class GlobalCommand implements CommandExecutor  {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!(src instanceof Player)){
            return CommandResult.builder().build();
        }

        Text message = Text.of((String) args.getOne("message").get());

        Player sender = (Player) src;

        GlobalChatEvent event = new GlobalChatEvent(sender, message);
        Sponge.getEventManager().post(event);

        if(!event.isCancelled()){
            event.send();
        }

        return CommandResult.builder().build();
    }
}
