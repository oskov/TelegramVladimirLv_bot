package com.warlodya.telegavladimirbot;

import com.warlodya.telegavladimirbot.models.ChatUser;
import com.warlodya.telegavladimirbot.models.Session;
import com.warlodya.telegavladimirbot.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Date;
import java.util.Optional;

@Component
public class SessionManager {
    private final SessionRepository sessionRepository;

    @Autowired
    public SessionManager(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Optional<Session> getSessionForUser(User user, long chatId) {
        ChatUser chatUser = ChatUser.getFrom(user, chatId);
        Optional<Session> session = sessionRepository.findById(chatUser);
        if (session.isPresent()) {
            if (session.get().getExpirationDate().before(new Date())) {
                clearSessionForUser(chatUser);
                session = Optional.empty();
            }
        }
        return session;
    }

    public void saveSessionForUser(Session session) {
        sessionRepository.save(session);
    }

    public void clearSessionForUser(ChatUser chatUser) {
        sessionRepository.deleteById(chatUser);
    }

    public void clearSessionForUser(User user, long chatId) {
        sessionRepository.deleteById(ChatUser.getFrom(user, chatId));
    }
}
