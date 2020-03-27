package com.warlodya.telegavladimirbot.bots;

import com.warlodya.telegavladimirbot.services.CovidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.toggle.AbilityToggle;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

@Component
public class CovidInfoBot extends AbilityBot {

    public static final String BOT_USERNAME = "COVID";

    @Autowired
    private CovidService covidService;

    public CovidInfoBot(@Value("${COVID_TOKEN}") String botToken,
                        @Qualifier("CovidBotToggle") AbilityToggle toggle) {
        super(botToken, BOT_USERNAME, toggle);
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

    @Override
    public int creatorId() {
        return 758056390;
    }
}
