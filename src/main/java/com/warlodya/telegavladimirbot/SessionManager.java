package com.warlodya.telegavladimirbot;

import com.warlodya.telegavladimirbot.models.Session;
import com.warlodya.telegavladimirbot.models.UserChatSession;
import com.warlodya.telegavladimirbot.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Date;
import java.util.Optional;

@Component
public class SessionManager {
    @Autowired
    private SessionRepository sessionRepository;

    public Optional<Session> getSessionForUser(User user, long chatId) {
        UserChatSession userChatSession = UserChatSession.getFrom(user, chatId);
        Optional<Session> session = sessionRepository.findById(userChatSession);
        if (session.isPresent()) {
            if (session.get().getExpirationDate().before(new Date())) {
                clearSessionForUser(userChatSession);
                session = Optional.empty();
            }
        }
        return session;
    }

    public void saveSessionForUser(Session session) {
        sessionRepository.save(session);
    }

    public void clearSessionForUser(UserChatSession userChatSession) {
        sessionRepository.deleteById(userChatSession);
    }

    public void clearSessionForUser(User user, long chatId) {
        sessionRepository.deleteById(UserChatSession.getFrom(user, chatId));
    }
}
