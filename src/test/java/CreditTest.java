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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static ru.netology.data.SQLHelper.*;

public class CreditTest {

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
    public void testCreditPayValidCard() {
        var mainPage = new MainPage();
        var creditCardPay = mainPage.openPageCreditPay();
        creditCardPay.buyForm(BankCardDataHalper.getCardValidValuesCurrentMonthYear());
        creditCardPay.operationWasApprovedByBank();
        var cardStatus = getCreditCardStatus();
        assertEquals("APPROVED", cardStatus);
    }

    // Карта со статусом DECLINED, остальные поля заполнены валидными данными
    @Test
    public void testCreditPayInValidCard() {
        var mainPage = new MainPage();
        var creditCardPay = mainPage.openPageCreditPay();
        creditCardPay.buyForm(BankCardDataHalper.getCardInValidValuesNextMonthYear());
        creditCardPay.bankRefusedTocCarryOutOperation();
        var cardStatus = getCreditCardStatus();
        assertEquals("DECLINED", cardStatus);
    }

    // Карта со статусом APPROVED, поле номер карты заполнено не полностью. Остальные поля валидными данными
    @Test
    public void testCreditPayIncompleteCard() {
        var mainPage = new MainPage();
        var creditCardPay = mainPage.openPageCreditPay();
        creditCardPay.buyForm(BankCardDataHalper.getIncompleteNumberCardValuesMonthYear());
        creditCardPay.InvalidFormat();
    }

    // Поле номер карты не заполнено. Остальные поля валидными данными
    @Test
    public void testCreditPayEmptyCard() {
        var mainPage = new MainPage();
        var creditCardPay = mainPage.openPageCreditPay();
        creditCardPay.buyForm(BankCardDataHalper.getEmptyNumberCardValuesMonthYear());
        creditCardPay.FieldIsRequiredToFillIn();
    }

    // Поле номер месяца заполнено одной цифрой, остальные поля заполнены валидными данными
    @Test
    public void testCreditPayIncompleteMonth() {
        var mainPage = new MainPage();
        var creditCardPay = mainPage.openPageCreditPay();
        creditCardPay.buyForm(BankCardDataHalper.getValidCardValuesInvalidMonthYear());
        creditCardPay.InvalidFormat();
    }

    // Поле номер месяца заполнено двумя нулями
    @Test
    public void testCreditPayTwoZeroMonth() {
        var mainPage = new MainPage();
        var creditCardPay = mainPage.openPageCreditPay();
        creditCardPay.buyForm(BankCardDataHalper.getValidCardValuesTwoZeroMonthYear());
        creditCardPay.ValidityPeriodOfCardIsSpecifiedIncorrectly();
    }

    // Поле номер месяца заполнено числом больше 12
    @Test
    public void testCreditPayUnrealMonth() {
        var mainPage = new MainPage();
        var creditCardPay = mainPage.openPageCreditPay();
        creditCardPay.buyForm(BankCardDataHalper.getValidCardValuesUnrealMonthYear());
        creditCardPay.ValidityPeriodOfCardIsSpecifiedIncorrectly();
    }

    // Поле номер месяца заполнен месяцем меньше текущего, будущего года
    @Test
    public void testCreditPayMinusMonthNextYear() {
        var mainPage = new MainPage();
        var creditCardPay = mainPage.openPageCreditPay();
        creditCardPay.buyForm(BankCardDataHalper.getCardValidValuesMinusMonthNextYear());
        creditCardPay.operationWasApprovedByBank();
        var cardStatus = getCreditCardStatus();
        assertEquals("APPROVED", cardStatus);
    }

    // Поле номер месяца не заполнен
    @Test
    public void testCreditPayEmptyMonthCurrentYear() {
        var mainPage = new MainPage();
        var creditCardPay = mainPage.openPageCreditPay();
        creditCardPay.buyForm(BankCardDataHalper.getCardValidValuesEmptyMonthNextYear());
        creditCardPay.FieldIsRequiredToFillIn();
    }

    // Поле год не заполнено
    @Test
    public void testCreditPayMonthEmptyYear() {
        var mainPage = new MainPage();
        var creditCardPay = mainPage.openPageCreditPay();
        creditCardPay.buyForm(BankCardDataHalper.getCardValidValuesMonthEmptyYear());
        creditCardPay.FieldIsRequiredToFillIn();
    }

