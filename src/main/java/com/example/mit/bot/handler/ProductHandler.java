package com.example.mit.bot.handler;

import com.example.mit.bot.State;
import com.example.mit.model.ProductCategory;
import com.example.mit.model.User;
import com.example.mit.model.projection.NameUzCategory;
import com.example.mit.model.projection.NameUzProduct;
import com.example.mit.repository.CategoryRepository;
import com.example.mit.repository.ProductRepository;
import com.example.mit.repository.UserRepository;
import com.example.mit.util.KeyboardBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

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
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
//        System.out.println(message+" IN_CATEGORY");

        List<NameUzCategory> productCategories =categoryRepository.findChildCategories(user.getCurrent_category_id());
//        System.out.println(productCategories);

//        System.out.println("HAMMASI");
//        productRepository.allByCategoryId(102).forEach(item-> System.out.println(item.getName_uz()));


        if (productCategories.size()==0){



            List<NameUzProduct> products =productRepository.allByCategoryId(user.getCurrent_category_id());
//            System.out.println(products);

            List<InlineKeyboardButton> buttons=new ArrayList<>();
            for (NameUzProduct product : products) {
                buttons.add(keyboardBuilder.keyboard(product.getName_uz(), "p"+product.getId()));
            }
            List<NameUzCategory> parentId = categoryRepository.findParentId(user.getCurrent_category_id());
            if (parentId.size()>0){
//                System.out.println("PPPPPPAAAAAAAAARRRRRRRRREEEEEEEENNNNNNTTTTTT"+parentId.get(0).getId());
//                List<InlineKeyboardButton> back= Collections.singletonList(keyboardBuilder.keyboard("Back", "" + parentId.get(0).getParent_id()));
                buttons.add(keyboardBuilder.keyboard("Back",""+parentId.get(0).getId()));
            }
            InlineKeyboardMarkup markup = keyboardBuilder.keyboardMarkup(buttons);




            return List.of(createMessageTemplate(user).setText(String.format("" +
                    "Products!", user.getName()))
                    .setReplyMarkup(markup));






//            System.out.println("QOLMADI");
//            ThirdHandler thirdHandler=new ThirdHandler();
//            user.setBotState(State.IN_PRODUCT);
//            userRepository.save(user);
//            return thirdHandler.handle(user,message);
        }


            List<InlineKeyboardButton> buttons=new ArrayList<>();
            for (NameUzCategory productCategory : productCategories) {
                buttons.add(keyboardBuilder.keyboard(productCategory.getName_uz(), String.valueOf(productCategory.getId())));
            }
        List<NameUzCategory> parentId = categoryRepository.findParentId(user.getCurrent_category_id());
        if (parentId.size()>0){
//            System.out.println("PPPPPPAAAAAAAAARRRRRRRRREEEEEEEENNNNNNTTTTTT"+parentId.get(0).getId());
//                List<InlineKeyboardButton> back= Collections.singletonList(keyboardBuilder.keyboard("Back", "" + parentId.get(0).getParent_id()));
            buttons.add(keyboardBuilder.keyboard("Back",""+parentId.get(0).getId()));
        }
        InlineKeyboardMarkup markup = keyboardBuilder.keyboardMarkup(buttons);
        return List.of(createMessageTemplate(user).setText(String.format("" +
                    "Categories!", user.getName()))
                    .setReplyMarkup(markup));






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
