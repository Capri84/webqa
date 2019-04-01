package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SelectItems extends BaseRunner {

    public void selectDropdownItem(WebElement dropDown, String item) {
        Select select = new Select(dropDown);
        waitForItems(dropDown);
        select.selectByVisibleText(item);
    }

    private void waitForItems(WebElement dropDown) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dropDown.toString())));
    }

    String getCurrentValue(String elementPath) {
        return driver.findElement(By.xpath(elementPath)).getText();
    }
}
