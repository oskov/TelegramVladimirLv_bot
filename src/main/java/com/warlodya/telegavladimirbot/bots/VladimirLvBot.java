package com.warlodya.telegavladimirbot.bots;

import com.warlodya.telegavladimirbot.repositories.UserRepository;
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
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
    @Autowired
    private UserRepository userRepository;

    public VladimirLvBot(@Value("${VLADIMIR_TOKEN}") String botToken,
                         @Qualifier("VladimirLvBotToggle") AbilityToggle toggle) {
        super(botToken, BOT_USERNAME, toggle);
    }

    @Override
    public void onUpdateReceived(Update update) {
        userRepository.save(update.getMessage().getFrom());
        super.onUpdateReceived(update);
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

    private List<User> getUsers() {
        Iterable<User> userIterator = userRepository.findAll();
        List<User> users = new LinkedList<>();
        for (User user : userIterator) {
            users.add(user);
        }
        return users;
    }

    private User getRandomUser() {
        Random generator = new Random();
        List<User> users = getUsers();
        return users.get(generator.nextInt(users.size()));
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

    public Ability userList() {
        return Ability
                .builder()
                .name("users")
                .info("известные боту пользователи")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send(getUsers().stream().map(user -> nameService.getAuthorName(user)).collect(Collectors.joining(" ")), ctx.chatId()))
                .build();
    }

    @Override
    public int creatorId() {
        return 758056390;
    }

}
