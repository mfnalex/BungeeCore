package com.jeff_media.bungeecore.spigot.config;

import com.jeff_media.jefflib.data.Config;
import org.jetbrains.annotations.NotNull;

public class MySqlConfig extends Config {

    public MySqlConfig() {
        super("mysql.yml");
    }

    public @NotNull String url() {
        return getString("url", "jdbc:mysql://localhost:3306/database");
    }

    public @NotNull String username() {
        return getString("username", "username");
    }

    public @NotNull String password() {
        return getString("password", "password");
    }

    public @NotNull String cachePrepStmts() {
        return getString("cachePrepStmts", "true");
    }

    public @NotNull String prepStmtCacheSize() {
        return getString("prepStmtCacheSize", "250");
    }

    public @NotNull String prepStmtCacheSqlLimit() {
        return getString("prepStmtCacheSqlLimit", "2048");
    }


}
