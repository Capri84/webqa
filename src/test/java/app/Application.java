package app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.GoogleResultsPage;
import pages.GoogleSearchPage;
import pages.TinkoffMobTariffsPage;
import pages.TinkoffVacanciesPage;
import test.BrowsersFactory;

import java.util.concurrent.TimeUnit;

public class Application {

    private static final String browserName = System.getProperty("browser") == null ? "chrome" : System.getProperty("browser");
    public WebDriverWait wait;
    public GoogleSearchPage google;
    public GoogleResultsPage googleResults;
    public TinkoffMobTariffsPage tinkoffMobTariffs;
    public TinkoffVacanciesPage tinkoffVacancies;
    private WebDriver driver;

    public Application() {
        driver = new EventFiringWebDriver(getDriver());
        ((EventFiringWebDriver) driver).register(new BrowsersFactory.MyListener());
        wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //page
        google = new GoogleSearchPage(driver);
        googleResults = new GoogleResultsPage(driver);
        tinkoffVacancies = new TinkoffVacanciesPage(driver);
        tinkoffMobTariffs = new TinkoffMobTariffsPage(driver);
    }

    public void quit() {
        driver.quit();
        driver = null;
    }

    public WebDriver getDriver() {
        return BrowsersFactory.buildDriver(browserName);
    }
}


