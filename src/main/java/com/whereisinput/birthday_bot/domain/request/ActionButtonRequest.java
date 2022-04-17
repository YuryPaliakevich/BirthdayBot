package com.whereisinput.birthday_bot.domain.request;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by yuriy_polyakevich at 4/17/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionButtonRequest {

    private String name;

}
