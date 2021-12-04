package ru.netology.web.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.web.data.Card;
import ru.netology.web.data.DbUtils;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataGenerator.*;

public class PaymentTest {
    DashboardPage dashboardPage = new DashboardPage();
    PaymentPage paymentPage = new PaymentPage();

    @BeforeEach
    void setUp() {
        DbUtils.clearTables();
        open("http://localhost:8080");
        dashboardPage.buy();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    //passed
    @Test
    void shouldBuyInPaymentGate() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        paymentPage.inputData(card);
        paymentPage.checkSuccessNotification();
        assertEquals("APPROVED", DbUtils.getPaymentStatus());
    }

    //passed
    @Test
    void shouldBuyInPaymentGateWithNameInLatinLetters() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidNameInLatinLetters(), getValidCvc());
        paymentPage.inputData(card);
        paymentPage.checkSuccessNotification();
        assertEquals("APPROVED", DbUtils.getPaymentStatus());
    }

    //failed
    @Test
    void shouldNotBuyInPaymentGateWithDeclinedCardNumber() {
        Card card = new Card(getDeclinedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        paymentPage.inputData(card);
        paymentPage.checkDeclineNotification();
        assertEquals("DECLINED", DbUtils.getPaymentStatus());
    }

    //CardField
    //failed
    @Test
    void shouldNotBuyInPaymentGateWithInvalidCardNumber() {
        Card card = new Card(getInvalidCardNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        paymentPage.inputData(card);
        paymentPage.checkDeclineNotification();
    }

    //passed
    @Test
    void shouldNotBuyInPaymentGateWithShortCardNumber() {
        Card card = new Card(getShortCardNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        paymentPage.inputData(card);
        paymentPage.checkInvalidFormat();
    }

    //failed
    @Test
    void shouldNotBuyInPaymentGateWithEmptyCardNumber() {
        Card card = new Card(null, getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        paymentPage.inputData(card);
        paymentPage.checkRequiredField();
    }

    //MonthField
    //failed
    @Test
    void shouldNotBuyInPaymentGateWithInvalidMonth() {
        Card card = new Card(getApprovedNumber(), "00", getNextYear(), getValidName(), getValidCvc());
        paymentPage.inputData(card);
        paymentPage.checkInvalidDate();
    }

    //passed
    @Test
    void shouldNotBuyInPaymentGateWithNonExistingMonth() {
        Card card = new Card(getApprovedNumber(), "13", getNextYear(), getValidName(), getValidCvc());
        paymentPage.inputData(card);
        paymentPage.checkInvalidDate();
    }

    //failed
    @Test
    void shouldNotBuyInPaymentGateWithExpiredMonth() {
        Card card = new Card(getApprovedNumber(), getLastMonth(), getCurrentYear(), getValidName(), getValidCvc());
        paymentPage.inputData(card);
        paymentPage.checkExpiredDate();
    }

    //failed
    @Test
    void shouldNotBuyInPaymentGateWithEmptyMonth() {
        Card card = new Card(getApprovedNumber(), null, getNextYear(), getValidName(), getValidCvc());
        paymentPage.inputData(card);
        paymentPage.checkRequiredField();
    }

    //YearField
    //passed
    @Test
    void shouldNotBuyInPaymentGateWithExpiredYear() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getLastYear(), getValidName(), getValidCvc());
        paymentPage.inputData(card);
        paymentPage.checkExpiredDate();
    }

    //failed
    @Test
    void shouldNotBuyInPaymentGateWithEmptyYear() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), null, getValidName(), getValidCvc());
        paymentPage.inputData(card);
        paymentPage.checkRequiredField();
    }

    //NameField
    //failed
    @Test
    void shouldNotBuyInPaymentGateWithOnlyName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getOnlyName(), getValidCvc());
        paymentPage.inputData(card);
        paymentPage.checkInvalidName();
    }

    //failed
    @Test
    void shouldNotBuyInPaymentGateWithOnlyNameInLatinLetters() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getOnlyNameInLatinLetters(), getValidCvc());
        paymentPage.inputData(card);
        paymentPage.checkInvalidName();
    }

    //failed
    @Test
    void shouldNotBuyInPaymentGateWithOnlySurname() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getOnlySurname(), getValidCvc());
        paymentPage.inputData(card);
        paymentPage.checkInvalidName();
    }

    //failed
    @Test
    void shouldNotBuyInPaymentGateWithOnlySurnameInLatinLetters() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getOnlySurnameInLatinLetters(), getValidCvc());
        paymentPage.inputData(card);
        paymentPage.checkInvalidName();
    }

    //failed
    @Test
    void shouldNotBuyInPaymentGateWithTooLongName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getTooLongName(), getValidCvc());
        paymentPage.inputData(card);
        paymentPage.checkLongName();
    }

    //failed
    @Test
    void shouldNotBuyInPaymentGateWithDigitsInName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getNameWithNumbers(), getValidCvc());
        paymentPage.inputData(card);
        paymentPage.checkInvalidDataName();
    }

    //failed
    @Test
    void shouldNotBuyInPaymentGateWithTooShortName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getNameWithOneLetter(), getValidCvc());
        paymentPage.inputData(card);
        paymentPage.checkShortName();
    }

    //passed
    @Test
    void shouldNotBuyInPaymentGateWithEmptyName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), null, getValidCvc());
        paymentPage.inputData(card);
        paymentPage.checkRequiredField();
    }

    //failed
    @Test
    void shouldNotBuyInPaymentGateWithSpaceInsteadOfName() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), " ", getValidCvc());
        paymentPage.inputData(card);
        paymentPage.checkInvalidDataName();
    }

    //CVC/CVVField
    //failed
    @Test
    void shouldNotBuyInPaymentGateWithOneDigitInCvc() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getCvcWithOneDigit());
        paymentPage.inputData(card);
        paymentPage.checkInvalidCvc();
    }

    //failed
    @Test
    void shouldNotBuyInPaymentGateWithTwoDigitsInCvc() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getCvcWithTwoDigits());
        paymentPage.inputData(card);
        paymentPage.checkInvalidCvc();
    }

    //passed
    @Test
    void shouldNotBuyInPaymentGateWithEmptyCvc() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidName(), null);
        paymentPage.inputData(card);
        paymentPage.checkRequiredField();
    }

    //AllEmptyFields
    //failed
    @Test
    void shouldNotBuyInPaymentGateWithAllEmptyFields() {
        Card card = new Card(null, null, null, null, null);
        paymentPage.inputData(card);
        paymentPage.checkAllFieldsAreRequired(); //TODO Изменить надписи под полями на "Поле обязательно для заполнения"
    }
}
