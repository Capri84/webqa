package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import test.SelectItems;
import test.TextInput;

import java.time.Duration;
import java.util.List;

public class GoogleSearchPage extends Page {

    private TextInput input = new TextInput();
    private SelectItems select = new SelectItems();
    @FindBy(name = "q")
    private WebElement searchLine;

    public GoogleSearchPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open(String url) {
        getPage(url);
        logger.info("Открыта страница: " + url);
    }

    public void performSearch(String searchText, String xpath, String itemText) {
        input.setInput(searchLine, searchText);
        logger.info("В строку поиска введен текст: " + searchText);
        findAndClickItem(xpath, itemText);
        logger.info("Из списка вариаций поисковой выдачи выбран: " + itemText);
    }

    private void findAndClickItem(String xpath, String itemText) {
        wait
                .ignoring(StaleElementReferenceException.class)
                .withMessage("Что-то пошло не так.")
                .pollingEvery(Duration.ofMillis(500))
                .until(driver -> {
                    By listItems = By.xpath(xpath);
                    List<WebElement> items = driver.findElements(listItems);
                    select.selectItem(items, itemText);
                    return driver.getTitle().equals("мобайл тинькофф тарифы - Поиск в Google");
                });
    }
}
