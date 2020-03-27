package com.warlodya.telegavladimirbot.repositories;

import com.warlodya.telegavladimirbot.models.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Integer> {
}
