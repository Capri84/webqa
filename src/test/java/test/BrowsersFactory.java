package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

public class BrowsersFactory {

    public static WebDriver buildDriver(String browserName) {
        switch (browserName) {

            case "chrome_invisible":
                ChromeOptions chromeInvisibleOpt = new ChromeOptions();
                chromeInvisibleOpt.addArguments("--disable-notifications");
                chromeInvisibleOpt.addArguments("--headless");
                return new ChromeDriver(chromeInvisibleOpt);

            case "firefox":
                //Disable login to console and redirect log to an external file
                System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
                System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "./src/test/java/resources.firefox_logs/log");

                FirefoxOptions ffOpt = new FirefoxOptions();
                ffOpt.addPreference("dom.webnotifications.enabled", false);
                return new FirefoxDriver(ffOpt);

            default:
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-notifications");
                return new ChromeDriver(options);
        }
    }

    public static class MyListener extends AbstractWebDriverEventListener {

        @Override
        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        }

        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver) {
        }
    }
}
