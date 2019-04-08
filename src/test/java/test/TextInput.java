package test;

import org.openqa.selenium.WebElement;

public class TextInput extends BaseRunner {

    public void setInput(WebElement element, String input) {
        element.sendKeys(input);
    }

    String getText(WebElement element) {
        return element.getAttribute("value");
    }
}
