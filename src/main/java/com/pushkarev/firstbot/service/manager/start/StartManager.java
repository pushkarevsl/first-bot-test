package com.pushkarev.firstbot.service.manager.start;

import com.pushkarev.firstbot.service.factory.AnswerMethodFactory;
import com.pushkarev.firstbot.service.factory.KeyboardFactory;
import com.pushkarev.firstbot.service.manager.AbstractManager;
import com.pushkarev.firstbot.telegram.Bot;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

import static com.pushkarev.firstbot.service.data.CallbackData.FEEDBACK;
import static com.pushkarev.firstbot.service.data.CallbackData.HELP;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StartManager extends AbstractManager {

    final AnswerMethodFactory methodFactory;
    final KeyboardFactory keyboardFactory;

    @Autowired
    public StartManager(AnswerMethodFactory methodFactory, KeyboardFactory keyboardFactory) {
        this.methodFactory = methodFactory;
        this.keyboardFactory = keyboardFactory;
    }

    @Override
    public SendMessage answerCommand(Message message, Bot bot) {
        return methodFactory.getSendMessage(
                message.getChatId(),
                """
                        🖖Приветствую в Tutor-Bot, инструменте для упрощения взаимодействия репититора и ученика.
                        
                        Что бот умеет?
                        📌 Составлять расписание
                        📌 Прикреплять домашние задания
                        📌 Ввести контроль успеваемости
                        """,
                keyboardFactory.getInlineKeyboardMarkup(
                        List.of("Помощь", "Обратная связь"),
                        List.of(2),
                        List.of(HELP, FEEDBACK)
                )
        );
    }

    @Override
    public BotApiMethod<?> answerMessage(Message message, Bot bot) {
        return null;
    }

    @Override
    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, Bot bot) {
        return null;
    }
}
