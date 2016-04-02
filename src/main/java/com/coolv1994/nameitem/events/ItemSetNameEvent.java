package com.coolv1994.nameitem.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

public final class ItemSetNameEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final ItemStack item;
    private final boolean recolor;
    private boolean cancelled;

    public ItemSetNameEvent(Player player, ItemStack item, boolean recolor) {
        this.player = player;
        this.item = item;
        this.recolor = recolor;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getItem() {
        return item;
    }

    public boolean isRecolor() {
        return recolor;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}