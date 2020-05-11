package org.runecraft.runechat.channel;

import javafx.scene.text.Text;
import org.runecraft.runechat.channel.TextChannel;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.context.Context;

import java.util.List;
import java.util.Set;

public class LocalChannel extends TextChannel {

    public LocalChannel(String id, boolean showTags) {
        super(id, showTags);
    }

    @Override
    public boolean canView(Player player, List<Context> contexts) {
        Player sender = Sponge.getServer().getPlayer(contexts.iterator().next().getValue()).get();
        return sender.getPosition().distance(player.getPosition()) < 50;
    }
}
