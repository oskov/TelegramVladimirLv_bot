package com.warlodya.telegavladimirbot.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Date;
import java.util.Map;

@Document
public class Session {
    @Id
    private final ChatUser chatUser;
    private final Date expirationDate;
    private final Map<String, Object> map;

    public Session(User user, long chatId, Date expirationDate, Map<String, Object> map) {
        this.chatUser = new ChatUser(user, chatId);
        this.expirationDate = expirationDate;
        this.map = map;
    }

    @PersistenceConstructor
    public Session(ChatUser chatUser, Date expirationDate, Map<String, Object> map) {
        this.chatUser = chatUser;
        this.expirationDate = expirationDate;
        this.map = map;
    }

    public ChatUser getChatUser() {
        return chatUser;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public Map<String, Object> getMap() {
        return map;
    }
}
