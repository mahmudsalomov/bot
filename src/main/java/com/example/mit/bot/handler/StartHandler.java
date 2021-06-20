package com.example.mit.bot.handler;


import com.example.mit.bot.State;
import com.example.mit.model.User;
import com.example.mit.model.profile_handler_model.ProfileEnums;
import com.example.mit.repository.CategoryRepository;
import com.example.mit.repository.UserRepository;
import com.example.mit.util.ButtonModel.Col;
import com.example.mit.util.ButtonModel.Row;
import com.example.mit.util.KeyboardBuilder;
import com.example.mit.util.Language;
import com.example.mit.util.MessagesInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.mit.util.TelegramUtil.createMessageTemplate;


@Component
public class StartHandler implements Handler{
    @Autowired
    private KeyboardBuilder keyboardBuilder;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        System.out.println(message);

           if (user.getLanguage()==null){
               Row row=new Row();
               row.add("\uD83C\uDDFA\uD83C\uDDFF Uz", Language.LANGUAGE_UZ_LATIN.name());
               row.add("\uD83C\uDDFA\uD83C\uDDFF Ўз", Language.LANGUAGE_UZ_KRIL.name());
               row.add("\uD83C\uDDF7\uD83C\uDDFA Ru", Language.LANGUAGE_RU.name());

               user.setBotState(State.START);
               userRepository.save(user);
               return List.of(createMessageTemplate(user).setText(String.format("" +
                       "Tilni tanlang!!!\nТилни танланг!!!\nВыберите язык", user.getName()))
                       .setReplyMarkup(row.getMarkup()));
           }else{
               String msg=user.getLanguage().equals(Language.LANGUAGE_RU.name())? MessagesInterface.MENU_MSG_RU :
                       user.getLanguage().equals(Language.LANGUAGE_UZ_LATIN.name())?MessagesInterface.MENU_MSG_UZ_LATIN
                               :MessagesInterface.MENU_MSG_UZ_KRILL;
               String BTN_PROMOTIONS=user.getLanguage().equals(Language.LANGUAGE_RU.name())? MessagesInterface.BTN_PROMOTIONS_RU :
                       user.getLanguage().equals(Language.LANGUAGE_UZ_LATIN.name())?MessagesInterface.BTN_PROMOTIONS_UZ_LATIN
                               :MessagesInterface.BTN_PROMOTIONS_UZ_KRILL;
               String BTN_BASKET=user.getLanguage().equals(Language.LANGUAGE_RU.name())? MessagesInterface.BTN_BASKET_RU :
                       user.getLanguage().equals(Language.LANGUAGE_UZ_LATIN.name())?MessagesInterface.BTN_BASKET_UZ_LATIN
                               :MessagesInterface.BTN_BASKET_UZ_KRILL;
               String BTN_PRODUCT=user.getLanguage().equals(Language.LANGUAGE_RU.name())? MessagesInterface.BTN_PRODUCT_RU :
                       user.getLanguage().equals(Language.LANGUAGE_UZ_LATIN.name())?MessagesInterface.BTN_PRODUCT_UZ_LATIN
                               :MessagesInterface.BTN_PRODUCT_UZ_KRILL;
               String BTN_PROFILE=user.getLanguage().equals(Language.LANGUAGE_RU.name())? MessagesInterface.BTN_PROFILE_RU :
                       user.getLanguage().equals(Language.LANGUAGE_UZ_LATIN.name())?MessagesInterface.BTN_PROFILE_LATIN
                               :MessagesInterface.BTN_PROFILE_KRILL;

               Col col =new Col();
               col.add(BTN_PRODUCT,State.PRODUCT.name());
               col.add(BTN_PROMOTIONS,State.PROMOTIONS.name());
               col.add(BTN_BASKET,State.BASKET.name());
               col.add(BTN_PROFILE,State.PROFILE.name());


               return List.of(createMessageTemplate(user).setText(String.format("" +
                       msg, user.getName())).setReplyMarkup(col.getMarkup())
                       );
           }
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, CallbackQuery callback) {
        return null;
    }

    @Override
    public State operatedBotState() {
        return State.START;
    }

    @Override
    public List<String> operatedCallBackQuery(User user) {
        List<String> callBackList=new ArrayList<>();
        callBackList.add(Language.LANGUAGE_RU.name());
        callBackList.add(Language.LANGUAGE_UZ_KRIL.name());
        callBackList.add(Language.LANGUAGE_UZ_LATIN.name());
        callBackList.add(ProfileEnums.MY_LANGUAGE.name());
        return callBackList;
    }
}
