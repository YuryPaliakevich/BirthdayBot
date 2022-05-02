package com.whereisinput.birthday_bot.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.whereisinput.birthday_bot.config.AdventureConfigurationProperties;
import com.whereisinput.birthday_bot.domain.AdventureConfigAction;
import com.whereisinput.birthday_bot.domain.Message;
import com.whereisinput.birthday_bot.domain.builder.ActionButtonRequestBuilder;
import com.whereisinput.birthday_bot.domain.builder.MessageBuilder;
import com.whereisinput.birthday_bot.domain.request.ActionButtonRequest;
import com.whereisinput.birthday_bot.domain.request.AudioRequest;
import com.whereisinput.birthday_bot.domain.request.ImageRequest;
import com.whereisinput.birthday_bot.domain.request.TextRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by yury_paliakevich at 4/25/22
 */
@Slf4j
@Service
public class TelegramServiceFacade {

    @Autowired
    private TelegramService telegramService;
    @Autowired
    private AdventureConfigurationProperties advConfigProperties;
    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    private void init() {
        telegramBot.setUpdatesListener(updates -> {
            try {
                final Update update = updates.get(0);
                sendMessage(update.callbackQuery().data());
            } catch (Exception e) {
                log.error("Exception occurred", e);
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    public void sendMessage(final String callbackKey) {
        final AdventureConfigAction adventureConfigActionByKey = advConfigProperties.getAdventureConfigActionByKey(callbackKey);
        final Message message = createMessage(adventureConfigActionByKey);
        telegramService.sendMessage(message);
    }

    private Message createMessage(AdventureConfigAction adventureConfigAction) {
        final List<ActionButtonRequest> actionButtonRequests = adventureConfigAction.getButtons().stream()
                .map(button -> new ActionButtonRequestBuilder().withName(button.getText())
                        .withCallback(button.getCallback()).build()).toList();
        final TextRequest textRequest = new TextRequest(adventureConfigAction.getText());
        final ImageRequest imageRequest = new ImageRequest(getFileByUrl(adventureConfigAction.getImageUrl()));
        final AudioRequest audioRequest;
        if (!StringUtils.hasText(adventureConfigAction.getAudioUrl())) {
            audioRequest = null;
        } else {
            audioRequest = new AudioRequest(getFileByUrl(adventureConfigAction.getAudioUrl()));
        }
        return new MessageBuilder().withMessageID(adventureConfigAction.getKey())
                .withActionButtonRequests(actionButtonRequests).withImageRequest(imageRequest)
                .withTextRequest(textRequest)
                .withAudioRequest(audioRequest)
                .build();
    }

    private byte[] getFileByUrl(String fileUrl) {
        try {
            final InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(fileUrl);
            if (resourceAsStream != null) {
                return IOUtils.toByteArray(resourceAsStream);
            }
        } catch (IOException e) {
            log.error("File with path: {} was not found", fileUrl);
        }
        return null;
    }

}
