package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SelectItems extends BaseRunner {

    public void selectItem(List<WebElement> items, String text) {
        items.forEach(item -> {
            if (item.getText().contains(text)) {
                item.click();
            }
        });
    }

    String getCurrentValue(String elementPath) {
        return app.getDriver().findElement(By.xpath(elementPath)).getText();
    }
}
