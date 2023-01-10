import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.BankCardDataHalper;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.SQLHelper.cleanDatabase;
import static ru.netology.data.SQLHelper.getDebitCardStatus;

public class DebitTest {
    private static MainPage mainPage;

    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());

    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @BeforeEach
    public void openPage() {
        open("http://localhost:8080/");
    }

    @AfterAll
    static void tearDown() {
        cleanDatabase();
    }

    // Карта со статусом APPROVED, Все поля заполнены валидными данными
    @Test
    public void testDebitPayValidCard() {
        var mainPage = new MainPage();
        var debitCardPay = mainPage.openPageDebitPay();
        debitCardPay.buyForm(BankCardDataHalper.getCardValidValuesCurrentMonthYear());
        debitCardPay.operationWasApprovedByBank();
        var cardStatus = getDebitCardStatus();
        assertEquals("APPROVED", cardStatus);
    }

    // Карта со статусом DECLINED, остальные поля заполнены валидными данными // bug
    @Test
    public void testDebitPayInValidCard() {
        var mainPage = new MainPage();
        var debitCardPay = mainPage.openPageDebitPay();
        debitCardPay.buyForm(BankCardDataHalper.getCardInValidValuesNextMonthYear());
        debitCardPay.bankRefusedTocCarryOutOperation();
        var cardStatus = getDebitCardStatus();
        assertEquals("DECLINED", cardStatus);
    }

    // Карта со статусом APPROVED, поле номер карты заполнено не полностью. Остальные поля валидными данными
    @Test
    public void testDebitPayIncompleteCard() {
        var mainPage = new MainPage();
        var debitCardPay = mainPage.openPageDebitPay();
        debitCardPay.buyForm(BankCardDataHalper.getIncompleteNumberCardValuesMonthYear());
        debitCardPay.InvalidFormat();
            }

    // Поле номер карты не заполнено. Остальные поля валидными данными
    @Test
    public void testDebitPayEmptyCard() {
        var mainPage = new MainPage();
        var debitCardPay = mainPage.openPageDebitPay();
        debitCardPay.buyForm(BankCardDataHalper.getEmptyNumberCardValuesMonthYear());
        debitCardPay.InvalidFormat();
          }

    // Поле номер месяца заполнено одной цифрой, остальные поля заполнены валидными данными
    @Test
    public void testDebitPayIncompleteMonth() {
        var mainPage = new MainPage();
        var debitCardPay = mainPage.openPageDebitPay();
        debitCardPay.buyForm(BankCardDataHalper.getValidCardValuesInvalidMonthYear());
        debitCardPay.InvalidFormat();
         }

    // Поле номер месяца заполнено двумя нулями
    @Test
    public void testDebitPayTwoZeroMonth() {
        var mainPage = new MainPage();
        var debitCardPay = mainPage.openPageDebitPay();
        debitCardPay.buyForm(BankCardDataHalper.getValidCardValuesTwoZeroMonthYear());
        debitCardPay.ValidityPeriodOfCardIsSpecifiedIncorrectly();
    }

    // Поле номер месяца заполнено числом больше 12
    @Test
    public void testDebitPayUnrealMonth() {
        var mainPage = new MainPage();
        var debitCardPay = mainPage.openPageDebitPay();
        debitCardPay.buyForm(BankCardDataHalper.getValidCardValuesUnrealMonthYear());
        debitCardPay.ValidityPeriodOfCardIsSpecifiedIncorrectly();
            }

    // Поле номер месяца заполнен месяцем меньше текущего, будущего года
    @Test
    public void testDebitPayMinusMonthNextYear() {
        var mainPage = new MainPage();
        var debitCardPay = mainPage.openPageDebitPay();
        debitCardPay.buyForm(BankCardDataHalper.getCardValidValuesMinusMonthNextYear());
        debitCardPay.operationWasApprovedByBank();
        var cardStatus = getDebitCardStatus();
        assertEquals("APPROVED", cardStatus);
    }

    // Поле номер месяца не заполнен
    @Test
    public void testDebitPayEmptyMonthCurrentYear() {
        var mainPage = new MainPage();
        var debitCardPay = mainPage.openPageDebitPay();
        debitCardPay.buyForm(BankCardDataHalper.getCardValidValuesEmptyMonthNextYear());
        debitCardPay.InvalidFormat();
    }

     // Поле год не заполнено
    @Test
    public void testDebitPayMonthEmptyYear() {
        var mainPage = new MainPage();
        var debitCardPay = mainPage.openPageDebitPay();
        debitCardPay.buyForm(BankCardDataHalper.getCardValidValuesMonthEmptyYear());
        debitCardPay.InvalidFormat();
    }

    // Поле год заполнено двумя нулями
    @Test
    public void testDebitPayMonthTwoZeroYear() {
        var mainPage = new MainPage();
        var debitCardPay = mainPage.openPageDebitPay();
        debitCardPay.buyForm(BankCardDataHalper.getCardValidValuesMonthTwoZeroYear());
        debitCardPay.CardExpired();
    }

    // Поле год заполнено +6 лет к текущему году
    @Test
    public void testDebitPayMonthNextSixYear() {
        var mainPage = new MainPage();
        var debitCardPay = mainPage.openPageDebitPay();
        debitCardPay.buyForm(BankCardDataHalper.getCardValidValuesMonthNextSixYear());
        debitCardPay.ValidityPeriodOfCardIsSpecifiedIncorrectly();
    }

    // Поле владелец заполнено киррилицей
    @Test
    public void testDebitPayMonthYearHolderOfKirrilitsa() {
        var mainPage = new MainPage();
        var debitCardPay = mainPage.openPageDebitPay();
        debitCardPay.buyForm(BankCardDataHalper.getKirrilitsaHolderCardValidValuesMonthYear());
        debitCardPay.InvalidFormat();
    }

    // Поле владелец не заполнено
    @Test
    public void testDebitPayMonthYearEmptyHolder() {
        var mainPage = new MainPage();
        var debitCardPay = mainPage.openPageDebitPay();
        debitCardPay.buyForm(BankCardDataHalper.getEmptyHolderCardValidValuesMonthYear());
        debitCardPay.FieldIsRequiredToFillIn();
    }

    // Поле владелец заполнено не полностью
    @Test
    public void testDebitPayMonthYearOneWordHolder() {
        var mainPage = new MainPage();
        var debitCardPay = mainPage.openPageDebitPay();
        debitCardPay.buyForm(BankCardDataHalper.getOneWordHolderCardValidValuesMonthYear());
        debitCardPay.InvalidFormat();
    }

    // Поле владелец заполнено одним символом на латинице
    @Test
    public void testDebitPayMonthYearOneCharacterHolder() {
        var mainPage = new MainPage();
        var debitCardPay = mainPage.openPageDebitPay();
        debitCardPay.buyForm(BankCardDataHalper.getOneCharacterHolderCardValidValuesMonthYear());
        debitCardPay.InvalidFormat();
    }

    // Поле владелец заполнено цифрами
    @Test
    public void testDebitPayMonthYearNumbersHolder() {
        var mainPage = new MainPage();
        var debitCardPay = mainPage.openPageDebitPay();
        debitCardPay.buyForm(BankCardDataHalper.getNumbersHolderCardValidValuesMonthYear());
        debitCardPay.InvalidFormat();
    }

    // Поле владелец заполнено спец символами
    @Test
    public void testDebitPayMonthYearCharactersHolder() {
        var mainPage = new MainPage();
        var debitCardPay = mainPage.openPageDebitPay();
        debitCardPay.buyForm(BankCardDataHalper.getCharacterHolderCardValidValuesMonthYear());
        debitCardPay.InvalidFormat();
    }

    // Поле CVC/CVV
    @Test
    public void testDebitPayMonthYearHolderZeroCvc() {
        var mainPage = new MainPage();
        var debitCardPay = mainPage.openPageDebitPay();
        debitCardPay.buyForm(BankCardDataHalper.getZeroCvcCardValidValuesMonthYear());
        debitCardPay.InvalidFormat();
    }

    // Поле CVC/CVV заполнено двумя цифрами
    @Test
    public void testDebitPayMonthYearHolderTwoNumbersCvc() {
        var mainPage = new MainPage();
        var debitCardPay = mainPage.openPageDebitPay();
        debitCardPay.buyForm(BankCardDataHalper.getTwoNumberCvcCardValidValuesMonthYear());
        debitCardPay.InvalidFormat();
    }

    // Поле CVC/CVV не заполнено , остальные поля заполнены валидными данными
    @Test
    public void testDebitPayMonthYearHolderEmptyCvc() {
        var mainPage = new MainPage();
        var debitCardPay = mainPage.openPageDebitPay();
        debitCardPay.buyForm(BankCardDataHalper.getEmptyCvcCardValidValuesMonthYear());
        debitCardPay.InvalidFormat();
    }

}
