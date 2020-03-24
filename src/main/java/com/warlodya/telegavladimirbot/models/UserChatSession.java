package com.warlodya.telegavladimirbot.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.telegram.telegrambots.meta.api.objects.User;

@Document
public class UserChatSession {
    private User user;
    private long chatId;

    public UserChatSession(User user, long chatId) {
        this.user = user;
        this.chatId = chatId;
    }

    public static UserChatSession getFrom(User user, long chatId) {
        return new UserChatSession(user, chatId);
    }

    public User getUser() {
        return user;
    }

    public long getChatId() {
        return chatId;
    }
}
