package com.whereisinput.birthday_bot.service.impl;

import static com.whereisinput.birthday_bot.config.TelegramConfig.CHAT_ID;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * Created by yuriy_polyakevich at 4/16/22
 */
@Service
public class TelegramServiceImpl implements TelegramService {

    @Autowired
    private TelegramBot bot;

    @Override
    public void sendMessage(Message message) {
        if (message.getImageRequest() != null) {
            final SendPhoto baseRequest = new SendPhoto(CHAT_ID, message.getImageRequest().getImage());
            sendRequest(baseRequest, new SendImageCallback(message.getTextRequest() == null ? "" : message.getTextRequest().getText()));
        } else {
            if (message.getTextRequest() != null) {
                final SendMessage baseRequest = new SendMessage(CHAT_ID, message.getTextRequest().getText());
                sendRequest(baseRequest);
            }
        }
    }

    private <T extends BaseRequest<T, R>, R extends BaseResponse> void sendRequest(final T baseRequest) {
        sendRequest(baseRequest, null);
    }

    private <T extends BaseRequest<T, R>, R extends BaseResponse> void sendRequest(final T baseRequest,
                                                                                   Callback<T, R> callback) {
        bot.execute(baseRequest, callback);
    }

    private class SendImageCallback implements Callback<SendPhoto, SendResponse> {

        private final String message;

        private SendImageCallback(String message) {
            this.message = message;
        }

        @Override
        public void onResponse(SendPhoto request, SendResponse response) {
            final SendMessage baseRequest = new SendMessage(CHAT_ID, message);
            sendRequest(baseRequest);
        }

        @Override
        public void onFailure(SendPhoto request, IOException e) {
        }
    }

}
