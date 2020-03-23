package com.warlodya.telegavladimirbot.services;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.objects.MessageContext;

@Component
@Scope
public class AuthorNameService {
    public String getAuthorName(MessageContext context) {
        String fName = context.user().getFirstName();
        String lName = context.user().getLastName();
        String userName = context.user().getUserName();

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
