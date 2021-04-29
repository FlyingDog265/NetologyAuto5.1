package ru.netology.cases;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.*;
import static ru.netology.helpers.DataHelper.*;
import static ru.netology.helpers.DateHelper.getDate;
import static ru.netology.helpers.DateHelper.getShiftedDate;

public class CardWithDeliveryTest {

    Condition clickable = and("can be clicked", visible, enabled);
    Duration duration = Duration.ofSeconds(15);

    private final int week = 7;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void start() {
        open("http://localhost:9999/");
        element("h2[class*='heading']").shouldBe(visible);
    }

    @Test
    @DisplayName("Отображение элементов формы")
    public void shouldShowAllElements() {
        element("span[data-test-id='city'] input").shouldBe(visible);
        element("span[data-test-id='city'] span[class$='sub']").shouldBe(visible)
                .shouldHave(text("Выберите ваш город"));
        element("span[data-test-id='date'] input").shouldBe(visible);
        element("span[data-test-id='date'] span[class$='icon'] button").shouldBe(visible);
        element("span[data-test-id='date'] span[class$='sub']").shouldBe(visible)
                .shouldHave(text("Выберите дату встречи с представителем банка"));
        element("span[data-test-id='name'] input").shouldBe(visible);
        element("span[data-test-id='name'] span[class$='top']").shouldBe(visible);
        element("span[data-test-id='name'] span[class$='sub']").shouldBe(visible);
        element("span[data-test-id='phone'] input").shouldBe(visible);
        element("span[data-test-id='phone'] span[class$='top']").shouldBe(visible);
        element("span[data-test-id='phone'] span[class$='sub']").shouldBe(visible);
        element("label[data-test-id='agreement'] > span[class$='box']").shouldBe(visible);
        element("label[data-test-id='agreement'] > span[class$='text']").shouldBe(visible);
        element("span[class^='icon'][class*='guard']").shouldBe(visible);
        element("button[class^='button']").shouldBe(visible);
    }

    @Test
    @DisplayName("Проверка текстов формы")
    public void shouldCheckAllTexts() {
        element("h2[class*='heading']").shouldHave(text("Карта с доставкой!"));
        element("span[data-test-id='city'] span[class$='sub']")
                .shouldHave(text("Выберите ваш город"));
        element("span[data-test-id='date'] span[class$='sub']")
                .shouldHave(text("Выберите дату встречи с представителем банка"));
        element("span[data-test-id='name'] span[class$='top']")
                .shouldHave(text("Фамилия и имя"));
        element("span[data-test-id='name'] span[class$='sub']")
                .shouldHave(text("Укажите точно как в паспорте"));
        element("span[data-test-id='phone'] span[class$='top']")
                .shouldHave(text("Мобильный телефон"));
        element("span[data-test-id='phone'] span[class$='sub']")
                .shouldHave(text("На указанный номер моб. тел. будет отправлен смс-код для подтверждения заявки на карту." +
                        " Проверьте, что номер ваш и введен корректно."));
        element("label[data-test-id='agreement'] > span[class$='text']")
                .shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
        element("p[class^='paragraph']")
                .shouldHave(text("Мы гарантируем безопасность ваших данных"));
    }

    @Test
    @DisplayName("Отправка формы")
    public void shouldSendForm() {
        String nextWeekDate = getShiftedDate(week);
        element("span[data-test-id='city'] input").shouldBe(clickable).click();
        element("span[data-test-id='city'] input").sendKeys(getCity());
        element("span[data-test-id='city'] input").pressTab();
        element("div[class^='popup'] > div[class^='calendar']").shouldBe(visible);
        element("span[data-test-id='date'] input").sendKeys(nextWeekDate);
        element("span[data-test-id='date'] input").pressTab();
        element("span[data-test-id='name'] input").sendKeys(getName());
        element("span[data-test-id='phone'] input").shouldBe(clickable).click();
        element("span[data-test-id='phone'] input").sendKeys(getPhone());
        element("label[data-test-id='agreement'] > span[class$='box']").shouldBe(clickable).click();
        element("button[class^='button']").shouldBe(clickable).click();
        element("div[data-test-id='success-notification']").shouldBe(visible, duration);
        element("div[data-test-id='success-notification'] > div[class$='title']")
                .shouldBe(visible)
                .shouldHave(text("Успешно!"));
        element("div[data-test-id='success-notification'] > div[class$='content']")
                .shouldBe(visible)
                .shouldHave(text("Встреча успешно запланирована на " + nextWeekDate));
    }

