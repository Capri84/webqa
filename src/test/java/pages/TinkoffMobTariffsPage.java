package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class TinkoffMobTariffsPage extends Page {

    public TinkoffMobTariffsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open(String url) {
        getPage(url);
        logger.info("Открыта страница: " + url);
    }

    public void setMoscowRegion() {
        if (driver.findElement(By.xpath("//span[contains(@class, 'MvnoRegionConfirmation__regionName')]")).getText().
                equals("Москва и Московская область?")) {
            driver.findElement(By.xpath("//span[contains(@class, 'MvnoRegionConfirmation__option')]" +
                    "[contains(.,'Да')]")).click();
        } else {
            switchRegion("г Москва");
        }
        logger.info("Выбран регион \"Москва и Московская область\"");
    }

    public void checkButton(String xpath) {
        assertTrue(driver.findElement(By.xpath(xpath)).isEnabled());
        logger.info("Кнопка проверена на активность");
    }

    public void switchRegion(String region) {
        driver.findElement(By.xpath("//div[contains(@class, 'MvnoRegionConfirmation__title')]")).click();
        driver.findElement(By.xpath("//input[contains(@class, 'Input__value')]")).sendKeys(region);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait
                .ignoring(StaleElementReferenceException.class)
                .withMessage("что то пошло не так4")
                .pollingEvery(Duration.ofMillis(500))
                .until(driver -> {
                    By listItems = By.xpath("//div[contains(@class, 'Text')]");
                    java.util.List<WebElement> items = driver.findElements(listItems);
                    items.forEach(item -> {
                        if (item.getText().equalsIgnoreCase(region)) {
                            item.click();
                        }
                    });
                    return driver.getTitle();
                });
        logger.info("Выбран регион " + region);
    }

    // Максимальный набор услуг
    public void pickMaxCoverage() {
        // Интернет
        Actions actionsInternet = new Actions(driver);
        actionsInternet.moveToElement(driver.findElement(By.xpath("(//div[contains(@class,'additional')])[1]")))
                .click()
                .click(driver.findElement(By.xpath("//span[@class='ui-dropdown-field-list__item-text']" +
                        "[contains(.,'Безлимитный интернет')]")))
                .perform();
        logger.info("Выбрана опция \"Безлимитный интернет\"");

        // Звонки
        Actions actionsCalls = new Actions(driver);
        actionsCalls.moveToElement(driver.findElement(By.xpath("(//div[contains(@class,'additional')])[2]")))
                .click()
                .click(driver.findElement(By.xpath("//span[@class='ui-dropdown-field-list__item-text']" +
                        "[contains(.,'Безлимитные минуты')]")))
                .perform();
        logger.info("Выбрана опция \"Безлимитные минуты\"");

        // Режим модема и безлимитные смс
        By listCheckBoxes = By.xpath("//div[contains(@class, 'CheckboxSet')]//" +
                "div[contains(@class, 'Checkbox__inputOuter')]");
        java.util.List<WebElement> checkBoxes = driver.findElements(listCheckBoxes);
        checkBoxes.forEach(checkBox -> {
            if (!checkBox.isSelected()) {
                checkBox.click();
            }
        });
        logger.info("Выбраны опции \"Режим модема\" и \"Безлимитные смс\"");
        logger.info("Установлен максимальный пакет услуг");
    }

    public String getPriceText(String xpath) {
        logger.info("Получаем цену");
        return driver.findElement(By.xpath(xpath)).getText();
    }

    // Минимальный набор услуг
    public void uncheckAll() {
        // Интернет
        Actions actionsInternet = new Actions(driver);
        actionsInternet.moveToElement(driver.findElement(By.xpath("(//div[contains(@class,'additional')])[1]")))
                .click()
                .click(driver.findElement(By.xpath("//span[@class='ui-dropdown-field-list__item-text']" +
                        "[contains(.,'0 ГБ')]")))
                .perform();
        logger.info("Выбрана опция \"0 ГБ\"");

        // Звонки
        Actions actionsCalls = new Actions(driver);
        actionsCalls.moveToElement(driver.findElement(By.xpath("(//div[contains(@class,'additional')])[2]")))
                .click()
                .click(driver.findElement(By.xpath("//span[@class='ui-dropdown-field-list__item-text']" +
                        "[contains(.,'0 минут')]")))
                .perform();
        logger.info("Выбрана опция \"0 минут\"");

        // Отключение чекбоксов
        By listCheckBoxes = By.xpath("//div[contains(@class, 'CheckboxSet')]//div[contains(@class, 'checked')]");
        List<WebElement> checkBoxes = driver.findElements(listCheckBoxes);
        for (WebElement checkBox : checkBoxes) {
            checkBox.click();
        }
        logger.info("Отключены все чекбоксы");
        logger.info("Установлен минимальный пакет услуг");
    }
}
