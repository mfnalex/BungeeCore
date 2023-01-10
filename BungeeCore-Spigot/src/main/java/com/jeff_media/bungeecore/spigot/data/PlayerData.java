package com.jeff_media.bungeecore.spigot.data;

import com.jeff_media.bungeecore.spigot.BungeeCoreSpigot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
@AllArgsConstructor
public class PlayerData implements DatabaseObject<PlayerData> {

    private final BungeeCoreSpigot plugin;

    @Nullable private String chatColor1;
    @Nullable private String chatColor2;

    private static final String CREATE = "CREATE TABLE IF NOT EXISTS simp_player_data(uuid varchar(36) PRIMARY KEY, chat_color_1 VARCHAR(6), chat_color_2 VARCHAR(6)";
    private static final String INSERT = "INSERT INTO simp_player_data (uuid, chat_color_1, chat_color_2) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE uuid=?";
    private static final String SELECT = "SELECT * FROM simp_player_data WHERE uuid=?";
    private static final String UPDATE = "UPDATE simp_player_data SET chat_color_1=?, chat_color_2=? WHERE uuid=?";


    public void createTable() {
        try (Connection connection = plugin.dataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @RequiredArgsConstructor
    public static class PlayerDataManager implements DatabaseObjectManager<PlayerData> {

        private final BungeeCoreSpigot plugin;

        @Override
        public CompletableFuture<PlayerData> load(UUID uuid) {
            return CompletableFuture.supplyAsync(() -> {
                try (Connection connection = plugin.dataSource().getConnection();
                     PreparedStatement insert = connection.prepareStatement(INSERT);
                     PreparedStatement select = connection.prepareStatement(SELECT)) {
                    insert.setString(1, uuid.toString());
                    insert.setString(2, null);
                    insert.setString(3, null);
                    insert.execute();

                    select.setString(1, uuid.toString());
                    ResultSet result = select.executeQuery();
                    if (result.next()) {
                        return new PlayerData(plugin, result.getString("chat_color_1"), result.getString("chat_color_2"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            });
        }
    }
}
