package com.warlodya.telegavladimirbot.repositories;

import com.warlodya.telegavladimirbot.models.ChatUser;
import com.warlodya.telegavladimirbot.models.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, ChatUser> {
}
