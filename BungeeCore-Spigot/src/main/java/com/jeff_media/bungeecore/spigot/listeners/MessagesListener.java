package com.jeff_media.bungeecore.spigot.listeners;

import com.jeff_media.bungeecore.spigot.BungeeCoreSpigot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MessagesListener implements Listener {
    private final BungeeCoreSpigot plugin;

    public MessagesListener(BungeeCoreSpigot plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(plugin.messageDataManager().getJoinMessage(event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLeave(PlayerQuitEvent event) {
        event.setQuitMessage(plugin.messageDataManager().getLeaveMessage(event.getPlayer()));
    }
}
