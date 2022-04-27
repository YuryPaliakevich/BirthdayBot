package com.whereisinput.birthday_bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pengrad.telegrambot.TelegramBot;

/**
 * Created by yuriy_polyakevich at 4/16/22
 */
@Configuration
@ConfigurationPropertiesScan(basePackageClasses = {AdventureConfigurationProperties.class})
public class TelegramConfig {

    public static final String CHAT_ID = "-699185295";

    @Bean
    public TelegramBot getTelegramBot(@Value("${telegram.bot.token}") final String botToken) {
        return new TelegramBot(botToken);
    }

}
