package test;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;

public class VacancyFormTests extends BaseRunner {

    @Test
    public void VacancyFormTestOne() {
        driver.get(baseUrl);
        driver.findElement(By.xpath("//input[@name='name']")).click();
        driver.findElement(By.xpath("//input[@name='birthday']")).click();
        driver.findElement(By.xpath("//input[@name='city']")).click();
        assertEquals("Поле обязательное",
                driver.findElement(By.xpath("//div[contains(@class, 'ui-form__row_date')]" +
                        "//div[contains(@class, 'ui-form-field-error-message')]")).getText());
        driver.findElement(By.xpath("//input[@name='email']")).click();
        driver.findElement(By.xpath("//input[@name='phone']")).click();
        driver.findElement(By.xpath("//span[@class='ui-upload__item-plus']")).click();
        driver.findElement(By.xpath("//input[@name='socialLink0']")).click();
        driver.findElement(By.xpath("//div[@class='ui-checkbox__check']")).click();
        driver.findElement(By.xpath("//input[@name='birthday']")).sendKeys(Keys.TAB);
        assertEquals("Поле обязательное",
                driver.findElement(By.xpath("//div[contains(@class, 'ui-form__row_checkbox')]" +
                        "//div[contains(@class, 'ui-form-field-error-message')]")).getText());
    }

    @Test
    public void VacancyFormTestTwo() {
        driver.get(baseUrl);
        // Тестирование поля "Фамилия и имя"
        testFIO();
        // Тестирование поля "Дата рождения"
        testDOB();
        // Тестирование поля "Электронная почта"
        testEMail();
        // Тестирование поля "Мобильный телефон"
        testMobPhone();
    }

    private void testFIO() {
        driver.findElement(By.xpath("//input[@name='name']")).click();
        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("Петров Федор.");
        driver.findElement(By.xpath("//input[@name='name']")).sendKeys(Keys.TAB);
        assertEquals("Допустимо использовать только буквы русского алфавита и дефис", driver.findElement
                (By.xpath("//div[contains(@class, 'ui-form__row ui-form__row_default-error-view-visible')]" +
                        "//div[contains(@class, 'ui-form-field-error-message')]")).getText());
        driver.findElement(By.xpath("//input[@name='name']")).click();
        clearInput(driver.findElement(By.xpath("//input[@name='name']")));
        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("Я");
        driver.findElement(By.xpath("//input[@name='name']")).sendKeys(Keys.TAB);
        assertEquals("Необходимо ввести фамилию и имя через пробел", driver.findElement
                (By.xpath("//div[contains(@class, 'ui-form__row ui-form__row_default-error-view-visible')]" +
                        "//div[contains(@class, 'ui-form-field-error-message')]")).getText());
        driver.findElement(By.xpath("//input[@name='name']")).click();
        clearInput(driver.findElement(By.xpath("//input[@name='name']")));
        driver.findElement(By.xpath("//input[@name='name']")).sendKeys("Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                "Lorem ipsum.");
        driver.findElement(By.xpath("//input[@name='birthday']")).sendKeys(Keys.TAB);
        assertEquals("Максимальное количество символов – 133", driver.findElement
                (By.xpath("//div[contains(@class, 'ui-form__row ui-form__row_default-error-view-visible')]" +
                        "//div[contains(@class, 'ui-form-field-error-message')]")).getText());
    }

    private void testDOB() {
        driver.findElement(By.xpath("//input[@name='birthday']")).click();
        driver.findElement(By.xpath("//input[@name='birthday']")).sendKeys("25.03.2020");
        driver.findElement(By.xpath("//input[@name='birthday']")).sendKeys(Keys.TAB);
        assertEquals("Мы не нанимаем пришельцев из будущего",
                driver.findElement(By.xpath("//div[contains(@class, 'ui-form__row_date')]" +
                        "//div[contains(@class, 'ui-form-field-error-message')]")).getText());
        driver.findElement(By.xpath("//input[@name='birthday']")).click();
        clearInput(driver.findElement(By.xpath("//input[@name='birthday']")));
        driver.findElement(By.xpath("//input[@name='birthday']")).sendKeys("00.00.0000");
        driver.findElement(By.xpath("//input[@name='birthday']")).sendKeys(Keys.TAB);
        assertEquals("Поле заполнено некорректно",
                driver.findElement(By.xpath("//div[contains(@class, 'ui-form__row_date')]" +
                        "//div[contains(@class, 'ui-form-field-error-message')]")).getText());
    }

    private void testEMail() {
        driver.findElement(By.xpath("//input[@name='email']")).click();
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("ivanov@domain@domain.com");
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys(Keys.TAB);
        assertEquals("Введите корректный адрес эл. почты",
                driver.findElement(By.xpath("//div[contains(@class, 'ui-form__row_email')]" +
                        "//div[contains(@class, 'ui-form-field-error-message')]")).getText());
    }

    private void testMobPhone() {
        driver.findElement(By.xpath("//input[@name='phone']")).click();
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("+(214) 578-95-54");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys(Keys.TAB);
        assertEquals("Код города/оператора должен начинаться с цифры 3, 4, 5, 6, 8, 9",
                driver.findElement(By.xpath("//div[contains(@class, 'ui-form__row_tel')]" +
                        "//div[contains(@class, 'ui-form-field-error-message')]")).getText());
        driver.findElement(By.xpath("//input[@name='phone']")).click();
        clearInput(driver.findElement(By.xpath("//input[@name='phone']")));
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys("+(978) 547");
        driver.findElement(By.xpath("//input[@name='phone']")).sendKeys(Keys.TAB);
        assertEquals("Номер телефона должен состоять из 10 цифр, начиная с кода оператора",
                driver.findElement(By.xpath("//div[contains(@class, 'ui-form__row_tel')]" +
                        "//div[contains(@class, 'ui-form-field-error-message')]")).getText());
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
    }
}

