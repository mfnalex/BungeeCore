package com.jeff_media.bungeecore.spigot.config;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

@Accessors(fluent = true)
@Getter
public class Config extends com.jeff_media.jefflib.data.Config {

    public Config() {
        super("config.yml");
    }

    public @NonNull String serverId() {
        return getString("server.id", "default-server-id");
    }

    public @NonNull String serverName() {
        return getString("server.name", "Server Name");
    }

    public @NonNull String serverDisplayName() {
        return getString("server.display-name", "<rainbow>Server Name</gradient>");
    }

    public @NonNull String defaultChatColor1() {
        return getString("chatcolors.default.1", "ffffff");
    }

    public @NonNull String defaultChatColor2() {
        return getString("chatcolors.default.2", defaultChatColor1());
    }
}
