package com.jeff_media.bungeecore.spigot.data;

public record MessageData(String joinMessage, String leaveMessage) {

    public MessageData(String joinMessage, String leaveMessage) {
        this.joinMessage = joinMessage;
        this.leaveMessage = leaveMessage;
    }

    public String joinMessage() {
        return joinMessage;
    }

    public String leaveMessage() {
        return leaveMessage;
    }
}
