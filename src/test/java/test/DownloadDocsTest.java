package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DownloadDocsTest extends BaseRunner {

    WebDriver driver;
    FirefoxProfile prof = new FirefoxProfile();
    FirefoxOptions options = new FirefoxOptions();

    @Before
    public void setUp() {
        driver = new FirefoxDriver(options);
        prof.setPreference("browser.download.dir", "E:\\Downloads");
        prof.setPreference("browser.download.folderList", 2);
        prof.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        options.setProfile(prof);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void downloadDocument() {
        driver.get("https://www.tinkoff.ru/mobile-operator/documents/");
        By listItems = By.xpath("//div[contains(@class, 'Document__root')]//a");
        List<WebElement> items = driver.findElements(listItems);
        items.get(generateRndNumber(items.size())).click();
    }

    private int generateRndNumber(int limit) {
        Random random = new Random();
        int rnd = random.nextInt(limit);
        System.out.println(rnd);
        return rnd;
    }
}
