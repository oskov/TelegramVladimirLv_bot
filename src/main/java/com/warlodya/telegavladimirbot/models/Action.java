package com.warlodya.telegavladimirbot.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Action {
    public String text;
    public String author;

    public Action(String text, String author) {
        this.text = text;
        this.author = author;
    }
}