    @Test
    @DisplayName("Повторная отправка формы с перепланированием")
    public void shouldSendReplanForm() {
        String date = getShiftedDate(week);
        String anotherDate = getShiftedDate(9);
        element("span[data-test-id='city'] input").shouldBe(clickable).click();
        element("span[data-test-id='city'] input").sendKeys(getCity());
        element("span[data-test-id='city'] input").pressTab();
        element("div[class^='popup'] > div[class^='calendar']").shouldBe(visible);
        element("span[data-test-id='date'] input").sendKeys(date);
        element("span[data-test-id='date'] input").pressTab();
        element("span[data-test-id='name'] input").sendKeys(getName());
        element("span[data-test-id='phone'] input").shouldBe(clickable).click();
        element("span[data-test-id='phone'] input").sendKeys(getPhone());
        element("label[data-test-id='agreement'] > span[class$='box']").shouldBe(clickable).click();
        element("button[class^='button']").shouldBe(clickable).click();
        element("div[data-test-id='success-notification']").shouldBe(visible, duration);
        element("div[data-test-id='success-notification'] > div[class$='title']")
                .shouldBe(visible)
                .shouldHave(text("Успешно!"));
        element("div[data-test-id='success-notification'] > div[class$='content']")
                .shouldBe(visible)
                .shouldHave(text("Встреча успешно запланирована на " + date));
        element("span[data-test-id='date'] input").click();
        element("span[data-test-id='date'] input").sendKeys(chord(CONTROL, "a"));
        element("span[data-test-id='date'] input").sendKeys(anotherDate);
        element("button[class^='button']").click();
        element("div[data-test-id='replan-notification']").shouldBe(visible, duration);
        element("div[data-test-id='replan-notification'] > div[class$='title']")
                .shouldBe(visible)
                .shouldHave(text("Необходимо подтверждение"));
        element("div[data-test-id='replan-notification'] > div[class$='content']")
                .shouldBe(visible)
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        element("div[data-test-id='replan-notification']  button:nth-of-type(1)").shouldBe(clickable).click();
        element("div[data-test-id='success-notification']").shouldBe(visible, duration);
        element("div[data-test-id='success-notification'] > div[class$='title']")
                .shouldBe(visible)
                .shouldHave(text("Успешно!"));
        element("div[data-test-id='success-notification'] > div[class$='content']")
                .shouldBe(visible)
                .shouldHave(text("Встреча успешно запланирована на " + anotherDate));
    }

