package ru.netology.helpers;

import com.github.javafaker.Faker;

import java.util.Locale;

public class DataHelper {
    private DataHelper() {
    }

    private static final Faker faker = new Faker(new Locale("ru"));

    public static String getCity() {
        return faker.address().cityName()
                .replace("Новокузнецк", "Курск")
                .replace("Тольятти", "Курск")
                .replace("Сочи", "Тула");
    }

    public static String getPhone() {
        return faker.phoneNumber().phoneNumber();
    }

    public static String getName() {
        return faker.name().lastName().replace("ё", "е") + " " + faker.name().firstName().replace("ё", "е");
    }
}
