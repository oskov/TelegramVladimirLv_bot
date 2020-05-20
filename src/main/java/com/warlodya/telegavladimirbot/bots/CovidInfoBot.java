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
                .info("Confirmed cases in Latvia")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send(covidService.getCovidCountForCountry("Latvia"), ctx.chatId()))
                .build();
    }

    public Ability countries() {
        return Ability
                .builder()
                .name("countries")
                .info("Available counties for data request")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send(String.join("\n", covidService.getCountrySet()), ctx.chatId()))
                .build();
    }

    public Ability confirmed() {
        return Ability
                .builder()
                .name("topconfirmed")
                .info("Top 10 countries by confirmed cases")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send(covidService.getTopConfirmed(), ctx.chatId()))
                .build();
    }

    public Ability deaths() {
        return Ability
                .builder()
                .name("topdeaths")
                .info("Top 10 countries by death cases")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send(covidService.getTopDeaths(), ctx.chatId()))
                .build();
    }

    public Ability covidForCountry() {
        return Ability
                .builder()
                .name("covidin")
                .info("Covid data for country. Example: /covidIn Latvia")
                .locality(ALL)
                .privacy(PUBLIC)
                .input(1)
                .action(ctx -> silent.send(covidService.getCovidCountForCountry(ctx.firstArg()), ctx.chatId()))
                .build();
    }

    public Ability weekReport() {
        return Ability
                .builder()
                .name("weekreportfor")
                .info("Covid data for country. Example: /weekReportFor Latvia")
                .locality(ALL)
                .privacy(PUBLIC)
                .input(1)
                .action(ctx -> silent.send(covidService.generateWeekReportForCountry(ctx.firstArg()), ctx.chatId()))
                .build();
    }

    @Override
    public int creatorId() {
        return 758056390;
    }
}
