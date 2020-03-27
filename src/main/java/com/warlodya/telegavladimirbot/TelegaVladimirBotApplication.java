package com.warlodya.telegavladimirbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
//@EnableScheduling
public class TelegaVladimirBotApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(TelegaVladimirBotApplication.class, args);
    }
}
