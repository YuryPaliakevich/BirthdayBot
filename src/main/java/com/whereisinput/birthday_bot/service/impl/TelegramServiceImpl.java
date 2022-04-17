package com.whereisinput.birthday_bot.service.impl;

import static com.whereisinput.birthday_bot.config.TelegramConfig.CHAT_ID;

import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import com.whereisinput.birthday_bot.domain.Message;
import com.whereisinput.birthday_bot.service.TelegramService;

import lombok.RequiredArgsConstructor;

/**
 * Created by yuriy_polyakevich at 4/16/22
 */
@Service
@RequiredArgsConstructor
public class TelegramServiceImpl implements TelegramService {

    private final TelegramBot bot;

    @Override
    public void sendMessage(Message message) {
        if (message.getImageRequest() != null) {
            sendRequest(new SendPhoto(CHAT_ID, message.getImageRequest().getImage()));
        }
        if (message.getTextRequest() != null) {
            sendRequest(new SendMessage(CHAT_ID, message.getTextRequest().getText()));
        }
    }

    private <T extends BaseRequest<T, R>, R extends BaseResponse> void sendRequest(final T baseRequest) {
        sendRequest(baseRequest, null);
    }

    private <T extends BaseRequest<T, R>, R extends BaseResponse> void sendRequest(final T baseRequest, Callback<T, R> callback) {
        bot.execute(baseRequest, callback);
    }

}
