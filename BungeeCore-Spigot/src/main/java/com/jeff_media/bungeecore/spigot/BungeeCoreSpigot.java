package com.jeff_media.bungeecore.spigot;

import co.aikar.commands.PaperCommandManager;
import com.jeff_media.bungeecore.spigot.command.ChatColorCommand;
import com.jeff_media.bungeecore.spigot.command.SimpCommand;
import com.jeff_media.bungeecore.spigot.config.Config;
import com.jeff_media.bungeecore.spigot.config.MySqlConfig;
import com.jeff_media.jefflib.JeffLib;
import com.jeff_media.jefflib.exceptions.NMSNotSupportedException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@Accessors(fluent = true)
public class BungeeCoreSpigot extends JavaPlugin {

    {
        JeffLib.init(this);
    }

    @Nullable
    private BukkitAudiences adventure;

    @NotNull
    @Getter
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    @NotNull
    @Getter
    private final Config config = new Config();

    private final MySqlConfig mySqlConfig = new MySqlConfig();

    @NotNull
    @Getter
    private final HikariDataSource dataSource;

    private final NamespacedKey chatColor1Key = new NamespacedKey(this, "chatcolor1");
    private final NamespacedKey chatColor2Key = new NamespacedKey(this, "chatcolor2");

    {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(mySqlConfig.url());
        hikariConfig.setUsername(mySqlConfig.username());
        hikariConfig.setPassword(mySqlConfig.password());
        hikariConfig.addDataSourceProperty("cachePrepStmts", mySqlConfig.cachePrepStmts());
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", mySqlConfig.prepStmtCacheSize());
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", mySqlConfig.prepStmtCacheSqlLimit());

        dataSource = new HikariDataSource(hikariConfig);
        if(dataSource.isRunning()) {
            getLogger().info("Connected to MySQL database");
        } else {
            getLogger().warning("Failed to connect to MySQL database");
        }
    }


    @Override
    public void onEnable() {

        adventure = BukkitAudiences.create(this);
        registerPlaceholders();

        try {
            JeffLib.enableNMS();
        } catch (NMSNotSupportedException exception) {
            getLogger().severe("NMS is not supported on this version of Minecraft. Please update JeffLib to the latest version:");
            exception.printStackTrace();
        }

        registerCommands();

    }

    private void registerCommands() {
        PaperCommandManager acf = new PaperCommandManager(this);
        acf.registerCommand(new ChatColorCommand(this));
        acf.registerCommand(new SimpCommand(this));

    }

    private void registerPlaceholders() {
        if(getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PAPIPlaceholders(this).register();
        } else {
            getLogger().severe("PlaceholderAPI is not installed or not enabled. Placeholders will not work.");
        }
    }

    public @NonNull BukkitAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    public @NonNull Component miniMessage(String miniMessage) {
        return this.miniMessage.deserialize(miniMessage);
    }

    public @NonNull String getChatColor1(Player player) {
        return player.getPersistentDataContainer().getOrDefault(chatColor1Key, PersistentDataType.STRING, config().defaultChatColor1());
    }

    public @NonNull String getChatColor2(Player player) {
        return player.getPersistentDataContainer().getOrDefault(chatColor2Key, PersistentDataType.STRING, config().defaultChatColor2());
    }

    public void setChatColor1(Player player, String color) {
        player.getPersistentDataContainer().set(chatColor1Key, PersistentDataType.STRING, color);
    }

    public void setChatColor2(Player player, String color) {
        player.getPersistentDataContainer().set(chatColor2Key, PersistentDataType.STRING, color);
    }

}
