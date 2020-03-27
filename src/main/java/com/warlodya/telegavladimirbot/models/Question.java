package com.warlodya.telegavladimirbot.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Question {
    public String text;
    public String author;

    public Question(String text, String author) {
        this.text = text;
        this.author = author;
    }

}
