package ru.netology.data;

import com.github.javafaker.Faker;
import ru.netology.card.CardsBank;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class BankCardDataHalper{

    private static Faker faker = new Faker(new Locale("en"));

    private static String validCardNumber = "4444 4444 4444 4441";
    private static String invalidCardNumber = "4444 4444 4444 4442";

    private static String getValidHolder() {
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    private static String getValidCVC() {
        return faker.numerify("###");
    }

    // Карта проверка поля
    // Карта со статусом APPROVED, Все поля заполнены валидными данными
    public static CardsBank getCardValidValuesCurrentMonthYear () {
        return new CardsBank(validCardNumber,
                LocalDate.now().format(DateTimeFormatter.ofPattern("MM")),
                LocalDate.now().format(DateTimeFormatter.ofPattern("yy")),
                getValidHolder(),
                getValidCVC());
    }

   // Карта со статусом DECLINED, остальные поля заполнены валидными данными
    public static CardsBank getCardInValidValuesNextMonthYear () {
        return new CardsBank(invalidCardNumber,
                LocalDate.now().format(DateTimeFormatter.ofPattern("MM")),
                LocalDate.now().format(DateTimeFormatter.ofPattern("yy")),
                getValidHolder(),
                getValidCVC());
    }

    // Поле номер карты заполнена не полностью, остальные поля заполнены валидными данными
    public static CardsBank getIncompleteNumberCardValuesMonthYear() {
        return new CardsBank( "4444 4444 4444 444",
                LocalDate.now().format(DateTimeFormatter.ofPattern("MM")),
                LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy")),
                getValidHolder(),
                getValidCVC());
    }

    // Поле номер карты не заполнено, остальные поля заполнены валидными данными
    public static CardsBank getEmptyNumberCardValuesMonthYear () {
        return new CardsBank( " ",
                LocalDate.now().format(DateTimeFormatter.ofPattern("MM")),
                LocalDate.now().format(DateTimeFormatter.ofPattern("yy")),
                getValidHolder(),
                getValidCVC());
    }

    //Месяц проверка поля
    // Поле номер месяца заполнено одной цифрой, остальные поля заполнены валидными данными
    public static CardsBank getValidCardValuesInvalidMonthYear() {
        return new CardsBank( validCardNumber,
                "1",
                LocalDate.now().format(DateTimeFormatter.ofPattern("yy")),
                getValidHolder(),
                getValidCVC());
    }
    // Поле номер месяца заполнено двумя нулями, остальные поля заполнены валидными данными
    public static CardsBank getValidCardValuesTwoZeroMonthYear() {
        return new CardsBank( validCardNumber,
                "00",
                LocalDate.now().format(DateTimeFormatter.ofPattern("yy")),
                getValidHolder(),
                getValidCVC());
    }
    // Поле номер месяца заполнено невалидным числом, остальные поля заполнены валидными данными
    public static CardsBank getValidCardValuesUnrealMonthYear() {
        return new CardsBank( validCardNumber,
                "13",
                LocalDate.now().format(DateTimeFormatter.ofPattern("yy")),
                getValidHolder(),
                getValidCVC());
    }
    // Поле месяц заполнено месяцем меньше текущего, будущего года, остальные данные валидные
    public static CardsBank getCardValidValuesMinusMonthNextYear() {
        return new CardsBank( validCardNumber,
                LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("MM")),
                LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy")),
                getValidHolder(),
                getValidCVC());
    }
    // Поле номер месяца заполнено прошедшим месяцем текущего года, остальные поля заполнены валидными данными
    public static CardsBank getCardValidValuesMinusMonthCurrentYear() {
        return new CardsBank( validCardNumber,
                LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("ММ")),
                LocalDate.now().format(DateTimeFormatter.ofPattern("yy")),
                getValidHolder(),
                getValidCVC());
    }

    // Поле номер месяца не заполнено Все поля заполнены валидными данными, будущие месяц , год
    public static CardsBank getCardValidValuesEmptyMonthNextYear () {
        return new CardsBank( validCardNumber,
                "",
                LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy")),
                getValidHolder(),
                getValidCVC());
    }
    // Проверка поля год 13 тест
    //Поле год не заполнено, все поля заполнены валидными данными
    public static CardsBank getCardValidValuesMonthEmptyYear () {
        return new CardsBank( validCardNumber,
                LocalDate.now().format(DateTimeFormatter.ofPattern("ММ")),
                "",
                getValidHolder(),
                getValidCVC());
    }

    //Поле год заполнено двумя нулями, все поля заполнены валидными данными
    public static CardsBank getCardValidValuesMonthTwoZeroYear () {
        return new CardsBank( validCardNumber,
                LocalDate.now().format(DateTimeFormatter.ofPattern("ММ")),
                "00",
                getValidHolder(),
                getValidCVC());
    }
    //Поле год заполнено прошедшим годом, все поля заполнены валидными данными
    public static CardsBank getCardValidValuesMonthLastYear () {
        return new CardsBank( validCardNumber,
                LocalDate.now().format(DateTimeFormatter.ofPattern("ММ")),
                LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy")),
                getValidHolder(),
                getValidCVC());
    }
    //Поле год заполнено +6 лет, все поля заполнены валидными данными
    public static CardsBank getCardValidValuesMonthNextSixYear () {
        return new CardsBank( validCardNumber,
                LocalDate.now().format(DateTimeFormatter.ofPattern("ММ")),
                LocalDate.now().plusYears(6).format(DateTimeFormatter.ofPattern("yy")),
                getValidHolder(),
                getValidCVC());
    }
    //Проверка поля владелец
    //Поле владелец заполнено Киррилицей, все поля заполнены валидными данными
    public static CardsBank getKirHolderCardValidValuesMonthYear () {
        return new CardsBank( validCardNumber,
                LocalDate.now().format(DateTimeFormatter.ofPattern("ММ")),
                LocalDate.now().plusYears(2).format(DateTimeFormatter.ofPattern("yy")),
                "Иван Иванов",
                getValidCVC());
    }
    //Поле владелец не заполнено , все поля заполнены валидными данными
    public static CardsBank getEmptyHolderCardValidValuesMonthYear () {
        return new CardsBank( validCardNumber,
                LocalDate.now().format(DateTimeFormatter.ofPattern("ММ")),
                LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy")),
                "",
                getValidCVC());
    }
    //Поле владелец заполнено не полностью , все поля заполнены валидными данными
    public static CardsBank getOneWordHolderCardValidValuesMonthYear () {
        return new CardsBank( validCardNumber,
                LocalDate.now().format(DateTimeFormatter.ofPattern("ММ")),
                LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy")),
                "Ivanov",
                getValidCVC());
    }
    //Поле владелец заполнено не полностью , все поля заполнены валидными данными
    public static CardsBank getOneCharacterHolderCardValidValuesMonthYear () {
        return new CardsBank( validCardNumber,
                LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("ММ")),
                LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy")),
                "I",
                getValidCVC());
    }
    //Поле владелец заполнено цифрами , все поля заполнены валидными данными
    public static CardsBank getNumbersHolderCardValidValuesMonthYear () {
        return new CardsBank( validCardNumber,
                LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("ММ")),
                LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy")),
                "123645",
                getValidCVC());
    }
    //Поле владелец заполнено  , все поля заполнены валидными данными
    public static CardsBank getCharacterHolderCardValidValuesMonthYear () {
        return new CardsBank( validCardNumber,
                LocalDate.now().plusMonths(2).format(DateTimeFormatter.ofPattern("ММ")),
                LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy")),
                "*-06; ",
                getValidCVC());
    }
    //Проверка поля cvc/cw
    //Поле cvc/cw заполнено нулями , все поля заполнены валидными данными
    public static CardsBank getZeroCvcCardValidValuesMonthYear () {
        return new CardsBank( validCardNumber,
                LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("ММ")),
                LocalDate.now().plusYears(2).format(DateTimeFormatter.ofPattern("yy")),
                getValidHolder(),
                "000");
    }
    //Поле cvc/cw заполнено нулями , все поля заполнены валидными данными
    public static CardsBank getTwoNumberCvcCardValidValuesMonthYear () {
        return new CardsBank( validCardNumber,
                LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("ММ")),
                LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy")),
                getValidHolder(),
                "02");
    }
    //Поле cvc/cw заполнено нулями , все поля заполнены валидными данными
    public static CardsBank getEmptyCvcCardValidValuesMonthYear () {
        return new CardsBank( validCardNumber,
                LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("ММ")),
                LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy")),
                getValidHolder(),
                "");
    }

}
