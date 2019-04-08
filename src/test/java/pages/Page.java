package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Page {

    Logger logger = LoggerFactory.getLogger(Page.class);

    WebDriver driver;
    WebDriverWait wait;

    Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }

    public void switchToWindow(String windowName) {
        wait.until(d -> {
            boolean check = false;
            for (String title : driver.getWindowHandles()) {
                driver.switchTo().window(title);
                check = d.getTitle().equals(windowName);
            }
            return check;
        });
        logger.info("Переключились на вкладку с именем " + windowName);
    }

    void getPage(String url) {
        driver.navigate().to(url);
        logger.info("Получена страница с адресом: " + url);
    }

    public void refreshPage() {
        driver.navigate().refresh();
        logger.info("Обновлена текущая страница");
    }

    // Универсальный xpath локатор, вернет все элементы, содержащие текст
    List<WebElement> xpathSearcherByText(String searchText) {
        String xpath = String.format("//*[contains(text(),'%s')]", searchText);
        return driver.findElements(By.xpath(xpath));
    }

    public void closeCurrentTab() {
        driver.close();
        logger.info("Закрыта активная вкладка");
    }

    public void switchToMainTab() {
        driver.switchTo().window(driver.getWindowHandles().iterator().next());
        logger.info("Переключились на главную вкладку");
    }

    public void checkEquals(String firstString, String secondString) {
        assertEquals(firstString, secondString);
        logger.info("Значения " + firstString + " и " + secondString + " проверены на совпадение");
    }

    public void checkNotEquals(String firstString, String secondString) {
        assertNotEquals(firstString, secondString);
        logger.info("Значения " + firstString + " и " + secondString + " проверены на несовпадение");
    }

    public void checkField(String expectedValue, String xpath) {
        assertEquals(expectedValue, driver.findElement(By.xpath(xpath)).getText());
        logger.info("Значения " + expectedValue + " и " + driver.findElement(By.xpath(xpath)).getText() + " проверены " +
                "на совпадение");
    }
}
