package com.warlodya.telegavladimirbot.services;

import com.warlodya.telegavladimirbot.SessionManager;
import com.warlodya.telegavladimirbot.models.Action;
import com.warlodya.telegavladimirbot.models.Question;
import com.warlodya.telegavladimirbot.models.Session;
import com.warlodya.telegavladimirbot.repositories.ActionRepository;
import com.warlodya.telegavladimirbot.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class ActionOrTruthService {
    @Autowired
    private ActionRepository actionRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private SessionManager sessionManager;

    public void startGameForUser(User user, long chatId) {
        LocalDateTime dateTime = LocalDateTime.now().plus(Duration.of(10, ChronoUnit.MINUTES));
        Date expirationDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        Session session = new Session(user, chatId, expirationDate, new HashMap<>());
        sessionManager.saveSessionForUser(session);
    }

    public void addQuestion(Question question) {
        questionRepository.save(question);
    }

    public void addAction(Action action) {
        actionRepository.save(action);
    }

    public Question getRandomQuestion() {
        Random random = new Random();
        Iterable<Question> iterable = questionRepository.findAll();
        List<Question> questions = new LinkedList<>();
        for (Question question : iterable) {
            questions.add(question);
        }
        return questions.get(random.nextInt(questions.size()));
    }

    public Action getRandomAction() {
        Random random = new Random();
        Iterable<Action> iterable = actionRepository.findAll();
        List<Action> actions = new LinkedList<>();
        for (Action action : iterable) {
            actions.add(action);
        }
        return actions.get(random.nextInt(actions.size()));
    }
}
