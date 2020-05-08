package org.runecraft.runechat.command.chat;

import org.runecraft.runechat.event.TellMessageEvent;
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

public class TellCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        if(!(src instanceof Player)){
            return CommandResult.builder().build();
        }

        Text.Builder messageBuilder = Text.builder();
        Collection<String> msg = args.getAll(Text.of("message"));

        Player sender = (Player) src;
        Optional<Player> target = Sponge.getServer().getPlayer(msg.iterator().next());

        if(!target.isPresent()){
            sender.sendMessage(Text.builder().color(TextColors.RED).append(Text.of("Esse jogador não está online.")).build());
            return CommandResult.builder().build();
        }

        msg.forEach(x -> messageBuilder.append(Text.of(x+ " ")));

        TellMessageEvent tellEvent = new TellMessageEvent(sender, messageBuilder.build(), target.get());
        Sponge.getEventManager().post(tellEvent);
        if(!tellEvent.isCancelled()){
            tellEvent.send();
        }

        return CommandResult.builder().build();
    }
}
