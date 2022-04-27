package com.whereisinput.birthday_bot;

import static com.whereisinput.birthday_bot.config.TelegramConfig.RequestCallbackID.INITIAL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.whereisinput.birthday_bot.service.TelegramServiceFacade;

@SpringBootApplication
public class BirthdayBotApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext run = SpringApplication.run(BirthdayBotApplication.class, args);
        final TelegramServiceFacade telegramServiceFacade = run.getBean(TelegramServiceFacade.class);
        telegramServiceFacade.sendMessage(INITIAL);
    }

}
