package com.pushkarev.firstbot.service.handler;

import com.pushkarev.firstbot.service.manager.FeedbackManager;
import com.pushkarev.firstbot.service.manager.HelpManager;
import com.pushkarev.firstbot.service.manager.StartManager;
import com.pushkarev.firstbot.telegram.Bot;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.pushkarev.firstbot.service.data.Command.*;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CommandHandler {

    final FeedbackManager feedbackManager;
    final HelpManager helpManager;
    final StartManager startManager;

    @Autowired
    public CommandHandler(FeedbackManager feedbackManager,
                          HelpManager helpManager,
                          StartManager startManager) {
        this.feedbackManager = feedbackManager;
        this.helpManager = helpManager;
        this.startManager = startManager;
    }

    public BotApiMethod<?> answer(Message message, Bot bot) {
        String command = message.getText();

        switch (command) {
            case START -> {
                return startManager.answerCommand(message);
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
}
