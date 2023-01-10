package com.jeff_media.bungeecore.spigot.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.jeff_media.bungeecore.spigot.BungeeCoreSpigot;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@CommandAlias("chatcolor")
public class ChatColorCommand extends BaseCommand {

    private final BungeeCoreSpigot plugin;

    @Default
    public void onCommand(Player player, String color) {
        plugin.setChatColor1(player, color);
        plugin.setChatColor2(player, color);
        sendMessage(player, color, color);
    }

    @Default
    public void onCommand(Player player, String color1, String color2) {
        plugin.setChatColor1(player, color1);
        plugin.setChatColor2(player, color2);
        sendMessage(player, color1, color2);
    }

    private final void sendMessage(Player player, String color1, String color2) {
        Component parsed = plugin.miniMessage().deserialize("<gradient:" + color1 + ":" + color2 + ">This is a test message</gradient>");
        plugin.adventure().player(player).sendMessage(parsed);
    }

}
