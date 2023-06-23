package commons;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BasePage {

    public static BasePage getBasePage() {
        return new BasePage();
    }

    protected void openPageUrl(WebDriver driver, String pageUrl) {
        driver.get(pageUrl);
    }

    protected String getPageTitle(WebDriver driver) {
        return driver.getTitle();
    }

    protected String getCurrentUrl(WebDriver driver) {
       return driver.getCurrentUrl();
    }

    protected String getPageSource(WebDriver driver) {
        return driver.getPageSource();
    }

    protected void backToPage(WebDriver driver) {
        driver.navigate().back();
    }

    protected void forwardToPage(WebDriver driver) {
        driver.navigate().back();
    }

    protected void refreshToPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    protected Alert waitForAlertPresence(WebDriver driver) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        return explicitWait.until(ExpectedConditions.alertIsPresent());
    }

    protected void acceptAlert(WebDriver driver) {
        waitForAlertPresence(driver).accept();
    }

    protected void cancelAlert(WebDriver driver) {
        waitForAlertPresence(driver).dismiss();
    }

    protected void sendKeyToAlert(WebDriver driver, String textValue) {
        waitForAlertPresence(driver).sendKeys(textValue);
    }

    protected String getTextAlert(WebDriver driver) {
        return waitForAlertPresence(driver).getText();
    }

    protected String getWindowHandle(WebDriver driver) {
        return driver.getWindowHandle();
    }
    protected void switchToWindowById(WebDriver driver, String parentID) {
        Set<String> allWindows = driver.getWindowHandles();

        for (String id: allWindows) {
            if(!id.equals(parentID)){
                driver.switchTo().window(id);
                break;
            }
        }
    }

    protected void switchWindowByTitle(WebDriver driver, String expectedTitle) {
        Set<String> allWindows = driver.getWindowHandles();

        for (String id: allWindows) {
            driver.switchTo().window(id);
            String windowTitle = getPageTitle(driver);
            if (windowTitle.equals(expectedTitle)){
                break;
            }
        }
    }

    protected void closeAllWindowsWithoutParent( WebDriver driver, String parentID) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String id: allWindows) {
            if (!id.equals(parentID)){
                driver.switchTo().window(id);
                driver.close();
            }
        }
        driver.switchTo().window(parentID);
    }

    protected By getByLocator(String locatorType) {
        By by = null;
        if (locatorType.startsWith("id=") || locatorType.startsWith("Id=") || locatorType.startsWith("ID=")) {
            by = By.id(locatorType.substring(3));
        } else if (locatorType.startsWith("class=") || locatorType.startsWith("Class=") || locatorType.startsWith("CLASS=")) {
            by = By.className(locatorType.substring(6));
        } else if (locatorType.startsWith("name=") || locatorType.startsWith("Name=") || locatorType.startsWith("NAME=")) {
            by = By.name(locatorType.substring(5));
        } else if (locatorType.startsWith("css=") || locatorType.startsWith("Css=") || locatorType.startsWith("CSS=")) {
            by = By.cssSelector(locatorType.substring(4));
        } else if (locatorType.startsWith("xpath=") || locatorType.startsWith("Xpath=") || locatorType.startsWith("XPATH=")) {
            by = By.xpath(locatorType.substring(6));
        } else {
            throw new RuntimeException("Locator type is not supported");
        }
        return by;
    }

    protected String getDynamicLocator (String locator, String... params) {
        return String.format(locator,(Object[]) params);
    }

    protected WebElement getWebElement(WebDriver driver, String locatorType) {
        return driver.findElement(getByLocator(locatorType));
    }

    protected WebElement getWebElement(WebDriver driver, String locatorType, String... params) {
        return driver.findElement(getByLocator(getDynamicLocator(locatorType, params)));
    }

    protected List<WebElement> getListWebElement(WebDriver driver, String locatorType) {
        return driver.findElements(getByLocator(locatorType));
    }

    protected  List<WebElement> getListWebElement(WebDriver driver, String locatorType, String... params) {
        return driver.findElements(getByLocator(getDynamicLocator(locatorType, params)));
    }

    protected void clickToElement(WebDriver driver, String locatorType) {
        getWebElement(driver, locatorType).click();
    }

    protected void clickToElement(WebDriver driver, String locatorType, String... params) {
        getWebElement(driver, locatorType, params).click();
    }

    protected void sendKeyToElement(WebDriver driver, String locatorType, String textValue) {
        WebElement element = getWebElement(driver, locatorType);
        element.clear();
        element.sendKeys(textValue);
    }

    protected void sendKeyToElement(WebDriver driver, String locatorType, String textValue, String... params) {
        WebElement element = getWebElement(driver, locatorType, params);
        element.clear();
        element.sendKeys(textValue);
    }

    protected void selectItemInDefaultDropdownByValue(WebDriver driver, String locatorType, String itemValue) {
        select = new Select(getWebElement(driver, locatorType));
        select.selectByValue(itemValue);
    }

    protected String getSelectedItemDefaultDropdown(WebDriver driver, String locatorType) {
        select = new Select(getWebElement(driver, locatorType));
        return select.getFirstSelectedOption().getText();
    }

    protected void selectItemInDefaultDropdownByText(WebDriver driver, String locatorType, String textValue) {
        select = new Select(getWebElement(driver, locatorType));
        select.selectByVisibleText(textValue);
    }

    protected void selectItemInDefaultDropdownByText(WebDriver driver, String locatorType, String textValue, String... params) {
        select = new Select(getWebElement(driver, locatorType, params));
        select.selectByVisibleText(textValue);
    }

    protected boolean isDropdownMultiple(WebDriver driver, String locatorType) {
        select = new Select(getWebElement(driver, locatorType));
        return select.isMultiple();
    }

    protected int getAllDataInDropdown (WebDriver driver, String locatorType) {
        select = new Select(getWebElement(driver, locatorType));
        return select.getOptions().size();
    }

    protected void selectItemInCustomDropdown (WebDriver driver, String parentLocator, String childLocator, String expectedTextItem) {
        getWebElement(driver, parentLocator).click();
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);

        List<WebElement> allItems = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByLocator(childLocator)));

        for (WebElement item : allItems) {
            if (item.getText().trim().equals(expectedTextItem)){
                if (item.isDisplayed()){
                    item.click();
                } else {
                    jsExecutor = (JavascriptExecutor) driver;
                    jsExecutor.executeScript("arguments[0].scrollIntoView(true);",item);
                    item.click();
                }
                break;
            }
        }
    }

    protected String getElementAttribute(WebDriver driver, String locatorType, String attributeName){
        return getWebElement(driver, locatorType).getAttribute(attributeName);
    }

    protected String getElementText(WebDriver driver, String locatorType) {
        return getWebElement(driver, locatorType).getText();
    }

    protected int getElementsSize(WebDriver driver, String locatorType) {
        return getListWebElement(driver, locatorType).size();
    }

    protected void checkTheCheckboxOrRadio(WebDriver driver, String locatorType) {
        WebElement element = getWebElement(driver, locatorType);
        if (!element.isSelected()){
            element.click();
        }
    }

    protected void checkTheCheckboxOrRadio(WebDriver driver, String locatorType, String... params) {
        WebElement element = getWebElement(driver, locatorType, params);
        if (!element.isSelected()){
            element.click();
        }
    }

    protected void uncheckDefaultCheckbox(WebDriver driver, String locatorType) {
        WebElement element = getWebElement(driver, locatorType);
            if (element.isSelected()){
                element.click();
            }

    }

    protected boolean isElementDisplayed(WebDriver driver, String locatorType) {
        try {
            return getWebElement(driver, locatorType).isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected boolean isElementUndisplayed(WebDriver driver, String locatorType) {
        overrideGlobalTimeout(driver, shortTimeout);
        List<WebElement> elements = getListWebElement(driver, locatorType);
        overrideGlobalTimeout(driver, longTimeout);
        if(elements.size() == 0) {
            return true;
        } else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
            return true;
        } else {
            return false;
        }

    }

    protected void overrideGlobalTimeout(WebDriver driver, long longTimeout) {
        driver.manage().timeouts().implicitlyWait(longTimeout, TimeUnit.SECONDS);
    }

    protected void switchToFrameIframe(WebDriver driver, String locatorType) {
        driver.switchTo().frame(getWebElement(driver, locatorType));
    }

    protected void switchToDefaultContent(WebDriver driver) {
        driver.switchTo().defaultContent();
    }

    protected void doubleClickToElement(WebDriver driver, String locatorType) {
        action = new Actions(driver);
        action.doubleClick(getWebElement(driver, locatorType)).perform();
    }

    protected void hoverMouseToElement(WebDriver driver, String locatorType) {
        action = new Actions(driver);
        action.moveToElement(getWebElement(driver, locatorType)).perform();
    }

    protected void rightClickToElement(WebDriver driver, String locatorType) {
        action = new Actions(driver);
        action.contextClick(getWebElement(driver, locatorType)).perform();
    }

    protected void scrollToBottomPage(WebDriver driver) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollBy(0, document.body.scrollHeight)");
    }

    protected void highlightElement(WebDriver driver, String locatorType) {
        jsExecutor = (JavascriptExecutor) driver;
        WebElement element = getWebElement(driver, locatorType);
        String originalStyle = element.getAttribute("style");
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
    }

    protected void clickToElementByJS(WebDriver driver, String locatorType) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", getWebElement(driver, locatorType));
    }

    protected void scrollToElement(WebDriver driver, String locatorType) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getWebElement(driver, locatorType));
    }

    protected void waitForElementVisible(WebDriver driver, String locatorType) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(locatorType)));
    }

    protected  void waitForElementVisible(WebDriver driver, String locatorType, String... params){
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(getDynamicLocator(locatorType, params))));
    }

    protected void waitForAllElementVisible(WebDriver driver, String locatorType) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByLocator(locatorType)));
    }

    protected void waitForAllElementVisible(WebDriver driver, String locatorType, String... params) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByLocator(getDynamicLocator(locatorType,params))));
    }

    protected void waitForElementClickable(WebDriver driver, String locatorType) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByLocator(locatorType)));
    }

    protected void waitForElementClickable(WebDriver driver, String locatorType, String... params){
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.elementToBeClickable(getWebElement(driver, locatorType, params)));
    }

    protected void waitForElementInvisible(WebDriver driver, String locatorType) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(locatorType)));
    }

    protected void waitForElementInvisible(WebDriver driver, String locatorType, String... params) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(getDynamicLocator(locatorType, params))));
    }

    public void selectRadioOptionByID(WebDriver driver, String idValue) {
        waitForElementVisible(driver, BasePageUI.RADIO_BUTTON_BY_ID, idValue);
        checkTheCheckboxOrRadio(driver, BasePageUI.RADIO_BUTTON_BY_ID, idValue );
    }

    public void enterToTextBoxByID(WebDriver driver, String value, String idValue) {
        waitForElementVisible(driver, BasePageUI.TEXT_BOX_BY_ID, idValue);
        sendKeyToElement(driver, BasePageUI.TEXT_BOX_BY_ID, value, idValue);
    }

    public void selectDefaultDropdownByName(WebDriver driver, String valueDropdown, String nameDropdown) {
        waitForElementClickable(driver, BasePageUI.DROPDOWN_BY_NAME, nameDropdown);
        selectItemInDefaultDropdownByText(driver, BasePageUI.DROPDOWN_BY_NAME, valueDropdown,nameDropdown);
    }









    
    private Select select;
    private Alert alert;
    private Actions action;

    private WebDriverWait explicitWait;

    private JavascriptExecutor jsExecutor;


    private long longTimeout = 30;
    private long shortTimeout = 15;



}
