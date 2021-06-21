package com.example.mit.bot;


import com.example.mit.bot.handler.Handler;
import com.example.mit.bot.handler.ProfileHandler;
import com.example.mit.bot.handler.StartHandler;
import com.example.mit.model.User;
import com.example.mit.repository.ProductRepository;
import com.example.mit.repository.UserRepository;
import com.example.mit.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
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
                    userRepository.save(user);
                    return getHandlerByState(user.getBotState()).handle(user, callbackQuery.getData());
                }
//                else if (message.equals("PRODUCT")||
//                message.startsWith("cat_id_")||message.startsWith("brand_id_")||message.startsWith("prod_id_")){
//                    final User user = userRepository.getByChatId(chatId)
//                            .orElseGet(() -> userRepository.save(new User(chatId)));
//                    userRepository.save(user);
//                    if (message.equals("PRODUCT")){
//                        return getHandlerByCallBackQuery(message,user).handle(user, callbackQuery);
//                    }else return getHandlerByCallBackQuery(message.substring(0,message.lastIndexOf("_")+1),user).handle(user,callbackQuery);
//                }

//                else if (message.equals("LANGUAGE_RU")||
//                        message.equals("LANGUAGE_UZ_LATIN")||
//                        message.equals("LANGUAGE_UZ_KRIL")){
//                    final User user = userRepository.getByChatId(chatId)
//                            .orElseGet(() -> userRepository.save(new User(chatId)));
//                    user.setLanguage(message);
////                    user.setCurrent_category_id(Integer.valueOf(update.getCallbackQuery().getData()));
//                    userRepository.save(user);
//                    return getHandlerByCallBackQuery(callbackQuery.getData(),user).handle(user, callbackQuery);
//
//                }else{
                    final User user = userRepository.getByChatId(chatId)
                            .orElseGet(() -> userRepository.save(new User(chatId)));
                    return getHandlerByCallBackQuery(callbackQuery.getData(),user).handle(user, callbackQuery);
//
//                }




            } else if (update.getMessage().hasContact()){
                StartHandler startHandler=new StartHandler(userService);
                final int chatId = Math.toIntExact(update.getMessage().getChatId());
                final User user = userRepository.getByChatId(chatId)
                        .orElseGet(() -> userRepository.save(new User(chatId)));

                return startHandler.addContact(user,update.getMessage().getContact().getPhoneNumber());
            }

            throw new UnsupportedOperationException();
        } catch (UnsupportedOperationException | IOException e) {
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
        String[] parts=query.split("-");
        String finalQuery = parts[0];
        return handlers.stream()
                .filter(h -> h.operatedCallBackQuery(user).stream()
                        .anyMatch(finalQuery::equals))
                .findAny()
                .orElseThrow(UnsupportedOperationException::new);
    }

    private boolean isMessageWithText(Update update) {
        return !update.hasCallbackQuery() && update.hasMessage() && update.getMessage().hasText()&& !update.getMessage().hasContact();
    }

    private String addAction(User user,String data){
        return  user.getAction()+
                (data.equals("PRODUCT")?"-[PRODUCT]":
                        data.startsWith("cat_id_")?"-c["+data.substring(data.lastIndexOf("_")+1)+"]":
                                data.startsWith("brand_id_")?"-b["+data.substring(data.lastIndexOf("_")+1)+"]":"");
    }

    private String removeAction(String data){
        return null;
    }
}
