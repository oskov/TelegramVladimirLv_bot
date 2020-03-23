package com.warlodya.telegavladimirbot.bots;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.abilitybots.api.toggle.AbilityToggle;
import org.telegram.abilitybots.api.toggle.CustomToggle;

@Configuration
public class VladimirLvBotConfiguration {

    @Bean(name = "VladimirLvBotToggle")
    public AbilityToggle getVladimirLvBotToggle() {
        return new CustomToggle()
                .turnOff("promote")
                .turnOff("demote")
                .turnOff("ban")
                .turnOff("unban")
//                .turnOff("report")
                .toggle("report", "команды")
                .turnOff("claim")
                .turnOff("backup")
                .turnOff("recover");
    }
}
