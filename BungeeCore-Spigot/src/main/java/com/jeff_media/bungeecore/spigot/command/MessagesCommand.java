package com.jeff_media.bungeecore.spigot.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import com.jeff_media.bungeecore.spigot.BungeeCoreSpigot;
import com.jeff_media.bungeecore.spigot.Utils;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;


@CommandAlias("messages")
@CommandPermission("simp.messages.admin")
public class MessagesCommand extends BaseCommand {

    private final BungeeCoreSpigot plugin;

    public MessagesCommand(BungeeCoreSpigot plugin) {
        this.plugin = plugin;
    }

    @Subcommand("leave")
    @CommandAlias("leavemessage")
    public void leave(Player player, @Optional String message) {
        if (message == null) {
            player.sendMessage(plugin.messageDataManager().getLeaveMessage(player));
        } else if (message.equals("reset")) {
            player.getPersistentDataContainer().remove(plugin.leaveMessageKey());
            player.sendMessage(Utils.format("<green>Your leave message has been reset!"));
        } else {
            String leaveMessage = message;
            for (Map.Entry entry : BungeeCoreSpigot.validNameValues().entrySet()) leaveMessage = leaveMessage.replace((String) entry.getKey(), (String)entry.getValue());
            plugin.setLeaveMessage(player, message);
            player.sendMessage(Utils.format("<green>Your new leave message is</green>: ") + Utils.format(leaveMessage));
        }
    }

    @Subcommand("join")
    @CommandAlias("joinmessage")
    public void join(Player player, @Optional String message) {
        if (message == null) {
            player.sendMessage(plugin.messageDataManager().getJoinMessage(player));
        } else if (message.equals("reset")) {
            player.getPersistentDataContainer().remove(plugin.joinMessageKey());
            player.sendMessage(Utils.format("<green>Your join message has been reset!"));
        } else {
            String joinMessage = message;
            for (Map.Entry entry : BungeeCoreSpigot.validNameValues().entrySet()) joinMessage = joinMessage.replace((String) entry.getKey(), (String) entry.getValue());
            plugin.setJoinMessage(player, joinMessage);
            player.sendMessage(Utils.format("<green>Your new join message is</green>: ") + Utils.format(joinMessage));
        }
    }
}
