package com.jeff_media.bungeecore.spigot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BungeeCoreSpigot extends JavaPlugin {

    @Override
    public void onEnable() {
        registerPlaceholders();
    }

    private void registerPlaceholders() {
        if(getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new BungeeCorePlaceholderExpansion(this).register();
        }
    }

}
