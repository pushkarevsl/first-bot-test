package com.pushkarev.firstbot.service.handler;

import com.pushkarev.firstbot.service.manager.feedback.FeedbackManager;
import com.pushkarev.firstbot.service.manager.help.HelpManager;
import com.pushkarev.firstbot.service.manager.start.StartManager;
import com.pushkarev.firstbot.service.manager.task.TaskManager;
import com.pushkarev.firstbot.service.manager.timeTable.TimetableManager;
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
    final TimetableManager timetableManager;
    final TaskManager taskManager;

    @Autowired
    public CommandHandler(FeedbackManager feedbackManager,
                          HelpManager helpManager,
                          StartManager startManager,
                          TimetableManager timetableManager,
                          TaskManager taskManager) {
        this.feedbackManager = feedbackManager;
        this.helpManager = helpManager;
        this.startManager = startManager;
        this.timetableManager = timetableManager;
        this.taskManager = taskManager;
    }

    public BotApiMethod<?> answer(Message message, Bot bot) {
        String command = message.getText();

        switch (command) {
            case START -> {
                return startManager.answerCommand(message, bot);
            }
            case FEEDBACK_COMMAND -> {
                return feedbackManager.answerCommand(message, bot);
            }
            case HELP_COMMAND -> {
                return helpManager.answerCommand(message, bot);
            }
            case TIMETABLE -> {
                return timetableManager.answerCommand(message, bot);
            }
            case TASK -> {
                return taskManager.answerCommand(message, bot);
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
