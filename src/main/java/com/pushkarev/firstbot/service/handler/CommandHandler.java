package com.pushkarev.firstbot.service.handler;

import com.pushkarev.firstbot.service.factory.KeyboardFactory;
import com.pushkarev.firstbot.telegram.Bot;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

import static com.pushkarev.firstbot.service.data.Command.*;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CommandHandler {

    final KeyboardFactory keyboardFactory;

    @Autowired
    public CommandHandler(KeyboardFactory keyboardFactory) {
        this.keyboardFactory = keyboardFactory;
    }

    public BotApiMethod<?> answer(Message message, Bot bot) {
        String command = message.getText();

        switch (command) {
            case START -> {
                return start(message);
            }
            case FEEDBACK -> {
                return feedback(message);
            }
            case HELP -> {
                return help(message);
            }
            default -> {
                return defaultAnswer(message);
            }
        }
    }

    private BotApiMethod<?> defaultAnswer(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text("Неподдерживаемая команда")
                .build();
    }

    private BotApiMethod<?> help(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text("""
                        📍 Доступные команды:
                        - start
                        - help
                        - feedback
                        
                        📍 Доступные функции:
                        - Расписание
                        - Домашнее задание
                        - Контроль успеваемости
                       """)
                .build();
    }

    private BotApiMethod<?> feedback(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text("""
                        📍 Ссылки для обратной связи
                        GitHub - https://github.com/pushkarevsl
                        LinkedIn - https://www.linkedin.com/in/sergey-pushkaryov-806259179/
                        Telegram - https://t.me/pushkarev_s
                        """)
                .disableWebPagePreview(true)
                .build();
    }

    private BotApiMethod<?> start(Message message) {
        log.info("Starting bot...{}", message.getText());
        return SendMessage.builder()
                .chatId(message.getChatId())
                .replyMarkup(keyboardFactory.getInlineKeyboardMarkup(
                        List.of("Помощь", "Обратная связь"),
                        List.of(2),
                        List.of("asd", "dfg")
                ))
                .text("""
                        🖖Приветствую в Tutor-Bot, инструменте для упрощения взаимодействия репититора и ученика.
                                                
                        Что бот умеет?
                        📌 Составлять расписание
                        📌 Прикреплять домашние задания
                        📌 Ввести контроль успеваемости                        
                        """)
                .build();
    }
}
