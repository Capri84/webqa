package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class GoogleResultsPage extends Page {

    public GoogleResultsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void clickSearchResultsByLink(String link) {
        wait.until(d -> xpathSearcherByText(link).size() > 0);
        xpathSearcherByText(link).get(0).click();
        logger.info("Найдена и нажата ссылка " + link);
    }

    public String getPageTitle() {
        logger.info("Получаем название страницы");
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        logger.info("Получаем адрес текущей страницы");
        return driver.getCurrentUrl();
    }
}
