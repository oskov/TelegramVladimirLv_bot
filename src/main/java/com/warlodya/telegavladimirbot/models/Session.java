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
    private UserChatSession userChatSession;
    private Date expirationDate;
    private Map<String, Object> map;

    public Session(User user, long chatId, Date expirationDate, Map<String, Object> map) {
        this.userChatSession = new UserChatSession(user, chatId);
        this.expirationDate = expirationDate;
        this.map = map;
    }

    @PersistenceConstructor
    public Session(UserChatSession userChatSession, Date expirationDate, Map<String, Object> map) {
        this.userChatSession = userChatSession;
        this.expirationDate = expirationDate;
        this.map = map;
    }

    public UserChatSession getUserChatSession() {
        return userChatSession;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public Map<String, Object> getMap() {
        return map;
    }
}
