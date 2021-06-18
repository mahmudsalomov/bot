package com.example.mit.bot.handler;


import com.example.mit.bot.State;
import com.example.mit.model.User;
import com.example.mit.model.projection.NameUzCategory;
import com.example.mit.repository.CategoryRepository;
import com.example.mit.repository.UserRepository;
import com.example.mit.util.KeyboardBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.mit.util.TelegramUtil.createMessageTemplate;


@Component
public class MyHandler implements Handler{
    @Autowired
    private KeyboardBuilder keyboardBuilder;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
//        System.out.println(message);
        List<NameUzCategory> productCategories = categoryRepository.findAllNameUz();
            List<InlineKeyboardButton> buttons=new ArrayList<>();
            for (NameUzCategory productCategory : productCategories) {
                buttons.add(keyboardBuilder.keyboard(productCategory.getName_uz(), String.valueOf(productCategory.getId())));
            }
            user.setBotState(State.IN_CATEGORY);
            userRepository.save(user);
            return List.of(createMessageTemplate(user).setText(String.format("" +
                    "Shop!", user.getName()))
                    .setReplyMarkup(keyboardBuilder.keyboardMarkupWithoutExit(buttons)));



    }

    @Override
    public State operatedBotState() {
        return State.START;
    }

    @Override
    public List<String> operatedCallBackQuery(User user) {
        return Collections.emptyList();
    }
}
