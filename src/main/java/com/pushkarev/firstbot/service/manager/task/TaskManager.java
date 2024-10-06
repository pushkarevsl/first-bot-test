package com.pushkarev.firstbot.service.manager.task;

import com.pushkarev.firstbot.service.factory.AnswerMethodFactory;
import com.pushkarev.firstbot.service.factory.KeyboardFactory;
import com.pushkarev.firstbot.service.manager.AbstractManager;
import com.pushkarev.firstbot.telegram.Bot;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

import static com.pushkarev.firstbot.service.data.CallbackData.*;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskManager extends AbstractManager {

    final AnswerMethodFactory methodFactory;
    final KeyboardFactory keyboardFactory;

    @Autowired
    public TaskManager(AnswerMethodFactory answerMethodFactory, KeyboardFactory keyboardFactory) {
        this.methodFactory = answerMethodFactory;
        this.keyboardFactory = keyboardFactory;
    }

    @Override
    public BotApiMethod<?> answerCommand(Message message, Bot bot) {
        return mainMenu(message);
    }

    @Override
    public BotApiMethod<?> answerMessage(Message message, Bot bot) {
        return null;
    }

    @Override
    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, Bot bot) {
        String callBackData = callbackQuery.getData();
        switch (callBackData){
            case TASK ->{
                return mainMenu(callbackQuery);
            }
            case TASK_CREATE ->{
                return create(callbackQuery);
            }
        }
        return null;
    }

    private BotApiMethod<?> mainMenu(Message message) {
        return methodFactory.getSendMessage(
                message.getChatId(),
                """
                        🗂 Вы можете добавить домашнее задание вашему ученику""",
                keyboardFactory.getInlineKeyboardMarkup(
                        List.of("Прикрепить домашнее задание"),
                        List.of(1),
                        List.of(TASK_CREATE)
                )
        );
    }

    private BotApiMethod<?> mainMenu(CallbackQuery callbackQuery) {
        return methodFactory.getEditMessageText(
                callbackQuery,
                """
                         🗂 Вы можете добавить домашнее задание вашему ученику""",
                keyboardFactory.getInlineKeyboardMarkup(
                        List.of("Прикрепить домашнее задание"),
                        List.of(1),
                        List.of(TASK_CREATE)
                )
        );
    }

    private BotApiMethod<?> create(CallbackQuery callbackQuery) {
        return methodFactory.getEditMessageText(
                callbackQuery,
                """
                        👤 Выберете ученика, которому хотите дать домашнее задание""",
                keyboardFactory.getInlineKeyboardMarkup(
                        List.of("Назад"),
                        List.of(1),
                        List.of(TASK)
                )
        );
    }
}
