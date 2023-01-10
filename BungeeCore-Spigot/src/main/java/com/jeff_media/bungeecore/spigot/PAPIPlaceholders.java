package com.jeff_media.bungeecore.spigot;

import com.jeff_media.bungeecore.spigot.BungeeCoreSpigot;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
public class PAPIPlaceholders extends PlaceholderExpansion {

    private final BungeeCoreSpigot plugin;

    @Override
    public @NotNull String getIdentifier() {
        return "simp";
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(", ", plugin.getDescription().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull String getName() {
        return "SimpBungeeCore";
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        String[] split = params.split("_");
        if(split.length == 0) return null;

        if(player != null && player.isOnline()) {
            String result = onPlaceholderRequest(player.getPlayer(), params);
            if(result != null) return result;
        }

        return switch(params) {
            case "server_id" -> plugin.config().serverId();
            case "server_name" -> plugin.config().serverName();
            case "server_display_name" -> plugin.config().serverDisplayName();
            default -> null;
        };
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        String[] split = params.split("_");
        if(split.length == 0) return null;

        return switch(params) {
            case "chatcolor1" -> plugin.getChatColor1(player);
            case "chatcolor2" -> plugin.getChatColor2(player);
            default -> null;
        };
    }
}
