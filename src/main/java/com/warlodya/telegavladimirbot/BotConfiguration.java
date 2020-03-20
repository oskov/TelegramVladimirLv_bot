package com.warlodya.telegavladimirbot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.List;

@Configuration
public class BotConfiguration {

    @Bean
    public TelegramBot vladimirLvBot() {
        System.out.println("Create bot with TOKEN" + Constants.TOKEN);
        return new TelegramBot.Builder(Constants.TOKEN).debug().build();
    }

    @Bean
    public List<BotAction> vladimirLvAction() {
        List<BotAction> list = new LinkedList<>();

        list.add(new BotAction.BotActionBuilder()
                .predicate(update -> update.message().text() != null && update.message().text().equals("/покурить"))
                .action((Update update) -> {
                    String firstName = update.message().from().firstName() != null ? update.message().from().firstName() : "";
                    String lastName = update.message().from().lastName() != null ? update.message().from().lastName() : "";
                    String author = firstName + " " + lastName;
                    return new SendMessage(update.message().chat().id(), "Держи сижку \uD83D\uDEAC " + author);
                })
                .build());

        return list;
    }
}
