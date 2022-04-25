package com.whereisinput.birthday_bot;

import static com.whereisinput.birthday_bot.config.TelegramConfig.CHAT_ID;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.whereisinput.birthday_bot.service.TelegramService;

@SpringBootApplication
public class BirthdayBotApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext run = SpringApplication.run(BirthdayBotApplication.class, args);
        final TelegramBot bot = run.getBean(TelegramBot.class);
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(new InlineKeyboardButton("url").url("www.google.com"),
                //catch callback data from updates to get kinda listener
                new InlineKeyboardButton("callback_data").callbackData("INITIAL"),
                new InlineKeyboardButton("Switch!").switchInlineQuery("switch_inline_query"));
        bot.execute(new SendMessage(CHAT_ID, "aaa").replyMarkup(inlineKeyboard), new Callback<SendMessage, SendResponse>() {
            @Override
            public void onResponse(SendMessage request, SendResponse response) {
                System.out.println(response.message().toString());
            }

            @Override
            public void onFailure(SendMessage request, IOException e) {

            }
        });
    }

}
