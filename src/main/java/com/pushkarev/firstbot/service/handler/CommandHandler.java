package com.pushkarev.firstbot.service.handler;

import com.pushkarev.firstbot.service.factory.KeyboardFactory;
import com.pushkarev.firstbot.service.manager.FeedbackManager;
import com.pushkarev.firstbot.service.manager.HelpManager;
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
import static com.pushkarev.firstbot.service.data.CallbackData.*;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CommandHandler {

    final KeyboardFactory keyboardFactory;
    final FeedbackManager feedbackManager;
    final HelpManager helpManager;

    @Autowired
    public CommandHandler(KeyboardFactory keyboardFactory,
                          FeedbackManager feedbackManager,
                          HelpManager helpManager) {
        this.keyboardFactory = keyboardFactory;
        this.feedbackManager = feedbackManager;
        this.helpManager = helpManager;
    }

    public BotApiMethod<?> answer(Message message, Bot bot) {
        String command = message.getText();

        switch (command) {
            case START -> {
                return start(message);
            }
            case FEEDBACK_COMMAND -> {
                return feedbackManager.answerCommand(message);
            }
            case HELP_COMMAND -> {
                return helpManager.answerCommand(message);
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

    private BotApiMethod<?> start(Message message) {
        log.info("Starting bot...{}", message.getText());
        return SendMessage.builder()
                .chatId(message.getChatId())
                .replyMarkup(keyboardFactory.getInlineKeyboardMarkup(
                        List.of("Помощь", "Обратная связь"),
                        List.of(2),
                        List.of(HELP, FEEDBACK)
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
