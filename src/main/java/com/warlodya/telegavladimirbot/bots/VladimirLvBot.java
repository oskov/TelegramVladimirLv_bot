package com.warlodya.telegavladimirbot.bots;

import com.warlodya.telegavladimirbot.SessionManager;
import com.warlodya.telegavladimirbot.models.ChatUser;
import com.warlodya.telegavladimirbot.repositories.ChatUserRepository;
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
    private ChatUserRepository chatUserRepository;
    @Autowired
    private SessionManager sessionManager;

    public VladimirLvBot(@Value("${VLADIMIR_TOKEN}") String botToken,
                         @Qualifier("VladimirLvBotToggle") AbilityToggle toggle) {
        super(botToken, BOT_USERNAME, toggle);
    }

    @Override
    public void onUpdateReceived(Update update) {
        chatUserRepository.save(ChatUser.getFrom(update.getMessage().getFrom(), update.getMessage().getChatId()));
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

    public Ability shrug() {
        return Ability
                .builder()
                .name("shrug")
                .info("¯\\_(ツ)_/¯")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("¯\\_(ツ)_/¯", ctx.chatId()))
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

    private List<ChatUser> getUsers() {
        Iterable<ChatUser> userIterator = chatUserRepository.findAll();
        List<ChatUser> users = new LinkedList<>();
        for (ChatUser user : userIterator) {
            users.add(user);
        }
        return users;
    }

    //TODO: refactor
    private User getRandomUser(long chatId) {
        Random generator = new Random();
        List<ChatUser> users = getUsers();
        users.removeIf(chatUser -> chatUser.getChatId() != chatId);
        return users.get(generator.nextInt(users.size())).getUser();
    }

    public Ability shot() {
        return Ability
                .builder()
                .name("shot")
                .info("убивает случайного пользователя")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send(nameService.getAuthorName(getRandomUser(ctx.chatId())) + " умер", ctx.chatId()))
                .build();
    }

    public Ability userList() {
        return Ability
                .builder()
                .name("users")
                .info("известные боту пользователи")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send(getUsers().stream().map(chatUser -> nameService.getAuthorName(chatUser.getUser())).collect(Collectors.joining(" , ")), ctx.chatId()))
                .build();
    }

    @Override
    public int creatorId() {
        return 758056390;
    }

    //TODO: save this value in database
    public long getMainChatId() {
        return -332738460;
    }

}
