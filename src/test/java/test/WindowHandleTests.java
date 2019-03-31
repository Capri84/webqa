package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class WindowHandleTests extends BaseRunner {

    private WebDriver driver;
    private String baseUrl;
    private String searchWindowId;
    private String tariffsWindowId;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        baseUrl = "https://www.google.ru/";
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void switchWindows() {
        driver.get("https://www.google.ru/");
        driver.findElement(By.xpath("//input[@name='q']")).sendKeys("мобайл тинькофф");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait
                .ignoring(StaleElementReferenceException.class)
                .withMessage("что то пошло не так")
                .pollingEvery(Duration.ofMillis(500))
                .until(driver -> {
                    By listItems = By.xpath("//ul[contains(@role, 'listbox')]/*");
                    List<WebElement> items = driver.findElements(listItems);
                    items.forEach(item -> {
                        if (item.getText().contains("мобайл тинькофф тарифы")) {
                            item.click();
                        }
                    });
                    searchWindowId = driver.getWindowHandle();
                    return driver.getTitle().equals("мобайл тинькофф тарифы - Поиск в Google");
                });

        wait
                .ignoring(StaleElementReferenceException.class)
                .withMessage("что то пошло не так2")
                .pollingEvery(Duration.ofMillis(500))
                .until(driver -> {
                    By listItems = By.xpath("//div[@id='rso']//div[@class='r']//cite");
                    List<WebElement> items = driver.findElements(listItems);
                    items.forEach(item -> {
                        if (item.getText().contains("https://www.tinkoff.ru/mobile-operator/tariffs/")) {
                            item.click();
                        }
                    });
                    Set<String> ids = driver.getWindowHandles();
                    ids.forEach(id -> {
                        if (!id.equals(driver.getWindowHandle())) {
                            driver.switchTo().window(id);
                        }
                    });
                    tariffsWindowId = driver.getWindowHandle();
                    assertEquals("Тарифы Тинькофф Мобайла", driver.getTitle());
                    return driver.getTitle().equals("Тарифы Тинькофф Мобайла");
                });
        driver.switchTo().window(searchWindowId).close();
        driver.switchTo().window(tariffsWindowId);
        assertEquals("https://www.tinkoff.ru/mobile-operator/tariffs/",
                driver.getCurrentUrl());
    }

    @Test
    public void changeRegions() {
        driver.get("https://www.tinkoff.ru/mobile-operator/tariffs/");
        if (driver.findElement(By.xpath("//span[contains(@class, 'MvnoRegionConfirmation__regionName')]")).getText().equals("Москва и Московская область?")) {
            driver.findElement(By.xpath("//span[contains(@class, 'MvnoRegionConfirmation__option')][contains(.,'Да')]")).click();
            assertEquals("Москва и Московская область", driver.findElement
                    (By.xpath("//div[contains(@class, 'MvnoRegionConfirmation__title')]")).getText());
        } else {
            String region = "г Москва";
            switchRegion(region);
            assertEquals("Москва и Московская область", driver.findElement
                    (By.xpath("//div[contains(@class, 'MvnoRegionConfirmation__title')]")).getText());
        }
        driver.navigate().refresh();
        assertEquals("Москва и Московская область", driver.findElement
                (By.xpath("//div[contains(@class, 'MvnoRegionConfirmation__title')]")).getText());
        String moscowDefault = driver.findElement(By.xpath("//div[contains(@class, 'ui-form__field_title')]")).getText();
        pickMaxCoverage();
        String moscowMax = driver.findElement(By.xpath("//div[contains(@class, 'ui-form__field_title')]")).getText();

        // Переключение на Краснодар
        String region = "г Краснодар";
        switchRegion(region);
        assertEquals("Краснодарский край", driver.findElement
                (By.xpath("//div[contains(@class, 'MvnoRegionConfirmation__title')]")).getText());
        String krasnodarDefault = driver.findElement(By.xpath("//div[contains(@class, 'ui-form__field_title')]")).getText();
        System.out.println(moscowDefault + " " + krasnodarDefault);
        assertNotEquals(moscowDefault, krasnodarDefault);
        pickMaxCoverage();
        String krasnodarMax = driver.findElement(By.xpath("//div[contains(@class, 'ui-form__field_title')]")).getText();
        System.out.println(moscowMax + " " + krasnodarMax);
        assertEquals(moscowMax, krasnodarMax);
    }

    @Test
    public void checkActiveButton() {
        driver.get("https://www.tinkoff.ru/mobile-operator/tariffs/");
        uncheckAll();
        assertEquals("Общая цена: 0 \u20BD", driver.findElement(By.xpath("//div[contains(@class, 'ui-form__field_title')]")).getText());
        assertTrue(driver.findElement(By.xpath("//div[contains(@class, 'LoaderRound__container')]")).isEnabled());
    }

    private void switchRegion(String region) {
        driver.findElement(By.xpath("//div[contains(@class, 'MvnoRegionConfirmation__title')]")).click();
        driver.findElement(By.xpath("//input[contains(@class, 'Input__value')]")).sendKeys(region);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait
                .ignoring(StaleElementReferenceException.class)
                .withMessage("что то пошло не так4")
                .pollingEvery(Duration.ofMillis(500))
                .until(driver -> {
                    By listItems = By.xpath("//div[contains(@class, 'Text')]");
                    List<WebElement> items = driver.findElements(listItems);
                    items.forEach(item -> {
                        if (item.getText().equalsIgnoreCase(region)) {
                            item.click();
                        }
                    });
                    return driver.getTitle();
                });
    }

    private void pickMaxCoverage() {
        // Интернет
        Actions actionsInternet = new Actions(driver);
        actionsInternet.moveToElement(driver.findElement(By.xpath("(//div[contains(@class,'additional')])[1]")))
                .click()
                .click(driver.findElement(By.xpath("//span[@class='ui-dropdown-field-list__item-text'][contains(.,'Безлимитный интернет')]")))
                .perform();

        // Звонки
        Actions actionsCalls = new Actions(driver);
        actionsCalls.moveToElement(driver.findElement(By.xpath("(//div[contains(@class,'additional')])[2]")))
                .click()
                .click(driver.findElement(By.xpath("//span[@class='ui-dropdown-field-list__item-text'][contains(.,'Безлимитные минуты')]")))
                .perform();

        // Режим модема и безлимитные смс
        By listCheckBoxes = By.xpath("//div[contains(@class, 'CheckboxSet')]//div[contains(@class, 'Checkbox__inputOuter')]");
        List<WebElement> checkBoxes = driver.findElements(listCheckBoxes);
        checkBoxes.forEach(checkBox -> {
            if (!checkBox.isSelected()) {
                checkBox.click();
            }
        });
    }

    private void uncheckAll() {
        // Интернет
        Actions actionsInternet = new Actions(driver);
        actionsInternet.moveToElement(driver.findElement(By.xpath("(//div[contains(@class,'additional')])[1]")))
                .click()
                .click(driver.findElement(By.xpath("//span[@class='ui-dropdown-field-list__item-text'][contains(.,'0 ГБ')]")))
                .perform();

        // Звонки
        Actions actionsCalls = new Actions(driver);
        actionsCalls.moveToElement(driver.findElement(By.xpath("(//div[contains(@class,'additional')])[2]")))
                .click()
                .click(driver.findElement(By.xpath("//span[@class='ui-dropdown-field-list__item-text'][contains(.,'0 минут')]")))
                .perform();

        // Отключение чекбоксов
        By listCheckBoxes = By.xpath("//div[contains(@class, 'CheckboxSet')]//div[contains(@class, 'checked')]");
        List<WebElement> checkBoxes = driver.findElements(listCheckBoxes);
        for (WebElement checkBox : checkBoxes) {
            checkBox.click();
        }
    }
}