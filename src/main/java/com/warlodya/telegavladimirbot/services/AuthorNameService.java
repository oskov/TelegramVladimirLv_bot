package com.warlodya.telegavladimirbot.services;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
@Scope
public class AuthorNameService {
    public String getAuthorName(MessageContext context) {
        return getAuthorName(context.user());
    }

    public String getAuthorName(User user) {
        String fName = user.getFirstName();
        String lName = user.getLastName();
        String userName = user.getUserName();

        String authorName;
        if (userName != null) {
            authorName = userName;
        } else {
            String firstName = fName != null ? fName : "";
            String lastName = lName != null ? lName : "";
            authorName = firstName + " " + lastName;
        }
        return authorName;
    }
}
