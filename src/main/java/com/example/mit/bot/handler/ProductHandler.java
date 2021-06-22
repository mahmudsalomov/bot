package com.example.mit.bot.handler;

import com.example.mit.bot.State;
import com.example.mit.bot.handler.method.Methods;
import com.example.mit.model.ProductCategory;
import com.example.mit.model.User;
import com.example.mit.model.dto.CategoryDto;
import com.example.mit.model.projection.NameBrand;
import com.example.mit.model.projection.uz_kril.NameOzProduct;
import com.example.mit.model.projection.uz_lat.NameUzCategory;
import com.example.mit.repository.BrandRepository;
import com.example.mit.repository.CategoryRepository;
import com.example.mit.repository.ProductRepository;
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

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.mit.util.TelegramUtil.createMessageTemplate;
import static com.example.mit.util.TelegramUtil.createPhotoTemplate;


@Component
public class ProductHandler implements Handler{
    @Autowired
    private KeyboardBuilder keyboardBuilder;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Methods methods;
    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message)  {
        if (message.equals(State.PRODUCT.name())){
                String msg=user.getLanguage().equals(Language.LANGUAGE_UZ_LATIN.name())?MessagesInterface.BTN_PRODUCT_UZ_LATIN:
                        user.getLanguage().equals(Language.LANGUAGE_RU.name())?MessagesInterface.BTN_PRODUCT_RU:
                                MessagesInterface.BTN_PRODUCT_UZ_KRILL;
                Col col=new Col();
                List<CategoryDto> childCategories = methods.categoryForLang(1,user);
                childCategories.forEach(chC->{
                    col.add(chC.getName(),"catId-"+chC.getId());
                });
//            col.add("\uD83D\uDD19 Orqaga","back_to");
            col.add("\uD83C\uDFD8 Bosh sahifa","EXIT");
                return Collections.singletonList(createMessageTemplate(user).setText(String.format(msg,
                        user.getName())).setReplyMarkup(col.getMarkup())
                );

        }else return null;

    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, CallbackQuery callback) throws IOException {
        String message=callback.getData();
        if (message.equals(State.PRODUCT.name())){
            String msg=user.getLanguage().equals(Language.LANGUAGE_UZ_LATIN.name())?MessagesInterface.BTN_PRODUCT_UZ_LATIN:
                    user.getLanguage().equals(Language.LANGUAGE_RU.name())?MessagesInterface.BTN_PRODUCT_RU:
                            MessagesInterface.BTN_PRODUCT_UZ_KRILL;
            Col col=new Col();
            Row row=new Row();
            List<CategoryDto> childCategories = methods.categoryForLang(1,user);
            childCategories.forEach(chC->{
                col.add(chC.getName(),"catId-"+chC.getId());
            });
            col.add("\uD83D\uDD19 Orqaga","back_to");
            col.add("\uD83C\uDFD8 Bosh sahifa","EXIT");
            return Collections.singletonList(createMessageTemplate(user).setText(String.format(msg,
                    user.getName())).setReplyMarkup(col.getMarkup())
            );

        }else if (callback.getData().startsWith("catId-"))
        {

            Integer id=Integer.valueOf(message.substring(message.lastIndexOf("-")+1));
            Col col=new Col();
            List<CategoryDto> childCategories = methods.categoryForLang(id,user);
            if (childCategories.size()>0){
                childCategories.forEach(chC->{
                    col.add(chC.getName(),"catId-"+chC.getId());
                });
            }else {
                List<NameBrand> nameUz = brandRepository.findNameUz(id);
                if (nameUz.size()>0){
                    nameUz.forEach(nu->{
                        col.add(nu.getName(),"brandId-"+nu.getId());
                    });
                }else {
                    System.out.println(id);
                    List<NameOzProduct> nameUzProducts = productRepository.allOzByCategoryId(Long.valueOf(id));
                    nameUzProducts.forEach(nameUzProduct -> {
                        col.add(nameUzProduct.getName_oz(),"prodId-"+nameUzProduct.getId());
                    });
                }

            }
            col.add("\uD83D\uDD19 Orqaga","back_to");
            col.add("\uD83C\uDFD8 Bosh sahifa","PRODUCT");
            return Collections.singletonList(createMessageTemplate(user).setText(String.format(message,
                    user.getName())).setReplyMarkup(col.getMarkup())
            );
        }else if (callback.getData().startsWith("brandId-"))
        {
            Col col =new Col();
            Long id=Long.valueOf(message.substring(message.lastIndexOf("-")+1));
            System.out.println(id);
            String action = user.getAction();
            Integer cat_id=Integer.valueOf(action.substring(action.lastIndexOf("c[")+1,action.indexOf("-b[")));
            List<NameOzProduct> nameUzProducts = productRepository.allOzByBrandId(id,cat_id);
            nameUzProducts.forEach(nameUzProduct -> {
                col.add(nameUzProduct.getName_oz(),"prodId-"+nameUzProduct.getId());
            });
            col.add("\uD83D\uDD19 Orqaga","back_to");
            col.add("\uD83C\uDFD8 Bosh sahifa","PRODUCT");
            return Collections.singletonList(createMessageTemplate(user).setText(String.format("message"+id,
                    user.getName())).setReplyMarkup(col.getMarkup()));
        }else
        {
            Col col=new Col();
            Row row=new Row();

            if (callback.getData().startsWith("prodId-")){
                Long id=Long.valueOf(message.substring(message.lastIndexOf("-")+1));
                System.out.println(id);
                NameOzProduct product = productRepository.getOzById(id);
                row.add("➖","minus");
                row.add("1",message);
                row.add("➕","plus");
                col.add(row);
                row.clear();
                row.add("✅ Buyurtma berish","add_order");
                row.add("\uD83D\uDED2 Savatga joylash","addBasket-"+id+"-1");
                col.add(row);
                col.add("\uD83D\uDD19 Orqaga","back_to");
                col.add("\uD83C\uDFD8 Bosh sahifa","PRODUCT");
                System.out.println(product.getMain_image());
                URL url=new URL("https://assets.asaxiy.uz/product/main_image/desktop/"+product.getMain_image());
                URLConnection connection=url.openConnection();
                try {
                    return Collections.singletonList(createPhotoTemplate(user.getChatId()).setPhoto(
                            "Photo",connection.getInputStream()
                    ).setCaption(product.getName_oz()).setReplyMarkup(col.getMarkup()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
//        if (message.startsWith("addBasket-")){
//            return List.of(createMessageTemplate(user).setText(String.format("null",
//                    user.getName())));
//        }else

        return Collections.singletonList(createMessageTemplate(user).setText(String.format("null",
                user.getName())));
    }

    @Override
    public State operatedBotState() {
        return State.IN_CATEGORY;
    }

    @Override
    public List<String> operatedCallBackQuery(User user) {
        List<NameUzCategory> childCategories = categoryRepository
                .findChildUzCategories(user.getCurrent_category_id());
        List<String> idList=new ArrayList<>();

//        System.out.println("User "+user);
        for (NameUzCategory childCategory : childCategories) {
            idList.add("catId-"+childCategory.getId());
        }
        if (childCategories.size()>0){
            Optional<ProductCategory> byTempId = categoryRepository.findByTempId(childCategories.get(0).getParent_id());
            if (byTempId.isPresent())
            idList.add(String.valueOf(byTempId.get().getId()));
        }
        idList.add(""+user.getCurrent_category_id());
//        System.out.println(idList+" idList");
        idList.add(""+State.PRODUCT.name());
        idList.add("catId");
        idList.add("prodId");
        idList.add("brandId");
        idList.add("minus");
        idList.add("plus");

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