    // Поле год заполнено двумя нулями
    @Test
    public void testCreditPayMonthTwoZeroYear() {
        var mainPage = new MainPage();
        var creditCardPay = mainPage.openPageCreditPay();
        creditCardPay.buyForm(BankCardDataHalper.getCardValidValuesMonthTwoZeroYear());
        creditCardPay.CardExpired();
    }

    // Поле год заполнено +6 лет к текущему году
    @Test
    public void testCreditPayMonthNextSixYear() {
        var mainPage = new MainPage();
        var creditCardPay = mainPage.openPageCreditPay();
        creditCardPay.buyForm(BankCardDataHalper.getCardValidValuesMonthNextSixYear());
        creditCardPay.ValidityPeriodOfCardIsSpecifiedIncorrectly();
    }

    // Поле владелец заполнено киррилицей
    @Test
    public void testCreditPayMonthYearHolderOfKirrilitsa() {
        var mainPage = new MainPage();
        var creditCardPay = mainPage.openPageCreditPay();
        creditCardPay.buyForm(BankCardDataHalper.getKirrilitsaHolderCardValidValuesMonthYear());
        creditCardPay.InvalidFormat();
    }

    // Поле владелец не заполнено
    @Test
    public void testCreditPayMonthYearEmptyHolder() {
        var mainPage = new MainPage();
        var creditCardPay = mainPage.openPageCreditPay();
        creditCardPay.buyForm(BankCardDataHalper.getEmptyHolderCardValidValuesMonthYear());
        creditCardPay.FieldIsRequiredToFillIn();
    }

    // Поле владелец заполнено не полностью
    @Test
    public void testCreditPayMonthYearOneWordHolder() {
        var mainPage = new MainPage();
        var creditCardPay = mainPage.openPageCreditPay();
        creditCardPay.buyForm(BankCardDataHalper.getOneWordHolderCardValidValuesMonthYear());
        creditCardPay.InvalidFormat();
    }

    // Поле владелец заполнено одним символом на латинице
    @Test
    public void testCreditPayMonthYearOneCharacterHolder() {
        var mainPage = new MainPage();
        var creditCardPay = mainPage.openPageCreditPay();
        creditCardPay.buyForm(BankCardDataHalper.getOneCharacterHolderCardValidValuesMonthYear());
        creditCardPay.InvalidFormat();
    }

    // Поле владелец заполнено цифрами
    @Test
    public void testCreditPayMonthYearNumbersHolder() {
        var mainPage = new MainPage();
        var creditCardPay = mainPage.openPageCreditPay();
        creditCardPay.buyForm(BankCardDataHalper.getNumbersHolderCardValidValuesMonthYear());
        creditCardPay.InvalidFormat();
    }

    // Поле владелец заполнено спец символами
    @Test
    public void testCreditPayMonthYearCharactersHolder() {
        var mainPage = new MainPage();
        var creditCardPay = mainPage.openPageCreditPay();
        creditCardPay.buyForm(BankCardDataHalper.getCharacterHolderCardValidValuesMonthYear());
        creditCardPay.InvalidFormat();
    }

    // Поле CVC/CVV
    @Test
    public void testCreditPayMonthYearHolderZeroCvc() {
        var mainPage = new MainPage();
        var creditCardPay = mainPage.openPageCreditPay();
        creditCardPay.buyForm(BankCardDataHalper.getZeroCvcCardValidValuesMonthYear());
        creditCardPay.InvalidFormat();
    }

    // Поле CVC/CVV заполнено двумя цифрами
    @Test
    public void testCreditPayMonthYearHolderTwoNumbersCvc() {
        var mainPage = new MainPage();
        var creditCardPay = mainPage.openPageCreditPay();
        creditCardPay.buyForm(BankCardDataHalper.getTwoNumberCvcCardValidValuesMonthYear());
        creditCardPay.InvalidFormat();
    }

    // Поле CVC/CVV не заполнено , остальные поля заполнены валидными данными
    @Test
    public void testCreditPayMonthYearHolderEmptyCvc() {
        var mainPage = new MainPage();
        var creditCardPay = mainPage.openPageCreditPay();
        creditCardPay.buyForm(BankCardDataHalper.getEmptyCvcCardValidValuesMonthYear());
        creditCardPay.InvalidFormat();
    }

}
