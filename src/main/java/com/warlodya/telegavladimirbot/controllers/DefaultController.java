package com.warlodya.telegavladimirbot.controllers;

import com.warlodya.telegavladimirbot.models.ChatUser;
import com.warlodya.telegavladimirbot.models.Question;
import com.warlodya.telegavladimirbot.models.Session;
import com.warlodya.telegavladimirbot.repositories.ChatUserRepository;
import com.warlodya.telegavladimirbot.repositories.QuestionRepository;
import com.warlodya.telegavladimirbot.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class DefaultController {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ChatUserRepository chatUserRepository;
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
    public List<ChatUser> users() {
        List<ChatUser> list = new LinkedList<>();
        chatUserRepository.findAll().forEach(list::add);
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
        long count = chatUserRepository.count();
        chatUserRepository.deleteAll();
        return count;
    }

    @GetMapping("/clearSessions")
    public long clearSession() {
        long count = sessionRepository.count();
        sessionRepository.deleteAll();
        return count;
    }

    @GetMapping("/dropAll")
    public boolean dropAll() {
        mongo.getDb().drop();
        return true;
    }

    @GetMapping("/test")
    public String test() {
        return "ok";
    }
}
