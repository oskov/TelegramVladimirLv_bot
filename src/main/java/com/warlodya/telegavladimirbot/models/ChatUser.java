package com.warlodya.telegavladimirbot.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.telegram.telegrambots.meta.api.objects.User;

@Document
public class ChatUser {
    @Id
    private final User user;
    private final long chatId;

    public ChatUser(User user, long chatId) {
        this.user = user;
        this.chatId = chatId;
    }

    public static ChatUser getFrom(User user, long chatId) {
        return new ChatUser(user, chatId);
    }

    public User getUser() {
        return user;
    }

    public long getChatId() {
        return chatId;
    }
}
