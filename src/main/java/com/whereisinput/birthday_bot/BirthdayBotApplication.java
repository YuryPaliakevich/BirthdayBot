package com.whereisinput.birthday_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.whereisinput.birthday_bot.service.TelegramService;

@SpringBootApplication
public class BirthdayBotApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext run = SpringApplication.run(BirthdayBotApplication.class, args);
        run.getBean(TelegramService.class).sendMessage("message");
    }

}
