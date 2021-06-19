package com.example.mit.bot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    @Getter
    private String botUsername;

    @Value("${bot.token}")
    @Getter
    private String botToken;

    private final UpdateReceiver updateReceiver;

    @Override
    public void onUpdateReceived(Update update) {
        List<PartialBotApiMethod<? extends Serializable>> messagesToSend = updateReceiver.handle(update);
        if (messagesToSend != null && !messagesToSend.isEmpty()) {
            messagesToSend.forEach(response -> {
                if (response instanceof SendMessage) {
                    executeWithExceptionCheck((SendMessage) response);
                }
            });
        }
       deleteMessage(update);
    }

    public void executeWithExceptionCheck(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("oops");
        }
    }
//    public void executeWithExceptionCheck( sendMessage) {
//        try {
//            execute(sendMessage);
//        } catch (TelegramApiException e) {
//            log.error("oops");
//        }
//    }

    public void deleteMessage(Update update){
        DeleteMessage deleteMessage=new DeleteMessage();
        if (update.hasMessage()) {
            deleteMessage.setChatId(update.getMessage().getChatId()).setMessageId(update.getMessage().getMessageId());
        } else {
            deleteMessage.setMessageId(update.getCallbackQuery().getMessage().getMessageId()).setChatId(update.getCallbackQuery().getMessage().getChatId());
        }
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
