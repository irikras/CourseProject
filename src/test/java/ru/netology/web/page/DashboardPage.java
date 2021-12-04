package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final SelenideElement buyButton = $$("button").findBy(Condition.text("Купить"));

    public PaymentPage buy() {
        buyButton.click();
        return new PaymentPage();
    }
}
