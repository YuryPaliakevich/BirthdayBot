package com.whereisinput.birthday_bot.domain.builder;

import java.util.ArrayList;
import java.util.List;

import com.whereisinput.birthday_bot.domain.Message;
import com.whereisinput.birthday_bot.domain.request.ActionButtonRequest;
import com.whereisinput.birthday_bot.domain.request.AudioRequest;
import com.whereisinput.birthday_bot.domain.request.ImageRequest;
import com.whereisinput.birthday_bot.domain.request.TextRequest;

/**
 * Created by yuriy_polyakevich at 4/17/22
 */
public class MessageBuilder {

    private String messageID;
    private final List<ActionButtonRequest> actionButtonRequests = new ArrayList<>();
    private ImageRequest imageRequest;
    private TextRequest textRequest;
    private AudioRequest audioRequest;

    public MessageBuilder withImageRequest(final ImageRequest imageRequest) {
        this.imageRequest = imageRequest;
        return this;
    }

    public MessageBuilder withMessageID(final String messageID) {
        this.messageID = messageID;
        return this;
    }

    public MessageBuilder withTextRequest(final TextRequest textRequest) {
        this.textRequest = textRequest;
        return this;
    }

    public MessageBuilder withActionButtonRequests(final List<ActionButtonRequest> actionButtonRequests) {
        this.actionButtonRequests.addAll(actionButtonRequests);
        return this;
    }

    public MessageBuilder withAudioRequest(final AudioRequest audioRequest) {
        this.audioRequest = audioRequest;
        return this;
    }

    public Message build() {
        return new Message(messageID, actionButtonRequests, imageRequest, textRequest, audioRequest);
    }

}
