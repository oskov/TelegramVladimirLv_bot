package com.warlodya.telegavladimirbot.repositories;

import com.warlodya.telegavladimirbot.models.ChatUser;
import org.springframework.data.repository.CrudRepository;
import org.telegram.telegrambots.meta.api.objects.User;

public interface ChatUserRepository extends CrudRepository<ChatUser, User> {
}
