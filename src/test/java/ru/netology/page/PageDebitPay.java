package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class PageDebitPay extends FormPaymentCard {

    public PageDebitPay() {
        super();
        formOfPaymentByCard.shouldBe(visible);
    }


}
