package com.pushkarev.firstbot.service.handler;

import com.pushkarev.firstbot.service.manager.feedback.FeedbackManager;
import com.pushkarev.firstbot.service.manager.help.HelpManager;
import com.pushkarev.firstbot.service.manager.task.TaskManager;
import com.pushkarev.firstbot.service.manager.timeTable.TimetableManager;
import com.pushkarev.firstbot.telegram.Bot;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import static com.pushkarev.firstbot.service.data.CallbackData.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CallBackQueryHandler {

    final HelpManager helpManager;
    final FeedbackManager feedbackManager;
    final TimetableManager timetableManager;
    final TaskManager taskManager;

    @Autowired
    public CallBackQueryHandler(
            HelpManager helpManager,
            FeedbackManager feedbackManager,
            TimetableManager timetableManager,
            TaskManager taskManager) {
        this.helpManager = helpManager;
        this.feedbackManager = feedbackManager;
        this.timetableManager = timetableManager;
        this.taskManager = taskManager;
    }

    public BotApiMethod<?> answer(CallbackQuery callbackQuery, Bot bot) {
        String callbackData = callbackQuery.getData();
        String keyWord = callbackData.split("_")[0];

        if (TIMETABLE.equals(keyWord)) {
            return timetableManager.answerCallbackQuery(callbackQuery, bot);
        }

        if(TASK.equals(keyWord)) {
            return taskManager.answerCallbackQuery(callbackQuery, bot);
        }

        switch (callbackData) {
            case FEEDBACK -> {
                return feedbackManager.answerCallbackQuery(callbackQuery, bot);
            }
            case HELP -> {
                return helpManager.answerCallbackQuery(callbackQuery, bot);
            }
        }
        return null;
    }
}