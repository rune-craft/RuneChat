package org.runecraft.runechat.channel;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.context.Context;

import java.util.List;
import java.util.Set;

public abstract class TextChannel {
    protected String id;
    protected boolean showTags;

    public TextChannel(String id, boolean showTags){
        this.id = id;
        this.showTags = showTags;
    }

    public String getId(){
        return id;
    }

    public boolean showTags() {
        return showTags;
    }

    public abstract boolean canView(Player player, List<Context> contexts);

}
