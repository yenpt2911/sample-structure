package pageObjects;

import commons.BasePage;
import org.openqa.selenium.WebDriver;
import pageUIs.RegisterPageUI;

public class RegisterPageObject extends BasePage {

    private WebDriver driver;

    public RegisterPageObject(WebDriver driver) {
        this.driver = driver;
    }

    public void clickToButtonByText() {
        waitForElementClickable(driver, RegisterPageUI.BUTTON_BY_TEXT);
        clickToElement(driver, RegisterPageUI.BUTTON_BY_TEXT);
    }
}
