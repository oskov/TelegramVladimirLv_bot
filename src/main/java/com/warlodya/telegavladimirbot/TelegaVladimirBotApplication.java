package com.warlodya.telegavladimirbot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.DeleteWebhook;
import com.pengrad.telegrambot.request.SetWebhook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TelegaVladimirBotApplication {

	public static TelegramBot bot;

	public static void main(String[] args) {
		bot = new TelegramBot.Builder(Constants.TOKEN).debug().build();
		SpringApplication.run(TelegaVladimirBotApplication.class, args);
	}

}
