package com.pushkarev.firstbot.service.manager.feedback;

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

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackManager extends AbstractManager {

    final AnswerMethodFactory methodFactory;
    final KeyboardFactory keyboardFactory;

    @Autowired
    public FeedbackManager(AnswerMethodFactory methodFactory, KeyboardFactory keyboardFactory) {
        this.methodFactory = methodFactory;
        this.keyboardFactory = keyboardFactory;
    }

    @Override
    public BotApiMethod<?> answerCommand(Message message, Bot bot) {
        return methodFactory.getSendMessage(
                message.getChatId(),
                """
                        📍 Ссылки для обратной связи
                        GitHub - https://github.com/pushkarevsl
                        LinkedIn - https://www.linkedin.com/in/sergey-pushkaryov-806259179/
                        Telegram - https://t.me/pushkarev_s
                        """,
                null
        );
    }

    @Override
    public BotApiMethod<?> answerCallbackQuery(CallbackQuery callbackQuery, Bot bot) {
        return methodFactory.getEditMessageText(
                callbackQuery,
                """
                        📍 Ссылки для обратной связи
                        GitHub - https://github.com/pushkarevsl
                        LinkedIn - https://www.linkedin.com/in/sergey-pushkaryov-806259179/
                        Telegram - https://t.me/pushkarev_s
                        """,
                null
        );
    }

    @Override
    public BotApiMethod<?> answerMessage(Message message, Bot bot) {
        return null;
    }
}
