package com.warlodya.telegavladimirbot.repositories;

import com.warlodya.telegavladimirbot.models.Session;
import com.warlodya.telegavladimirbot.models.UserChatSession;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, UserChatSession> {
}
