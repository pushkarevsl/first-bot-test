package com.pushkarev.firstbot.service.factory;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeyboardFactory {

    /**
     * InlineKeyboard - клавиатура которая появляется под сообщениями бота
     */
    public InlineKeyboardMarkup getInlineKeyboardMarkup(
            List<String> text,
            List<Integer> configuration,
            List<String> data
    ) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        int index = 0;

        for (Integer rowNumber : configuration) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int i = 0; i < rowNumber; i++) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(text.get(index));
                button.setCallbackData(data.get(index));

                row.add(button);
                index += 1;
            }
            keyboard.add(row);
        }
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    /**
     * ReplyKeyboard - нужна, чтобы подсказывать пользователю, какое сообщение могу отправить в данный момент.
     * Клавиатура в Telegram, которая рекомендует пользователю, какое текстовое сообщение отправить
     */
    public ReplyKeyboardMarkup getReplyKeyboardMarkup(
            List<String> text,
            List<Integer> configuration
    ) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        int index = 0;
        for (Integer rowNumber : configuration) {
            KeyboardRow row = new KeyboardRow();
            for (int i = 0; i < rowNumber; i++) {
                KeyboardButton button = new KeyboardButton();
                button.setText(text.get(index));
                row.add(button);
                index += 1;
            }
            keyboard.add(row);
        }
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }
}
