package org.runecraft.runechat.command;

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

        Text.Builder messageBuilder = Text.builder();
        Collection<String> msg = args.getAll(Text.of("message"));

        Player sender = (Player) src;

        msg.forEach(x -> messageBuilder.append(Text.of(x+ " ")));
        GlobalChatEvent event = new GlobalChatEvent(sender, messageBuilder.build());

        Sponge.getEventManager().post(event);

        if(!event.isCancelled()){
            event.send();
        }

        return CommandResult.builder().build();
    }
}
