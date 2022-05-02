package com.whereisinput.birthday_bot.domain;

import java.util.List;

import com.whereisinput.birthday_bot.domain.request.ActionButtonRequest;
import com.whereisinput.birthday_bot.domain.request.AudioRequest;
import com.whereisinput.birthday_bot.domain.request.ImageRequest;
import com.whereisinput.birthday_bot.domain.request.TextRequest;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by yuriy_polyakevich at 4/17/22
 */
@Data
@AllArgsConstructor
public class Message {

    private String messageID;
    private List<ActionButtonRequest> actionButtonRequests;
    private ImageRequest imageRequest;
    private TextRequest textRequest;
    private AudioRequest audioRequest;

}
