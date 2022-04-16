package com.whereisinput.birthday_bot.service.impl;

import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.whereisinput.birthday_bot.service.TelegramService;

import lombok.RequiredArgsConstructor;

/**
 * Created by yuriy_polyakevich at 4/16/22
 */
@Service
@RequiredArgsConstructor
public class TelegramServiceImpl implements TelegramService {

    private final TelegramBot bot;
    private static final String CHAT_ID = "-699185295";

    @Override
    public void sendMessage(String message) {
        final BaseRequest<SendMessage, SendResponse> request = new SendMessage(CHAT_ID, message);
        bot.execute(request);
    }
}
