package com.example.mit.model.profile_handler_model;

import java.util.HashMap;
import java.util.Map;

public final class ProfileMenuStrings {
    public static Map<String, String> map_oz;
    static {
        map_oz = new HashMap<>();
        map_oz.put(ProfileEnums.MY_ORDERS.name(), "\uD83D\uDD16 Mening buyurtmalarim");
        map_oz.put(ProfileEnums.MY_LANGUAGE.name(), "\uD83C\uDF10 Tilni o'zgartirish");
        map_oz.put(ProfileEnums.MY_REGION.name(), "\uD83D\uDDFA Regionni o'zgartirish");
        map_oz.put(ProfileEnums.EXIT.name(), "\uD83D\uDD19 Ortga");
    }

    public static Map<String, String> map_uz;
    static {
        map_uz = new HashMap<>();
        map_uz.put(ProfileEnums.MY_ORDERS.name(), "\uD83D\uDD16 Менинг буюртмаларим");
        map_uz.put(ProfileEnums.MY_LANGUAGE.name(), "\uD83C\uDF10 Тилни ўзгартириш");
        map_uz.put(ProfileEnums.MY_REGION.name(), "\uD83D\uDDFA Регионни ўзгартириш");
        map_uz.put(ProfileEnums.EXIT.name(), "\uD83D\uDD19 Ортга");
    }

    public static Map<String, String> map_ru;
    static {
        map_ru = new HashMap<>();
        map_ru.put(ProfileEnums.MY_ORDERS.name(), "\uD83D\uDD16 Мои заказы");
        map_ru.put(ProfileEnums.MY_LANGUAGE.name(), "\uD83C\uDF10 Изменить язык");
        map_ru.put(ProfileEnums.MY_REGION.name(), "\uD83D\uDDFA Сменить регион");
        map_ru.put(ProfileEnums.EXIT.name(), "\uD83D\uDD19 Назад");
    }
}
