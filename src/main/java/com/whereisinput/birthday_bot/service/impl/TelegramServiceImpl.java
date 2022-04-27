package com.whereisinput.birthday_bot.service.impl;

import static com.whereisinput.birthday_bot.config.TelegramConfig.CHAT_ID;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import com.whereisinput.birthday_bot.domain.Message;
import com.whereisinput.birthday_bot.domain.request.ActionButtonRequest;
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
        if (message.getImageRequest() != null) {
            final SendPhoto baseRequest = new SendPhoto(CHAT_ID, message.getImageRequest().getImage());
            sendRequest(baseRequest, new SendImageCallback(message));
        } else {
            if (message.getTextRequest() != null) {
                final SendMessage baseRequest = getSendMessage(message);
                sendRequest(baseRequest);
            }
        }
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
            final SendMessage baseRequest = getSendMessage(message);

            sendRequest(baseRequest);
        }

        @Override
        public void onFailure(SendPhoto request, IOException e) {
        }
    }

    private SendMessage getSendMessage(final Message message) {
        final SendMessage sendMessage = new SendMessage(CHAT_ID,
                message.getTextRequest() == null ? "" : message.getTextRequest().getText());
        final List<ActionButtonRequest> actionButtonRequests = message.getActionButtonRequests();
        if (!CollectionUtils.isEmpty(actionButtonRequests) && actionButtonRequests.stream()
                .anyMatch(actionButtonRequest -> StringUtils.hasText(actionButtonRequest.getCallback()))) {
            final InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
            for (final ActionButtonRequest actionButtonRequest : actionButtonRequests) {
                if (!StringUtils.hasText(actionButtonRequest.getCallback())) {
                    log.error("callback must contain text");
                    continue;
                }
                final InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(
                        actionButtonRequest.getName()).callbackData(actionButtonRequest.getCallback());
                inlineKeyboard.addRow(inlineKeyboardButton);
            }
            sendMessage.replyMarkup(inlineKeyboard.inlineKeyboard().length == 0 ? null : inlineKeyboard);
        }
        return sendMessage;
    }

}
