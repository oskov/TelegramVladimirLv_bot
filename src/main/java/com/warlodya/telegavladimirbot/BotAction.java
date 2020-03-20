package com.warlodya.telegavladimirbot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.function.Function;
import java.util.function.Predicate;

public class BotAction implements Predicate<Update>, Function<Update, BaseRequest> {
    private Predicate<Update> predicate;
    private Function<Update, BaseRequest> action;

    private BotAction(Predicate<Update> predicate, Function<Update, BaseRequest> action) {
        this.predicate = predicate;
        this.action = action;
    }

    @Override
    public boolean test(Update update) {
        return predicate.test(update);
    }

    @Override
    public BaseRequest apply(Update update) {
        return action.apply(update);
    }

    public static final class BotActionBuilder {
        private Predicate<Update> predicate;
        private Function<Update, BaseRequest> action;

        public BotActionBuilder () {
            predicate = (update -> !update.message().from().isBot());
            action = (update -> new SendMessage(update.message().chat().id(), "pong"));
        }

        public BotActionBuilder predicate(Predicate<Update> predicate) {
            this.predicate = predicate;
            return this;
        }

        public BotActionBuilder action(Function<Update, BaseRequest> action) {
            this.action = action;
            return this;
        }

        public BotAction build() {
            return new BotAction(predicate, action);
        }
    }
}
