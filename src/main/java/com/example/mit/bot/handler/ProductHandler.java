package com.example.mit.bot.handler;

import com.example.mit.bot.State;
import com.example.mit.model.ProductCategory;
import com.example.mit.model.User;
import com.example.mit.model.projection.uz_lat.NameUzCategory;
import com.example.mit.repository.CategoryRepository;
import com.example.mit.repository.ProductRepository;
import com.example.mit.repository.UserRepository;
import com.example.mit.util.KeyboardBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.mit.util.TelegramUtil.createMessageTemplate;


@Component
public class ProductHandler implements Handler{
    @Autowired
    private KeyboardBuilder keyboardBuilder;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message){
        return null;
    }

    @Override
    public State operatedBotState() {
        return State.IN_CATEGORY;
    }

    @Override
    public List<String> operatedCallBackQuery(User user) {
        List<NameUzCategory> childCategories = categoryRepository
                .findChildCategories(user.getCurrent_category_id());
        List<String> idList=new ArrayList<>();

//        System.out.println("User "+user);
        for (NameUzCategory childCategory : childCategories) {
            idList.add(String.valueOf(childCategory.getId()));
        }
        if (childCategories.size()>0){
            Optional<ProductCategory> byTempId = categoryRepository.findByTempId(childCategories.get(0).getParent_id());
            if (byTempId.isPresent())
            idList.add(String.valueOf(byTempId.get().getId()));
        }
        idList.add(""+user.getCurrent_category_id());
//        System.out.println(idList+" idList");
        idList.add(State.PRODUCT.name());

        return idList;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
