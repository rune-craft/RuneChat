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

        Player sender = (Player) src;
        Optional<Player> destinatario = Sponge.getServer().getPlayer((String)args.getOne("destinatario").get());

        if(!destinatario.isPresent()){
            sender.sendMessage(Text.builder("Esse jogador não está online").color(TextColors.RED).build());
            return CommandResult.builder().build();
        }

        Text message = Text.of(args.getOne("message").get());

        TellMessageEvent tellEvent = new TellMessageEvent(sender, message, destinatario.get());

        Sponge.getEventManager().post(tellEvent);
        if(!tellEvent.isCancelled()){
            tellEvent.send();
        }

        return CommandResult.builder().build();
    }
}
