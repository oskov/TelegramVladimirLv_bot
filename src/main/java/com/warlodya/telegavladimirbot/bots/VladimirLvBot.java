package com.warlodya.telegavladimirbot.bots;

import com.warlodya.telegavladimirbot.SessionManager;
import com.warlodya.telegavladimirbot.models.Action;
import com.warlodya.telegavladimirbot.models.ChatUser;
import com.warlodya.telegavladimirbot.models.Question;
import com.warlodya.telegavladimirbot.models.Session;
import com.warlodya.telegavladimirbot.repositories.ChatUserRepository;
import com.warlodya.telegavladimirbot.services.ActionOrTruthService;
import com.warlodya.telegavladimirbot.services.AnekdotService;
import com.warlodya.telegavladimirbot.services.AuthorNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.toggle.AbilityToggle;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.*;
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
    private ActionOrTruthService actionOrTruthService;
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
        if (!processUpdate(update)) {
            super.onUpdateReceived(update);
        }
    }

    //TODO: refactor hardcoding
    public boolean processUpdate(Update update) {
        Message message = update.getMessage();
        Optional<Session> session = sessionManager.getSessionForUser(message.getFrom(), message.getChatId());

        if (session.isPresent() && session.get().getChatUser().getChatId() == message.getChatId()) {
            String output;
            if (message.getText().matches("/действие")) {
                Action action = actionOrTruthService.getRandomAction();
                output = "Действие от " + action.author + " : " + action.text;
                silent.send(output, message.getChatId());
                sessionManager.clearSessionForUser(message.getFrom(), message.getChatId());
                return true;
            } else if (message.getText().matches("/правда")) {
                Question question = actionOrTruthService.getRandomQuestion();
                output = "Вопрос от " + question.author + " : " + question.text;
                silent.send(output, message.getChatId());
                sessionManager.clearSessionForUser(message.getFrom(), message.getChatId());
                return true;
            }
        }
        if (message.getText() != null) {
            if (message.getText().matches("/addQuestion .+[?]$")) {
                //TODO: change harcoded substring to normal regex;
                String text = message.getText().substring(13);
                Question question = new Question(text, nameService.getAuthorName(update.getMessage().getFrom()));
                actionOrTruthService.addQuestion(question);
                silent.send("Вопрос добавлен: " + question.text, update.getMessage().getChatId());
                return true;
            } else if (message.getText().matches("/addAction .+[!]$")) {
                //TODO: change harcoded substring to normal regex;
                String text = message.getText().substring(11);
                Action action = new Action(text, nameService.getAuthorName(update.getMessage().getFrom()));
                actionOrTruthService.addAction(action);
                silent.send("Действие добавлено: " + action.text, update.getMessage().getChatId());
                return true;
            }
        }
        return false;
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
                .action(ctx -> silent.send(getUsers(ctx.chatId())
                        .stream()
                        .map(chatUser -> nameService.getAuthorName(chatUser.getUser()))
                        .collect(Collectors.joining(" , ")), ctx.chatId()))
                .build();
    }

    //TODO Refactor
    public Ability say() {

        return Ability
                .builder()
                .name("вопрос")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send(actionOrTruthService.getRandomQuestion().toString(), ctx.chatId()))
                .build();
    }

    private String giveStats(String name) {
        Random r = new Random();
        return name + " : " + "Сила: " + r.nextInt(9) + " Ловкость: " + r.nextInt(9) + " Остальное: " + r.nextInt(9);
    }

    public Ability stats() {

        return Ability
                .builder()
                .name("stats")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> {
                    List<ChatUser> list = new ArrayList<>();
                    chatUserRepository.findAll().forEach(list::add);
                    StringBuilder sb = new StringBuilder();
                    sb.append("Треугольник петя : Сила: 10 Ловкость: 10 Остальное: 10");
                    list.forEach(user -> sb.append('\n').append(giveStats(nameService.getAuthorName(user.getUser()))));
                    silent.send(sb.toString(), ctx.chatId());
                })
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

    private List<ChatUser> getUsers(long chatId) {
        Iterable<ChatUser> userIterator = chatUserRepository.findAll();
        List<ChatUser> users = new LinkedList<>();
        for (ChatUser user : userIterator) {
            users.add(user);
        }
        users.removeIf(chatUser -> chatUser.getChatId() != chatId);
        return users;
    }

    //TODO: refactor
    private User getRandomUser(long chatId) {
        Random generator = new Random();
        List<ChatUser> users = getUsers();
        users.removeIf(chatUser -> chatUser.getChatId() != chatId);
        return users.get(generator.nextInt(users.size())).getUser();
    }

    // Once per 90 minutes
//    @Scheduled(fixedRate = 1000 * 60 * 90)
//    public void executeActionOrTruth() {
//        User user = getRandomUser(getMainChatId());
//        actionOrTruthService.startGameForUser(user, getMainChatId());
//        silent.send(nameService.getAuthorName(user) + " /правда или /действие", getMainChatId());
//    }

    @Override
    public int creatorId() {
        return 758056390;
    }

    //TODO: save this value in database
    public long getMainChatId() {
        return -332738460;
    }

}
