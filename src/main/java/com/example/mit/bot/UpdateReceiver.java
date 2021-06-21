package com.example.mit.bot;


import com.example.mit.bot.handler.Handler;
import com.example.mit.bot.handler.ProfileHandler;
import com.example.mit.model.User;
import com.example.mit.repository.ProductRepository;
import com.example.mit.repository.UserRepository;
import com.example.mit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Component
@Service
public class UpdateReceiver {
    private final List<Handler> handlers;
    private final UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;
    public UpdateReceiver(List<Handler> handlers, UserRepository userRepository) {
        this.handlers = handlers;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<PartialBotApiMethod<? extends Serializable>> handle(Update update) {

        try {
            if (isMessageWithText(update)) {
                final Message message = update.getMessage();
                System.out.println(message);
//                update.getMessage().getFrom();


                final int chatId = message.getFrom().getId();
                final User user = userRepository.getByChatId(chatId)
                        .orElseGet(() -> userRepository.save(new User(update.getMessage().getFrom())));
                return getHandlerByState(user.getBotState()).handle(user, message.getText());
            }
            else if (update.hasCallbackQuery()) {
                System.out.println(update.getCallbackQuery().getData());
                final CallbackQuery callbackQuery = update.getCallbackQuery();
                final int chatId = callbackQuery.getFrom().getId();
                String message=callbackQuery.getData();
                System.out.println(update.getCallbackQuery().getData());
                if (callbackQuery.getData().equals("EXIT")){
                    final User user = userRepository.getByChatId(chatId)
                            .orElseGet(() -> userRepository.save(new User(chatId)));
                    user.setBotState(State.START);
                    return getHandlerByState(user.getBotState()).handle(user, callbackQuery.getData());
                }
                else if (message.equals("LANGUAGE_RU")||
                        message.equals("LANGUAGE_UZ_LATIN")||
                        message.equals("LANGUAGE_UZ_KRIL")){
                    final User user = userRepository.getByChatId(chatId)
                            .orElseGet(() -> userRepository.save(new User(chatId)));
                    user.setLanguage(message);
//                    user.setCurrent_category_id(Integer.valueOf(update.getCallbackQuery().getData()));
                    userRepository.save(user);
                    return getHandlerByCallBackQuery(callbackQuery.getData(),user).handle(user, callbackQuery);

                }else{
                    final User user = userRepository.getByChatId(chatId)
                            .orElseGet(() -> userRepository.save(new User(chatId)));
                    user.setLanguage(message);
//                    user.setCurrent_category_id(Integer.valueOf(update.getCallbackQuery().getData()));
                    userRepository.save(user);
                    return getHandlerByCallBackQuery(callbackQuery.getData(),user).handle(user, callbackQuery);

                }




            } else if (update.getMessage().hasContact()){
                ProfileHandler profileHandler=new ProfileHandler(userService);
                final int chatId = Math.toIntExact(update.getMessage().getChatId());
                final User user = userRepository.getByChatId(chatId)
                        .orElseGet(() -> userRepository.save(new User(chatId)));
                return profileHandler.handle(user,update.getMessage().getContact().getPhoneNumber());
            }

            throw new UnsupportedOperationException();
        } catch (UnsupportedOperationException e) {
            return Collections.emptyList();
        }
    }

    private Handler getHandlerByState(State state) {
//        System.out.println("state "+state);
        return handlers.stream()
                .filter(h -> h.operatedBotState() != null)
                .filter(h -> h.operatedBotState().equals(state))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }

    private Handler getHandlerByCallBackQuery(String query,User user) {
//        System.out.println("state Callback "+query);
//        System.out.println(handlers);
//        handlers.forEach(item-> System.out.println(item.operatedCallBackQuery()));
        return handlers.stream()
                .filter(h -> h.operatedCallBackQuery(user).stream()
                        .anyMatch(query::equals))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }

    private boolean isMessageWithText(Update update) {
        return !update.hasCallbackQuery() && update.hasMessage() && update.getMessage().hasText()&& !update.getMessage().hasContact();
    }
}
