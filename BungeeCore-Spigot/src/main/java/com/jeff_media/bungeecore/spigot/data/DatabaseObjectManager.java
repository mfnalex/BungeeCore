package com.jeff_media.bungeecore.spigot.data;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface DatabaseObjectManager<T extends DatabaseObject> {

    CompletableFuture<T> load(UUID uuid);
}
