package com.jeff_media.bungeecore.spigot.listeners;

import com.jeff_media.bungeecore.spigot.BungeeCoreSpigot;
import com.jeff_media.bungeecore.spigot.Utils;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@RequiredArgsConstructor
public class ChatListener implements Listener {

    private final BungeeCoreSpigot plugin;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String playerNickname = PlaceholderAPI.setPlaceholders(player, plugin.getConfig().getString("format.nickname-placeholder")).replace('§', '&');
        String color1 = plugin.getChatColor1(player);
        String color2 = plugin.getChatColor2(player);
        String serverName = plugin.getConfig().getString("server.name");
        String serverColor = plugin.getConfig().getString("server.color");
        String messagePre = plugin.getConfig().getString("format.gradient");
        String messageFormatted = messagePre
                .replace("%server_color%", serverColor)
                .replace("%server_name%", serverName)
                .replace("%nickname%", playerNickname)
                .replace("%color_1%", color1)
                .replace("%color_2%", color2)
                .replace("%msg%", "%2$s");
        event.setFormat(Utils.format(messageFormatted));
    }
}
