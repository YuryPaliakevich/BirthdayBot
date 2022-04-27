package com.whereisinput.birthday_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.whereisinput.birthday_bot.service.TelegramServiceFacade;

@SpringBootApplication
public class BirthdayBotApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext run = SpringApplication.run(BirthdayBotApplication.class, args);
        final TelegramServiceFacade telegramServiceFacade = run.getBean(TelegramServiceFacade.class);
        telegramServiceFacade.sendMessage("INITIAL");
    }

}
