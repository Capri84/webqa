package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

class CheckBox extends BaseRunner {

    private WebElement checkBox;

    private void changeState() {
        checkBox.click();
    }

    String getText() {
        return checkBox.getText();
    }

    boolean getCurrentState(String elementPath) {
        return checkBox.isSelected();
    }

    List<WebElement> findAllChecked(By by) {
        return app.getDriver().findElements(by);
    }

    void uncheckAllState(List<WebElement> checkBoxes) {
        for (int i = 0; i < checkBoxes.size(); i++) {
            changeState();
        }
    }
}
