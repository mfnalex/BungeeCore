package com.jeff_media.bungeecore.spigot.data;

import com.jeff_media.bungeecore.spigot.BungeeCoreSpigot;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class DataManager {
    private final BungeeCoreSpigot plugin;


    public CompletableFuture<PlayerData> loadData(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = plugin.dataSource().getConnection();
                 Statement statement = connection.createStatement()) {
                statement.executeUpdate(null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null; // TODO
        });
    }
}
