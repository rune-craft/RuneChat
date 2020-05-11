package org.runecraft.runechat.channel;

import org.runecraft.runechat.util.Utils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.context.Context;

import java.util.List;
import java.util.Set;

public class TellChannel extends TextChannel{

    public TellChannel(String id, boolean showTags) {
        super(id, showTags);
    }

    @Override
    public boolean canView(Player player, List<Context> contexts) {
        Player sender = Sponge.getServer().getPlayer(contexts.get(1).getValue()).get();
        Player destinatary = Sponge.getServer().getPlayer(contexts.get(1).getValue()).get();

        return destinatary.equals(player) && !Utils.isIgnored(destinatary, sender);
    }
}
