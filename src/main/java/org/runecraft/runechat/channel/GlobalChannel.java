package org.runecraft.runechat.channel;

import org.runecraft.runechat.util.Utils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.context.Context;

import java.util.List;
import java.util.Set;

public class GlobalChannel extends TextChannel {

    private static GlobalChannel instance;

    public GlobalChannel(String id, boolean showTags) {
        super(id, showTags);
    }

    @Override
    public boolean canView(Player viewer, List<Context> contexts) {
        Player sender = Sponge.getServer().getPlayer(contexts.iterator().next().getValue()).get();
        return sender.getWorld().equals(viewer.getWorld()) && !Utils.isIgnored(viewer, sender);
    }
}
