package com.whereisinput.birthday_bot.service;

import com.whereisinput.birthday_bot.domain.Message;

public interface TelegramService {

    void sendMessage(Message message);

}
