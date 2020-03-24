package com.warlodya.telegavladimirbot.controllers;

import com.warlodya.telegavladimirbot.models.Question;
import com.warlodya.telegavladimirbot.models.Session;
import com.warlodya.telegavladimirbot.repositories.QuestionRepository;
import com.warlodya.telegavladimirbot.repositories.SessionRepository;
import com.warlodya.telegavladimirbot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.LinkedList;
import java.util.List;

@RestController
public class DefaultController {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private MongoTemplate mongo;

    @GetMapping("/questionList")
    public List<Question> questions() {
        List<Question> list = new LinkedList<>();
        questionRepository.findAll().forEach(list::add);
        return list;
    }

    @GetMapping("/userList")
    public List<User> users() {
        List<User> list = new LinkedList<>();
        userRepository.findAll().forEach(list::add);
        return list;
    }

    @GetMapping("/sessionList")
    public List<Session> sessions() {
        List<Session> list = new LinkedList<>();
        sessionRepository.findAll().forEach(list::add);
        return list;
    }

    @GetMapping("/clearQuestions")
    public long clearQuestion() {
        long count = questionRepository.count();
        questionRepository.deleteAll();
        return count;
    }

    @GetMapping("/clearUsers")
    public long clearUsers() {
        long count = userRepository.count();
        userRepository.deleteAll();
        return count;
    }

    @GetMapping("/clearSessions")
    public long clearSession() {
        long count = sessionRepository.count();
        sessionRepository.deleteAll();
        return count;
    }

    @GetMapping("/test")
    public String test() {
        return "ok";
    }
}
