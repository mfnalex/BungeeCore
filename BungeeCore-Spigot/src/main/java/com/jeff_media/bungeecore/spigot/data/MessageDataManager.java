package com.jeff_media.bungeecore.spigot.data;

import com.jeff_media.bungeecore.spigot.BungeeCoreSpigot;
import com.jeff_media.bungeecore.spigot.Utils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;


public class MessageDataManager {
    private final MessageData messageData;
    private final BungeeCoreSpigot plugin;
    private final String nicknamePlaceholder;

    public MessageDataManager(FileConfiguration config, BungeeCoreSpigot plugin) {
        String defaultJoin = config.getString("messages.default.join");
        String defaultLeave = config.getString("messages.default.leave");
        this.messageData = new MessageData(defaultJoin, defaultLeave);
        this.plugin = plugin;
        this.nicknamePlaceholder = config.getString("format.nickname-placeholder");
    }

    public String getJoinMessage(Player player) {
        String nickname = PlaceholderAPI.setPlaceholders(player, nicknamePlaceholder);
        String message = player.getPersistentDataContainer().getOrDefault(plugin.joinMessageKey(), PersistentDataType.STRING, messageData.joinMessage())
                .replace("%color_1%", plugin.getChatColor1(player))
                .replace("%color_2%", plugin.getChatColor2(player))
                .replace("%nick%", nickname);
        return Utils.format(message);
    }

    public String getLeaveMessage(Player player) {
        String nickname = PlaceholderAPI.setPlaceholders(player, nicknamePlaceholder);
        String message = player.getPersistentDataContainer().getOrDefault(plugin.leaveMessageKey(), PersistentDataType.STRING, messageData.leaveMessage())
                .replace("%color_1%", plugin.getChatColor1(player))
                .replace("%color_2%", plugin.getChatColor2(player))
                .replace("%nick%", nickname);
        return Utils.format(message);
    }
}
