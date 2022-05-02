package com.whereisinput.birthday_bot.domain.builder;

import static com.whereisinput.birthday_bot.config.TelegramConfig.CHAT_ID;

import java.util.List;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.whereisinput.birthday_bot.domain.Message;
import com.whereisinput.birthday_bot.domain.request.ActionButtonRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by yury_paliakevich at 5/2/22
 */
@Slf4j
public class TelegramSendMessageBuilder {

    public static SendMessage of(Message message) {
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
