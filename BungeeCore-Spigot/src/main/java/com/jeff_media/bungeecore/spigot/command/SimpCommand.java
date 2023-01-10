package com.jeff_media.bungeecore.spigot.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import com.jeff_media.bungeecore.spigot.BungeeCoreSpigot;
import com.jeff_media.jefflib.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;

@CommandAlias("simp")
@RequiredArgsConstructor
public class SimpCommand extends BaseCommand {

    private final BungeeCoreSpigot plugin;

    @Subcommand("reload")
    @CommandPermission("simp.reload")
    public void reload(CommandSender sender) {
        TimeUtils.startTimings("reload");
        plugin.config().reload();
        long nanoSeconds = TimeUtils.endTimings("reload");
        sender.sendMessage("Reloaded in " + TimeUtils.formatNanoseconds(nanoSeconds));
    }

}
