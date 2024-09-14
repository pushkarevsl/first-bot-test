package com.pushkarev.firstbot.service.handler;

import com.pushkarev.firstbot.telegram.Bot;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service
public class CallBackQueryHandler {

    public BotApiMethod<?> answer(CallbackQuery callbackQuery, Bot bot) {
        return null;
    }
}
