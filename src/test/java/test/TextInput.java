package test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

class TextInput extends BaseRunner {

    void setInput(WebElement element, String input) {
        element.sendKeys(input);
    }

    void pressTab(WebElement element) {
        element.sendKeys(Keys.TAB);
    }

    String getText(WebElement element) {
        return element.getText();
    }

    void clickElement(WebElement element) {
        element.click();
    }

    private void clearInput(WebElement element) {
        if (element.equals(driver.findElement(By.xpath("//input[@name='phone']")))) {
            element.sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
        } else {
            while (!(element.getAttribute("value")).isEmpty()) {
                element.sendKeys(Keys.BACK_SPACE);
            }
        }
    }
}
