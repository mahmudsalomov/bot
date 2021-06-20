package com.example.mit.util;

import com.example.mit.model.User;
import com.example.mit.util.ButtonModel.InlineKeyboardModel;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class TelegramUtil {
    public static SendMessage createMessageTemplate(User user) {
        return createMessageTemplate(String.valueOf(user.getChatId()));
    }
    public static SendMessage createMessageTemplate(String chatId) {
        return new SendMessage()
                .setChatId(chatId)
                .enableMarkdown(true);
    }
    public static InlineKeyboardButton createInlineKeyboardButton(String text, String command) {
        return new InlineKeyboardButton()
                .setText(text)
                .setCallbackData(command);
    }

    public static InlineKeyboardButton keyboard(String text){
        return InlineKeyboardModel
                .builder()
                .text(text)
                .callbackData("none")
                .build();
    }

    public static InlineKeyboardButton keyboard(String text, String callback){
        return InlineKeyboardModel
                .builder()
                .text(text)
                .callbackData(callback)
                .build();
    }

    public static InlineKeyboardButton keyboard(String text, String callback, String url){
        return InlineKeyboardModel
                .builder()
                .text(text)
                .callbackData(callback)
                .url(url)
                .build();
    }


    public static String parseName(User user){
        String str=" ";

        if (user.getFirstname()==null&&user.getLastname()==null){
            if (user.getUsername()!=null) str= user.getUsername();
        } else {
            if (user.getFirstname()!=null)
                str+=user.getFirstname();
            if (user.getLastname()!=null)
                str+= user.getLastname();
        }
        return "*"+str+"*";
    }
}
