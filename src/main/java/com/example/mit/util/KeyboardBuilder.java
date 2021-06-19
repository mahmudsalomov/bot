package com.example.mit.util;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeyboardBuilder {

    public InlineKeyboardButton keyboard(String name, String callback){
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(name);
        button.setCallbackData(callback);
        return button;
    }

    public InlineKeyboardMarkup keyboardMarkup(List<InlineKeyboardButton> buttonList){
        List<List<InlineKeyboardButton>> rowList= new ArrayList<>();

        for (InlineKeyboardButton inlineKeyboardButton : buttonList) {
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();

            keyboardButtonsRow.add(inlineKeyboardButton);
            rowList.add(keyboardButtonsRow);
        }
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(keyboard("Bosh menyu","EXIT"));
        rowList.add(keyboardButtonsRow);
        return new InlineKeyboardMarkup(rowList);
    }



    public InlineKeyboardMarkup keyboardMarkupWithoutExit(List<InlineKeyboardButton> buttonList){
        List<List<InlineKeyboardButton>> rowList= new ArrayList<>();
        for (InlineKeyboardButton inlineKeyboardButton : buttonList) {
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();

            keyboardButtonsRow.add(inlineKeyboardButton);
            rowList.add(keyboardButtonsRow);
        }
        return new InlineKeyboardMarkup(rowList);
    }
}
