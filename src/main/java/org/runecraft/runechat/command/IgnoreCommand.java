package org.runecraft.runechat.command;

import org.runecraft.runechat.RuneChat;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

public class IgnoreCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(!(src instanceof Player)){
            return CommandResult.builder().build();
        }

        Player user = (Player) src;
        if(!args.getOne(Text.of("message")).isPresent()){
            user.sendMessage(Text.builder().color(TextColors.RED).append(Text.of("Especifique um jogador")).build());
            return CommandResult.builder().build();
        }

        Optional<Player> target = Sponge.getServer().getPlayer(args.<String>getOne(Text.of("message")).get());

        if(!target.isPresent()){
            user.sendMessage(Text.builder().color(TextColors.RED).append(Text.of("Esse jogador não está online.")).build());
            return CommandResult.builder().build();
        }
        String ignoredPlayers = RuneChat.get().getConfig().getNode("ignored").getNode(user.getUniqueId().toString()).getString();
        ignoredPlayers.concat(target.get().getUniqueId().toString());

        RuneChat.get().getConfig().getNode("ignored").getNode(user.getUniqueId().toString()).setValue(ignoredPlayers);

        user.sendMessage(Text.builder().color(TextColors.GREEN).append(Text.of("Jogador ignorado.")).build());

        return CommandResult.builder().build();
    }
}
