package com.whereisinput.birthday_bot.service.impl;

import static com.whereisinput.birthday_bot.config.TelegramConfig.CHAT_ID;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendAudio;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import com.whereisinput.birthday_bot.domain.Message;
import com.whereisinput.birthday_bot.domain.builder.TelegramSendMessageBuilder;
import com.whereisinput.birthday_bot.service.TelegramService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by yuriy_polyakevich at 4/16/22
 */
@Service
@Slf4j
public class TelegramServiceImpl implements TelegramService {

    @Autowired
    private TelegramBot bot;

    @Override
    public void sendMessage(Message message) {
        if (message.getImageRequest().getImage() == null) {
            return;
        }
        final SendPhoto baseRequest = new SendPhoto(CHAT_ID, message.getImageRequest().getImage());
        sendRequest(baseRequest, new SendImageCallback(message));
    }

    private <T extends BaseRequest<T, R>, R extends BaseResponse> void sendRequest(final T baseRequest) {
        sendRequest(baseRequest, null);
    }

    private <T extends BaseRequest<T, R>, R extends BaseResponse> void sendRequest(final T baseRequest,
                                                                                   Callback<T, R> callback) {
        bot.execute(baseRequest, callback == null ? new Callback<T, R>() {
            @Override
            public void onResponse(T request, R response) {
                log.info(response.description());
            }

            @Override
            public void onFailure(T request, IOException e) {
                log.error(e.getMessage());
            }
        } : callback);
    }

    private class SendImageCallback implements Callback<SendPhoto, SendResponse> {

        private final Message message;

        private SendImageCallback(Message message) {
            this.message = message;
        }

        @Override
        public void onResponse(SendPhoto request, SendResponse response) {
            if (message.getAudioRequest().getAudio() != null) {
                final SendAudio audio = new SendAudio(CHAT_ID, message.getAudioRequest().getAudio());
                audio.title("sound.mp3");
                sendRequest(audio, new SendAudioCallback(message));
            } else {
                final SendMessage baseRequest = TelegramSendMessageBuilder.of(message);
                sendRequest(baseRequest);
            }
        }

        @Override
        public void onFailure(SendPhoto request, IOException e) {
            log.error("Error image callback, :{}", e.getMessage());
        }

        private class SendAudioCallback implements Callback<SendAudio, SendResponse> {

            private final Message message;

            private SendAudioCallback(Message message) {
                this.message = message;
            }

            @Override
            public void onResponse(SendAudio request, SendResponse response) {
                final SendMessage baseRequest = TelegramSendMessageBuilder.of(message);
                sendRequest(baseRequest);
            }

            @Override
            public void onFailure(SendAudio request, IOException e) {
                log.error("Error send audio callback, message: {}", e.getMessage());
            }
        }

    }
}
