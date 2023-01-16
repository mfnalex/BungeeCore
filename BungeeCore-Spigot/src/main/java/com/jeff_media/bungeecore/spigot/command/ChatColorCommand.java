package com.jeff_media.bungeecore.spigot.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.jeff_media.bungeecore.spigot.BungeeCoreSpigot;
import com.jeff_media.bungeecore.spigot.Utils;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@CommandAlias("chatcolor")
public class ChatColorCommand extends BaseCommand {

    private final BungeeCoreSpigot plugin;
    
    private final Pattern HEX_PATTERN = BungeeCoreSpigot.hexPattern();
    private final Map<String, String> VALID_NAMES = BungeeCoreSpigot.validNameValues();

    @Default
    public void onCommand(Player player, String color) {
        boolean isColorValid = HEX_PATTERN.matcher(color).matches() || VALID_NAMES.containsKey(color);
        boolean isColorAName = VALID_NAMES.containsKey(color);
        if (!isColorValid) {
            player.sendMessage(Utils.format(plugin.getConfig().getString("messages.incorrect-color")));
            return;
        }
        plugin.setChatColor1(player, isColorAName ? VALID_NAMES.get(color) : color);
        plugin.setChatColor2(player, isColorAName ? VALID_NAMES.get(color) : color);
        sendMessage(player, isColorAName ? VALID_NAMES.get(color) : color, isColorAName ? VALID_NAMES.get(color) : color);
    }

    @Default
    public void onCommand(Player player, String color1, String color2) {
        boolean isColor1Valid = HEX_PATTERN.matcher(color1).matches() || VALID_NAMES.containsKey(color1);
        boolean isColor2Valid = HEX_PATTERN.matcher(color2).matches() || VALID_NAMES.containsKey(color2);
        boolean isColor1AName = VALID_NAMES.containsKey(color1);
        boolean isColor2AName = VALID_NAMES.containsKey(color2);
        if (!isColor1Valid || !isColor2Valid) {
            player.sendMessage(Utils.format(plugin.getConfig().getString("messages.incorrect-color")));
            return;
        }
        plugin.setChatColor1(player, isColor1AName ? VALID_NAMES.get(color1) : color1);
        plugin.setChatColor2(player, isColor2AName ? VALID_NAMES.get(color2) : color2);
        sendMessage(player, isColor1AName ? VALID_NAMES.get(color1) : color1, isColor2AName ? VALID_NAMES.get(color2) : color2);
    }

    private void sendMessage(Player player, String color1, String color2) {
        player.sendMessage(Utils.format("<gradient:" + color1 + ":" + color2 + ">This is a test message</gradient>"));
    }

}