    @Test
    @DisplayName("Отображение ошибки при отправке пустого поля \"Город\" ")
    public void shouldShowErrorMessageEmptyCity() {
        element("span[data-test-id='date'] input").click();
        element("span[data-test-id='date'] input").sendKeys(getShiftedDate(week));
        element("span[data-test-id='date'] input").pressTab();
        element("span[data-test-id='name'] input").sendKeys(getName());
        element("span[data-test-id='phone'] input").click();
        element("span[data-test-id='phone'] input").sendKeys(getPhone());
        element("label[data-test-id='agreement'] > span[class$='box']").click();
        element("button[class^='button']").click();
        element("button[class^='button'] span[class^='spin'][class*='visible']").shouldBe(not(visible));
        element("span[data-test-id='city'] span[class$='sub']")
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Отображение ошибки при отправке в поле \"Город\" не субъекта РФ")
    public void shouldShowErrorMessageWithCityNotRf() {
        element("span[data-test-id='city'] input").shouldBe(clickable).click();
        element("span[data-test-id='city'] input").sendKeys("Бостон");
        element("span[data-test-id='city'] input").pressTab();
        element("div[class^='popup'] > div[class^='calendar']").shouldBe(visible);
        element("span[data-test-id='date'] input").sendKeys(getShiftedDate(week));
        element("span[data-test-id='date'] input").pressTab();
        element("span[data-test-id='name'] input").sendKeys(getName());
        element("span[data-test-id='phone'] input").click();
        element("span[data-test-id='phone'] input").sendKeys(getPhone());
        element("label[data-test-id='agreement'] > span[class$='box']").click();
        element("button[class^='button']").shouldBe(clickable).click();
        element("button[class^='button'] span[class^='spin'][class*='visible']").shouldBe(not(visible));
        element("span[data-test-id='city'] span[class$='sub']")
                .shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    @DisplayName("Отображение ошибки при отправке в поле \"Дата встречи\" пустой даты")
    public void shouldShowErrorMessageEmptyDate() {
        element("span[data-test-id='city'] input").click();
        element("span[data-test-id='city'] input").sendKeys(getCity());
        element("span[data-test-id='city'] input").pressTab();
        element("div[class^='popup'] > div[class^='calendar']").shouldBe(visible);
        element("span[data-test-id='date'] input").sendKeys(BACK_SPACE);
        element("span[data-test-id='date'] input").pressTab();
        element("span[data-test-id='name'] input").sendKeys(getName());
        element("span[data-test-id='phone'] input").click();
        element("span[data-test-id='phone'] input").sendKeys(getPhone());
        element("label[data-test-id='agreement'] > span[class$='box']").click();
        element("button[class^='button']").shouldBe(clickable).click();
        element("button[class^='button'] span[class^='spin'][class*='visible']").shouldBe(not(visible));
        element("span[data-test-id='date'] span[class$='sub']")
                .shouldHave(text("Неверно введена дата"));
    }

    @Test
    @DisplayName("Отображение ошибки при отправке в поле \"Дата встречи\" несуществующей даты")
    public void shouldShowErrorMessageWrongDate() {
        element("span[data-test-id='city'] input").click();
        element("span[data-test-id='city'] input").sendKeys(getCity());
        element("span[data-test-id='city'] input").pressTab();
        element("div[class^='popup'] > div[class^='calendar']").shouldBe(visible);
        element("span[data-test-id='date'] input").sendKeys("32.01.2020");
        element("span[data-test-id='date'] input").pressTab();
        element("span[data-test-id='name'] input").sendKeys(getName());
        element("span[data-test-id='phone'] input").click();
        element("span[data-test-id='phone'] input").sendKeys(getPhone());
        element("label[data-test-id='agreement'] > span[class$='box']").click();
        element("button[class^='button']").click();
        element("button[class^='button'] span[class^='spin'][class*='visible']").shouldBe(not(visible));
        element("span[data-test-id='date'] span[class$='sub']")
                .shouldHave(text("Неверно введена дата"));
    }

    @Test
    @DisplayName("Отображение даты плюс три дня при вводе текущей даты")
    public void shouldShowDatePlusThreeWhenSendCurrentDate() {
        element("span[data-test-id='date'] input").click();
        element("div[class^='popup'] > div[class^='calendar']").shouldBe(visible);
        element("span[data-test-id='date'] input").sendKeys(getDate());
        element("span[data-test-id='date'] input").pressTab();
        element("span[data-test-id='date'] input").shouldHave(value(getShiftedDate(3)));
    }

    @Test
    @DisplayName("Отображение ошибки при отправке пустого поля \"Фамилия и имя\" ")
    public void shouldShowErrorMessageEmptyName() {
        element("span[data-test-id='city'] input").click();
        element("span[data-test-id='city'] input").sendKeys(getCity());
        element("span[data-test-id='city'] input").pressTab();
        element("div[class^='popup'] > div[class^='calendar']").shouldBe(visible);
        element("span[data-test-id='date'] input").sendKeys(getShiftedDate(week));
        element("span[data-test-id='date'] input").pressTab();
        element("span[data-test-id='phone'] input").click();
        element("span[data-test-id='phone'] input").sendKeys(getPhone());
        element("label[data-test-id='agreement'] > span[class$='box']").click();
        element("button[class^='button']").click();
        element("button[class^='button'] span[class^='spin'][class*='visible']").shouldBe(not(visible));
        element("span[data-test-id='name'] span[class$='sub']")
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Отображение ошибки при отправке латиницы в поле \"Фамилия и имя\" ")
    public void shouldShowErrorMessageLatinName() {
        element("span[data-test-id='city'] input").click();
        element("span[data-test-id='city'] input").sendKeys(getCity());
        element("span[data-test-id='city'] input").pressTab();
        element("div[class^='popup'] > div[class^='calendar']").shouldBe(visible);
        element("span[data-test-id='date'] input").sendKeys(getShiftedDate(week));
        element("span[data-test-id='date'] input").pressTab();
        element("span[data-test-id='name'] input").sendKeys("Some Name");
        element("span[data-test-id='phone'] input").click();
        element("span[data-test-id='phone'] input").sendKeys(getPhone());
        element("label[data-test-id='agreement'] > span[class$='box']").click();
        element("button[class^='button']").click();
        element("button[class^='button'] span[class^='spin'][class*='visible']").shouldBe(not(visible));
        element("span[data-test-id='name'] span[class$='sub']")
                .shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    @DisplayName("Отображение ошибки при отправке цифр в поле \"Фамилия и имя\" ")
    public void shouldShowErrorMessageNumbersName() {
        element("span[data-test-id='city'] input").click();
        element("span[data-test-id='city'] input").sendKeys(getCity());
        element("span[data-test-id='city'] input").pressTab();
        element("div[class^='popup'] > div[class^='calendar']").shouldBe(visible);
        element("span[data-test-id='date'] input").sendKeys(getShiftedDate(week));
        element("span[data-test-id='date'] input").pressTab();
        element("span[data-test-id='name'] input").sendKeys("435345655");
        element("span[data-test-id='phone'] input").click();
        element("span[data-test-id='phone'] input").sendKeys(getPhone());
        element("label[data-test-id='agreement'] > span[class$='box']").click();
        element("button[class^='button']").click();
        element("button[class^='button'] span[class^='spin'][class*='visible']").shouldBe(not(visible));
        element("span[data-test-id='name'] span[class$='sub']")
                .shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    @DisplayName("Отображение ошибки при отправке пустого поля \"Мобильный телефон\" ")
    public void shouldShowErrorMessageEmptyPhone() {
        element("span[data-test-id='city'] input").click();
        element("span[data-test-id='city'] input").sendKeys(getCity());
        element("span[data-test-id='city'] input").pressTab();
        element("div[class^='popup'] > div[class^='calendar']").shouldBe(visible);
        element("span[data-test-id='date'] input").sendKeys(getShiftedDate(week));
        element("span[data-test-id='date'] input").pressTab();
        element("span[data-test-id='name'] input").sendKeys("Ильин Илья");
        element("label[data-test-id='agreement'] > span[class$='box']").click();
        element("button[class^='button']").click();
        element("button[class^='button'] span[class^='spin'][class*='visible']").shouldBe(not(visible));
        element("span[data-test-id='phone'] span[class$='sub']")
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName("Проверка ввода телефона без плюса в поле \"Мобильный телефон\" ")
    public void shouldWorkWithPhoneWithoutPlus() {
        element("span[data-test-id='city'] input").click();
        element("span[data-test-id='city'] input").sendKeys(getCity());
        element("span[data-test-id='city'] input").pressTab();
        element("div[class^='popup'] > div[class^='calendar']").shouldBe(visible);
        element("span[data-test-id='date'] input").sendKeys(getShiftedDate(week));
        element("span[data-test-id='date'] input").pressTab();
        element("span[data-test-id='name'] input").sendKeys(getName());
        element("span[data-test-id='phone'] input").click();
        element("span[data-test-id='phone'] input").sendKeys("79658711755");
        element("label[data-test-id='agreement'] > span[class$='box']").click();
        element("span[data-test-id='phone'] input").shouldHave(value("+7 965 871 17 55"));
        element("button[class^='button']").click();
        element("div[data-test-id='success-notification']").shouldBe(visible, duration);
        element("div[data-test-id='success-notification'] > div[class$='title']")
                .shouldBe(visible)
                .shouldHave(text("Успешно!"));
    }

    @Test
    @DisplayName("Отображение ошибки при отправке телефона с меньшим кол-вом цифр в поле \"Мобильный телефон\" ")
    public void shouldShowErrorMessageWithShortPhone() {
        element("span[data-test-id='city'] input").click();
        element("span[data-test-id='city'] input").sendKeys(getCity());
        element("span[data-test-id='city'] input").pressTab();
        element("div[class^='popup'] > div[class^='calendar']").shouldBe(visible);
        element("span[data-test-id='date'] input").sendKeys(getShiftedDate(week));
        element("span[data-test-id='date'] input").pressTab();
        element("span[data-test-id='name'] input").sendKeys(getName());
        element("span[data-test-id='phone'] input").click();
        element("span[data-test-id='phone'] input").sendKeys("+7965871");
        element("label[data-test-id='agreement'] > span[class$='box']").click();
        element("button[class^='button']").click();
        element("button[class^='button'] span[class^='spin'][class*='visible']").shouldBe(not(visible));
        element("span[data-test-id='phone'] span[class$='sub']")
                .shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    @DisplayName("Проверка ввода телефона с буквами в поле \"Мобильный телефон\" ")
    public void shouldShowErrorMessageWithLettersPhone() {
        element("span[data-test-id='city'] input").click();
        element("span[data-test-id='city'] input").sendKeys(getCity());
        element("span[data-test-id='city'] input").pressTab();
        element("div[class^='popup'] > div[class^='calendar']").shouldBe(visible);
        element("span[data-test-id='date'] input").sendKeys(getShiftedDate(week));
        element("span[data-test-id='date'] input").pressTab();
        element("span[data-test-id='name'] input").sendKeys(getName());
        element("span[data-test-id='phone'] input").click();
        element("span[data-test-id='phone'] input").sendKeys("+7965;%;%871вап7755");
        element("label[data-test-id='agreement'] > span[class$='box']").click();
        element("span[data-test-id='phone'] input").shouldHave(value("+7 965 871 77 55"));
        element("button[class^='button']").click();
        element("div[data-test-id='success-notification']").shouldBe(visible, duration);
        element("div[data-test-id='success-notification'] > div[class$='title']")
                .shouldBe(visible)
                .shouldHave(text("Успешно!"));
    }

    @Test
    @DisplayName("Отображение ошибки при отсутствии нажатия на чекбокс")
    public void shouldShowErrorCheckbox() {
        element("span[data-test-id='city'] input").click();
        element("span[data-test-id='city'] input").sendKeys(getCity());
        element("span[data-test-id='city'] input").pressTab();
        element("div[class^='popup'] > div[class^='calendar']").shouldBe(visible);
        element("span[data-test-id='date'] input").sendKeys(getShiftedDate(week));
        element("span[data-test-id='date'] input").pressTab();
        element("span[data-test-id='name'] input").sendKeys(getName());
        element("span[data-test-id='phone'] input").click();
        element("span[data-test-id='phone'] input").sendKeys(getPhone());
        element("button[class^='button']").click();
        element("button[class^='button'] span[class^='spin'][class*='visible']").shouldBe(not(visible));
        element("label[data-test-id='agreement'][class*='invalid'").shouldBe(visible);
    }

    @Test
    @DisplayName("Выбор НП из списка")
    public void shouldFindCityFromCityList() {
        element("span[data-test-id='city'] input").click();
        element("span[data-test-id='city'] input").sendKeys("Но");
        elements("div[class^='menu-item']").findBy(text("Новосибирск")).click();
        element("span[data-test-id='city'] input").shouldHave(value("Новосибирск"));
    }

    @Test
    @DisplayName("Проверка не отображения списка НП при вводе одного символа")
    public void shouldHideCityListWithOneSymbol() {
        element("span[data-test-id='city'] input").click();
        element("span[data-test-id='city'] input").sendKeys("Н");
        elements("div[class^='menu-item']").shouldBe(CollectionCondition.empty);
    }
}
