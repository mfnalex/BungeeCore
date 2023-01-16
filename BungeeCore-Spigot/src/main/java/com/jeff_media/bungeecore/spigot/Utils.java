package com.jeff_media.bungeecore.spigot;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;

public class Utils {

    private static LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.builder().character('&').hexCharacter('#').hexColors().useUnusualXRepeatedCharacterHexFormat().build();

    public static String format(String toTranslate) {
        return ChatColor.translateAlternateColorCodes('&', LEGACY_SERIALIZER.serialize(MiniMessage.miniMessage().deserialize(toTranslate)));
    }
}
