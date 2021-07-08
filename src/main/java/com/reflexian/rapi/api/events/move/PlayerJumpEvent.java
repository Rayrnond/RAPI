package com.reflexian.rapi.api.events.move;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class PlayerJumpEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;

    private final Player player;
    private final Location from;
    private final Location to;

    /**
     * Ever wanted a version of PlayerJumpEvent?
     */
    public PlayerJumpEvent(final Player player, final Location from, final Location to) {
        this.player = player;
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled=cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
