package ru.netology.page;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    public static SelenideElement header = $x("//h2[text()='Путешествие дня']");
    public static SelenideElement payBattonDebit =$x("//button//span[text()='Купить']/../..");
    public static SelenideElement payBattonCredit = $x("//button//span[text()='Купить в кредит']/../..");

    public MainPage(){
        header.shouldBe(visible);
        payBattonDebit.shouldBe(visible);
        payBattonCredit.shouldBe(visible);
    }

    public PageDebitPay openPageDebitPay(){
        payBattonDebit.click();
        return new PageDebitPay();
    }

    public PageCreditPay openPageCreditPay(){
        payBattonCredit.click();
        return new PageCreditPay();
    }

}
