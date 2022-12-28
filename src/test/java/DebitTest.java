import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.card.CardsBank;
import ru.netology.data.BankCardDataHalper;
import ru.netology.page.MainPage;
import ru.netology.page.PageDebitPay;

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
    static void tearDown(){
        cleanDatabase();
    }

    @Test
    public void testDebitPayValidCard(){
        var mainPage = new MainPage();
        var debitCardPay = mainPage.openPageDebitPay();
        debitCardPay.buyForm(BankCardDataHalper.getCardValidValuesCurrentMonthYear());
        debitCardPay.operationWasApprovedByBank();
        var cardStatus = getDebitCardStatus();
        assertEquals("APPROVED", cardStatus);

    }




}
