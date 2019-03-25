package test;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;


public class VacancyFormTests extends BaseRunner {

    @Test
    public void testVacancyFormTestOne() {
        driver.get(baseUrl);
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("birthday")).click();
        driver.findElement(By.name("city")).click();
        assertEquals("Поле обязательное", driver.findElement(By.xpath("(.//*[normalize-space(text()) and " +
                "normalize-space(.)='Дата рождения'])[1]/following::div[3]")).getText());
        driver.findElement(By.name("email")).click();
        driver.findElement(By.name("phone")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and " +
                "normalize-space(.)='Перетащите файлы сюда'])[1]/following::span[1]")).click();
        driver.findElement(By.name("socialLink0")).click();
        driver.findElement(By.cssSelector("svg.ui-icon-checkbox.ui-checkbox__icon")).click();
        driver.findElement(By.name("birthday")).sendKeys(Keys.TAB);
        assertEquals("Поле обязательное", driver.findElement(By.xpath("(.//*[normalize-space(text()) and " +
                "normalize-space(.)='условиями передачи информации'])[1]/following::div[1]")).getText());
    }

    @Test
    public void testVacancyFormTestTwo() {
        driver.get(baseUrl);
        // Тестирование поля "Фамилия и имя"
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("Петров Федор.");
        driver.findElement(By.name("birthday")).sendKeys(Keys.TAB);
        assertEquals("Допустимо использовать только буквы русского алфавита и дефис",
                driver.findElement(By.xpath("(.//*[normalize-space(text()) and " +
                        "normalize-space(.)='Фамилия и имя'])[1]/following::div[2]")).getText());
        driver.findElement(By.name("name")).click();
        clearInput(driver.findElement(By.name("name")));
        driver.findElement(By.name("name")).sendKeys("Я");
        driver.findElement(By.name("birthday")).sendKeys(Keys.TAB);
        assertEquals("Необходимо ввести фамилию и имя через пробел",
                driver.findElement(By.xpath("(.//*[normalize-space(text()) and " +
                        "normalize-space(.)='Фамилия и имя'])[1]/following::div[2]")).getText());
        driver.findElement(By.name("name")).click();
        clearInput(driver.findElement(By.name("name")));
        driver.findElement(By.name("name")).sendKeys("Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore " +
                "et dolore magna aliqua.Lorem ipsum");
        driver.findElement(By.name("birthday")).sendKeys(Keys.TAB);
        assertEquals("Максимальное количество символов – 133",
                driver.findElement(By.xpath("(//div[@data-qa-file='UIFormRowError'])[1]")).getText());

        // Тестирование поля "Дата рождения"
        driver.findElement(By.name("birthday")).click();
        clearInput(driver.findElement(By.name("birthday")));
        driver.findElement(By.name("birthday")).sendKeys("25.03.2020");
        driver.findElement(By.name("city")).sendKeys(Keys.TAB);
        assertEquals("Мы не нанимаем пришельцев из будущего",
                driver.findElement(By.xpath("(.//*[normalize-space(text()) and " +
                        "normalize-space(.)='Дата рождения'])[1]/following::div[3]")).getText());
        driver.findElement(By.name("birthday")).click();
        clearInput(driver.findElement(By.name("birthday")));
        driver.findElement(By.name("birthday")).sendKeys("00.00.0000");
        driver.findElement(By.name("city")).sendKeys(Keys.TAB);
        assertEquals("Поле заполнено некорректно",
                driver.findElement(By.xpath("(.//*[normalize-space(text()) and " +
                        "normalize-space(.)='Дата рождения'])[1]/following::div[3]")).getText());

        // Тестирование поля "Электронная почта"
        driver.findElement(By.name("email")).click();
        clearInput(driver.findElement(By.name("email")));
        driver.findElement(By.name("email")).sendKeys("ivanov@domain@domain.com");
        driver.findElement(By.name("phone")).sendKeys(Keys.TAB);
        assertEquals("Введите корректный адрес эл. почты",
                driver.findElement(By.xpath("(.//*[normalize-space(text()) and " +
                        "normalize-space(.)='Электронная почта'])[1]/following::div[2]")).getText());

        // Тестирование поля "Мобильный телефон"
        driver.findElement(By.name("phone")).click();
        driver.findElement(By.name("phone")).clear();
        driver.findElement(By.name("phone")).sendKeys("+(214) 578-95-54");
        driver.findElement(By.name("city")).sendKeys(Keys.TAB);
        assertEquals("Код города/оператора должен начинаться с цифры 3, 4, 5, 6, 8, 9",
                driver.findElement(By.xpath("(.//*[normalize-space(text()) and " +
                        "normalize-space(.)='Мобильный телефон'])[1]/following::div[2]")).getText());
        driver.findElement(By.name("phone")).click();
        clearInput(driver.findElement(By.name("phone")));
        driver.findElement(By.name("phone")).sendKeys("+(978) 547");
        driver.findElement(By.name("city")).sendKeys(Keys.TAB);
        assertEquals("Номер телефона должен состоять из 10 цифр, начиная с кода оператора",
                driver.findElement(By.xpath("(.//*[normalize-space(text()) and " +
                        "normalize-space(.)='Мобильный телефон'])[1]/following::div[2]")).getText());
    }

    /**
     * Метод для очистки полей формы от введенных ранее данных
     */
    private void clearInput(WebElement webElement) {
        if (webElement.equals(driver.findElement(By.name("phone")))) {
            webElement.sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
        } else {
            while (!(webElement.getAttribute("value")).isEmpty()) {
                // "\u0008" - is backspace char
                webElement.sendKeys("\u0008");
            }
        }
    }
}

