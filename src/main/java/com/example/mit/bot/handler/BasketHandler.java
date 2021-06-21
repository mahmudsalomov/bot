package com.example.mit.bot.handler;

import com.example.mit.bot.State;
import com.example.mit.model.*;
import com.example.mit.repository.OrderRepository;
import com.example.mit.repository.ProductWithAmountRepository;
import com.example.mit.repository.UserRepository;
import com.example.mit.util.ButtonModel.Col;
import com.example.mit.util.ButtonModel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.mit.util.TelegramUtil.createMessageTemplate;

@Service
@Component
public class BasketHandler implements Handler{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductWithAmountRepository amountRepository;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        return null;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, CallbackQuery callback) {

        List<Order> orders = orderRepository.findAllByUserAndOrderStateEquals(user,OrderState.DRAFT);
        if (orders.size()==0){

            Col col=new Col();
            col.add("Bosh menyu",State.START.name());

            return List.of(createMessageTemplate(user)
//                    .setText(MessagesInterface.BTN_PROFILE_LATIN+"\n"+ TelegramUtil.parseName(user))
                    .setText("*❗️Savatcha bo'sh!*")
                    .setReplyMarkup(col.getMarkup()));
        }


        if (parseString(callback.getData()).equals("amount")){
            amountRepository.deleteById(Long.valueOf(parseInt(callback.getData())));
        }
        List<ProductWithAmount> amounts = amountRepository.findAllByOrder(orders.get(0));



        String text="";
        Col col=new Col();
        Row row=new Row();
        col.add("✅Buyurtma berish!","order_"+orders.get(0).getId());
        for (ProductWithAmount amount:amounts){
            if (amount.getProduct().getActualPrice()!=null){
                row.clear();
                text+="Mahsulot:\n*"+amount.getProduct().getNameOz()+"\n"
                        +amount.getAmount()+"x"+amount.getProduct().getActualPrice()+"="
                        +(amount.getAmount()*Float.parseFloat(amount.getProduct().getActualPrice()))+"*\n\n";
                row.add("❌ O'chirish!","amount_"+amount.getId());
                row.add(amount.getProduct().getNameOz());
                col.add(row);
            }

        }





        col.add("Bosh menyu",State.START.name());

        return List.of(createMessageTemplate(user)
//                    .setText(MessagesInterface.BTN_PROFILE_LATIN+"\n"+ TelegramUtil.parseName(user))
                .setText(text)
                .setReplyMarkup(col.getMarkup()));
    }

    @Override
    public State operatedBotState() {
        return State.BASKET;
    }

    @Override
    public List<String> operatedCallBackQuery(User user) {
        List<String> list=new ArrayList<>();
        list.add(State.BASKET.name());
        list.add("amount_");
        list.add("order_");
        return list;
    }

    public List<Order> allDrafts(List<Order> orders){
        if (orders.size()>0){
            return orders
                    .stream()
                    .filter(item->item.getOrderState().equals(OrderState.DRAFT))
                    .collect(Collectors.toList());
        }
        return orders;

    }

    public Integer parseInt(String str){
        try {
            String[] parts = str.split("_");
            return Integer.parseInt(parts[2]);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public String parseString(String str){
        try {
            String[] parts = str.split("_");
            return parts[0];
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
}
