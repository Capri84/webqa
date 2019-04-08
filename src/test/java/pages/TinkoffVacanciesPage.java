package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import test.TextInput;

public class TinkoffVacanciesPage extends Page {

    @FindBy(name = "name")
    public WebElement nameField;
    @FindBy(name = "birthday")
    public WebElement birthdayField;
    @FindBy(name = "city")
    public WebElement cityField;
    @FindBy(name = "email")
    public WebElement emailField;
    @FindBy(name = "phone")
    public WebElement phoneField;
    @FindBy(xpath = "//span[@class='ui-upload__item-plus']")
    public WebElement uploadField;
    @FindBy(name = "socialLink0")
    public WebElement socialField;
    @FindBy(xpath = "//div[@class='ui-checkbox__check']")
    public WebElement agreeCheckBox;
    private TextInput input = new TextInput();

    public TinkoffVacanciesPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open(String url) {
        getPage(url);
        logger.info("Открыта страница: " + url);
    }

    public void clickField(WebElement element) {
        element.click();
    }

    public void clickTabButton(WebElement element) {
        element.sendKeys(Keys.TAB);
        logger.info("Нажата клавиша TAB");
    }

    public void testFIO() {
        clickField(nameField);
        logger.info("Кликнуто поле \"Фамилия и имя\"");
        input.setInput(nameField, "Петров Федор.");
        logger.info("Поле заполнено");
        clickTabButton(nameField);
        checkField("Допустимо использовать только буквы русского алфавита и дефис", "//div" +
                "[contains(@class, 'ui-form__row ui-form__row_default-error-view-visible')]" +
                "//div[contains(@class, 'ui-form-field-error-message')]");
        clickField(nameField);
        logger.info("Кликнуто поле \"Фамилия и имя\"");
        clearInput(nameField);
        input.setInput(nameField, "Я");
        logger.info("Поле заполнено");
        clickTabButton(nameField);
        checkField("Необходимо ввести фамилию и имя через пробел", "//div" +
                "[contains(@class, 'ui-form__row ui-form__row_default-error-view-visible')]" +
                "//div[contains(@class, 'ui-form-field-error-message')]");
        clickField(nameField);
        logger.info("Кликнуто поле \"Фамилия и имя\"");
        clearInput(nameField);
        input.setInput(nameField, "Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                "Lorem ipsum.");
        logger.info("Поле заполнено");
        clickTabButton(birthdayField);
        checkField("Максимальное количество символов – 133", "//div" +
                "[contains(@class, 'ui-form__row ui-form__row_default-error-view-visible')]" +
                "//div[contains(@class, 'ui-form-field-error-message')]");
    }

    public void testDOB() {
        clickField(birthdayField);
        logger.info("Кликнуто поле \"Дата рождения\"");
        input.setInput(birthdayField, "25.03.2020");
        logger.info("Поле заполнено");
        clickTabButton(birthdayField);
        checkField("Мы не нанимаем пришельцев из будущего", "//div" +
                "[contains(@class, 'ui-form__row_date')]//div[contains(@class, 'ui-form-field-error-message')]");
        clickField(birthdayField);
        logger.info("Кликнуто поле \"Дата рождения\"");
        clearInput(birthdayField);
        input.setInput(birthdayField, "00.00.0000");
        logger.info("Поле заполнено");
        clickTabButton(birthdayField);
        checkField("Поле заполнено некорректно", "//div[contains(@class, 'ui-form__row_date')]" +
                "//div[contains(@class, 'ui-form-field-error-message')]");
    }

    public void testEMail() {
        clickField(emailField);
        logger.info("Кликнуто поле \"Электронная почта\"");
        input.setInput(emailField, "ivanov@domain@domain.com");
        logger.info("Поле заполнено");
        clickTabButton(emailField);
        checkField("Введите корректный адрес эл. почты", "//div" +
                "[contains(@class, 'ui-form__row_email')]//div[contains(@class, 'ui-form-field-error-message')]");
    }

    public void testMobPhone() {
        clickField(phoneField);
        logger.info("Кликнуто поле \"Мобильный телефон\"");
        input.setInput(phoneField, "+(214) 578-95-54");
        logger.info("Поле заполнено");
        clickTabButton(phoneField);
        checkField("Код города/оператора должен начинаться с цифры 3, 4, 5, 6, 8, 9", "//div" +
                "[contains(@class, 'ui-form__row_tel')]//div[contains(@class, 'ui-form-field-error-message')]");
        clickField(phoneField);
        logger.info("Кликнуто поле \"Мобильный телефон\"");
        clearInput(phoneField);
        input.setInput(phoneField, "+(978) 547");
        logger.info("Поле заполнено");
        clickTabButton(phoneField);
        checkField("Номер телефона должен состоять из 10 цифр, начиная с кода оператора", "//div" +
                "[contains(@class, 'ui-form__row_tel')]//div[contains(@class, 'ui-form-field-error-message')]");
    }

    /**
     * Метод для очистки полей формы от введенных ранее данных
     */
    private void clearInput(WebElement webElement) {
        if (webElement.equals(driver.findElement(By.xpath("//input[@name='phone']")))) {
            webElement.sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
        } else {
            while (!(webElement.getAttribute("value")).isEmpty()) {
                webElement.sendKeys(Keys.BACK_SPACE);
            }
        }
        logger.info("Поле очищено");
    }
}
