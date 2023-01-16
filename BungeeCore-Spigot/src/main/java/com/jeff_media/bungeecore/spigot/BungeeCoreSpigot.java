package com.jeff_media.bungeecore.spigot;

import co.aikar.commands.PaperCommandManager;
import com.jeff_media.bungeecore.spigot.command.ChatColorCommand;
import com.jeff_media.bungeecore.spigot.command.MessagesCommand;
import com.jeff_media.bungeecore.spigot.command.SimpCommand;
import com.jeff_media.bungeecore.spigot.config.Config;
import com.jeff_media.bungeecore.spigot.config.MySqlConfig;
import com.jeff_media.bungeecore.spigot.data.MessageDataManager;
import com.jeff_media.bungeecore.spigot.listeners.ChatListener;
import com.jeff_media.bungeecore.spigot.listeners.MessagesListener;
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
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

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

    @Getter
    public final NamespacedKey joinMessageKey = new NamespacedKey(this, "joinmessage");
    @Getter
    public final NamespacedKey leaveMessageKey = new NamespacedKey(this, "leavemessage");

    @Getter
    private static final Pattern hexPattern = Pattern.compile("#[a-fA-F0-9]{6}");

    @Getter
    public MessageDataManager messageDataManager;

    @Getter
    private static Map<String, String> validNameValues = new HashMap<>();

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
        registerListeners();

        ConfigurationSection colorSection = getConfig().getConfigurationSection("format.colors");
        for (String key : colorSection.getKeys(false)) {
            validNameValues().put(key, colorSection.getString(key));
        }

        this.messageDataManager = new MessageDataManager(this.getConfig(), this);
    }

    private void registerCommands() {
        PaperCommandManager acf = new PaperCommandManager(this);
        acf.registerCommand(new ChatColorCommand(this));
        acf.registerCommand(new SimpCommand(this));
        acf.registerCommand(new MessagesCommand(this));

    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ChatListener(this), this);
        pluginManager.registerEvents(new MessagesListener(this), this);
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

    public void setJoinMessage(Player player, String message) {
        player.getPersistentDataContainer().set(joinMessageKey, PersistentDataType.STRING, message);
    }

    public void setLeaveMessage(Player player, String message) {
        player.getPersistentDataContainer().set(leaveMessageKey, PersistentDataType.STRING, message);
    }

}
