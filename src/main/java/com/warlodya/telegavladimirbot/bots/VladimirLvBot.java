package com.warlodya.telegavladimirbot.bots;

import com.warlodya.telegavladimirbot.services.AnekdotService;
import com.warlodya.telegavladimirbot.services.AuthorNameService;
import com.warlodya.telegavladimirbot.services.CovidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.toggle.AbilityToggle;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Date;
import java.util.Random;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

@Component
public class VladimirLvBot extends AbilityBot {

    public static final String BOT_USERNAME = "Vladimir";

    @Autowired
    private AuthorNameService nameService;
    @Autowired
    private AnekdotService anekdotService;
    @Autowired
    private CovidService covidService;

    public VladimirLvBot(@Value("${VLADIMIR_TOKEN}") String botToken,
                         @Qualifier("VladimirLvBotToggle") AbilityToggle toggle) {
        super(botToken, BOT_USERNAME, toggle);
    }

    public Ability sayHello() {
        return Ability
                .builder()
                .name("hello")
                .info("says hello!")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("Hello! " + nameService.getAuthorName(ctx), ctx.chatId()))
                .build();
    }

    public Ability sayGoodbye() {
        return Ability
                .builder()
                .name("bye")
                .info("says bye!")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("Bye! " + nameService.getAuthorName(ctx), ctx.chatId()))
                .build();
    }

    public Ability smoke() {
        return Ability
                .builder()
                .name("покурить")
                .info("даёт сижку")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("Держи сижку \uD83D\uDEAC " + nameService.getAuthorName(ctx), ctx.chatId()))
                .build();
    }

    public Ability joke() {
        return Ability
                .builder()
                .name("анекдот")
                .info("даёт анекдот")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send(anekdotService.getAnekdot(), ctx.chatId()))
                .build();
    }

    public Ability time() {
        return Ability
                .builder()
                .name("время")
                .info("показывает время и дату")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("Время тебя ебать, а еще " + new Date(), ctx.chatId()))
                .build();
    }

    public Ability covid() {
        return Ability
                .builder()
                .name("covid")
                .info("показывает сколько больных в Латвии")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("Больных в Латвии: " + covidService.getCovidCount(), ctx.chatId()))
                .build();
    }

    private User getRandomUser() {
        Random generator = new Random();
        Object[] values = users().values().toArray();
        Object randomValue = values[generator.nextInt(values.length)];
        return (User) randomValue;
    }

    public Ability shot() {
        return Ability
                .builder()
                .name("shot")
                .info("убивает случайного пользователя")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send(nameService.getAuthorName(getRandomUser()) + " умер", ctx.chatId()))
                .build();
    }

    @Override
    public int creatorId() {
        return 758056390;
    }
}
