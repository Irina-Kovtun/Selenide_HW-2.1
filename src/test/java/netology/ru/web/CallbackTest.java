package netology.ru.web;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CallbackTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTestHappyPath() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Василий");
        form.$("[data-test-id=phone] input").setValue("+12345678999");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldWarnIfNameFieldIsEmpty() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("");
        form.$("[data-test-id=phone] input").setValue("+12345678999");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("button").click();
        $("[data-test-id=name] [class=input__sub]").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldWarnIfPhoneFieldIsEmpty() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Василий");
        form.$("[data-test-id=phone] input").setValue("");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("button").click();
        $("[data-test-id=phone] [class=input__sub]").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldWarnIfAllFieldsAreEmpty() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("");
        form.$("[data-test-id=phone] input").setValue("");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("button").click();
        $("[data-test-id=name] [class=input__sub]").shouldHave(exactText("Поле обязательно для заполнения"));
        $("[data-test-id=phone] [class=input__sub]").shouldHave(exactText("На указанный номер моб. тел. будет отправлен смс-код для подтверждения заявки на карту. Проверьте, что номер ваш и введен корректно."));
    }

    @Test
    void shouldWarnIfNameIsInLatin() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Vasya");
        form.$("[data-test-id=phone] input").setValue("+71234567899");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("button").click();
        $("[data-test-id=name] [class=input__sub]").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldWarnIfPhoneIsNot11Numbers() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Василий");
        form.$("[data-test-id=phone] input").setValue("+123456789");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("button").click();
        $("[data-test-id=phone] [class=input__sub]").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldWarnIfCheckboxIsEmpty() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Василий");
        form.$("[data-test-id=phone] input").setValue("+12345678999");
        form.$("button").click();
        $("[data-test-id='agreement'].input_invalid .checkbox__text").should(exist);
        $("[data-test-id='agreement'].input_invalid .checkbox__text").getCssValue("rgba(255, 92, 92, 1)");
    }
}


