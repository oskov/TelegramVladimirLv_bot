package com.warlodya.telegavladimirbot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class VladimirBot {

    private TelegramBot bot;
    private List<BotAction> actions;

    private int offset;

    @Autowired
    public VladimirBot(TelegramBot bot, List<BotAction> actions) {
        this.bot = bot;
        this.actions = actions;
    }

    private void init() {
        GetUpdates getUpdates = new GetUpdates().allowedUpdates("message").limit(100).offset(0).timeout(0);
        GetUpdatesResponse updatesResponse = bot.execute(getUpdates);
        List<Update> updates = updatesResponse.updates();
        if (updates.size() == 0) {
            offset = 0;
        } else {
            offset = updates.get(updates.size() - 1).updateId() + 1;
        }
    }

    @Scheduled(fixedRate = 5000)
    public void fetchUpdates() {
        GetUpdates getUpdates = new GetUpdates().allowedUpdates("message").limit(10).offset(offset).timeout(0);
        GetUpdatesResponse updatesResponse = bot.execute(getUpdates);
        List<Update> updates = updatesResponse.updates();
        updates.forEach(this::processUpdate);
        if (updates.size() != 0) {
            offset = updates.get(updates.size() - 1).updateId() + 1;
        }
    }

    private void processUpdate(Update update) {
        for (BotAction action : actions) {
            if (action.test(update)) {
                bot.execute(action.apply(update));
            }
        }
    }
}
