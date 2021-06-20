package com.example.mit.bot.handler;


import com.example.mit.bot.State;
import com.example.mit.model.User;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.io.Serializable;
import java.util.List;

public interface Handler {
    List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message);
    List<PartialBotApiMethod<? extends Serializable>> handle(User user, CallbackQuery callback);

    State operatedBotState();

    List<String> operatedCallBackQuery(User user);
}
