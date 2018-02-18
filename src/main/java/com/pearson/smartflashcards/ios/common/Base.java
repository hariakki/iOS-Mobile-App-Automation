package com.pearson.smartflashcards.ios.common;

import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.pearson.smartflashcards.ios.util.DriverSetup;
import com.pearson.smartflashcards.ios.util.FileReadWrite;

import java.io.IOException;
import java.util.*;
import java.util.List;

import static com.pearson.smartflashcards.ios.util.DriverSetup.iosDriver;


/**
 * Created by Osanda on 5/14/2017.
 */


public class Base {

    public String PLATFORM = System.getenv("testing_platform");
    public final String ANDROID = "android";
    public final String IOS = "ios";
    public final String WEBVIEW = System.getenv("webview");
    public final String NATIVE_APP = System.getenv("native_app");
    private Dimension size;
    List<WebElement> elements;
    List <String> elementNameList = new ArrayList();

    public IOSDriver iosDriver(){
        return iosDriver;
    }

    public void printText(String text){
        System.out.println(text);
        Gauge.writeMessage(text);
    }

    public void waitForElementClickable(WebElement element) throws IOException {
        WebDriverWait wait = new WebDriverWait(iosDriver, 15);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementVisible(WebElement element) throws IOException {
        WebDriverWait wait = new WebDriverWait(iosDriver, 15);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void tap(WebElement element) throws IOException {
        waitForElementVisible(element);
        element.click();
    }

    public void setTextAs(WebElement element, String text) throws IOException {
        waitForElementClickable(element);
        element.sendKeys(text);
    }

    public void clearText(WebElement element) throws IOException {
        waitForElementClickable(element);
        element.clear();
    }

    public WebElement getElementByReplacingXpath(WebElement element, String textToBeReplaced, String replacementText) throws IOException {
        String modifiedXpath = element.getAttribute("xpath").replace(textToBeReplaced, replacementText);
        WebDriverWait wait = new WebDriverWait(iosDriver, 15);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(modifiedXpath)));
        return iosDriver.findElement(By.xpath(modifiedXpath));

    }

    public void replaceXpathAndTapElement(WebElement element, String textToBeReplaced, String replacementText) throws IOException {
        String modifiedXpath = element.getAttribute("xpath").replace(textToBeReplaced, replacementText);
        WebDriverWait wait = new WebDriverWait(iosDriver, 15);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(modifiedXpath)));
        iosDriver.findElement(By.xpath(modifiedXpath)).click();
    }

    public void hideKeyboard(){
        try {
            iosDriver.hideKeyboard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void isFailed(WebElement element, String expectedPageTitle) throws IOException {
        waitForElementVisible(element);
        Assert.assertTrue(element.isDisplayed(), "Page title locator is invalid.");
        Assert.assertTrue(element.getAttribute("name").equals(expectedPageTitle), "Expected result is not obtained");
    }

    public void isSuccess(WebElement element, String expectedPageTitle) throws IOException {
        waitForElementVisible(element);
        Assert.assertTrue(element.isDisplayed(), "Page title locator is invalid.");
        Assert.assertTrue(element.getAttribute("name").equals(expectedPageTitle), "Expected result is not obtained");
    }

    public void isElementAttributeValueEquals(WebElement element, String attributeName, String attributeValue) throws IOException {
        waitForElementVisible(element);
        Assert.assertTrue(element.isDisplayed(), "Element cannot be found.");
        Assert.assertEquals(element.getAttribute(attributeName), attributeValue, "Element's attribute "+attributeName+" is mismatched.");
    }

    public void isPageTitleEquals(WebElement element, String expectedPageTitle) throws IOException {
        waitForElementVisible(element);
        Assert.assertTrue(element.isDisplayed(), "Page title locator is invalid.");
        Assert.assertEquals(element.getAttribute("name"), expectedPageTitle, "Page title mismatched.");
    }

    public void isElementNameEquals(WebElement element, String elementText) throws IOException {
        waitForElementVisible(element);
        Assert.assertTrue(element.isDisplayed(), "Element cannot be found.");
        Assert.assertEquals(element.getAttribute("name"), elementText, "Element text mismatched.");
    }

    public void isElementValueEquals(WebElement element, String attributeValue) throws IOException {
        waitForElementVisible(element);
        Assert.assertTrue(element.isDisplayed(), "Element cannot be found.");
        Assert.assertEquals(element.getAttribute("value"), attributeValue, "Element's value is mismatched.");
    }

    public void isElementLabelEquals(WebElement element, String attributeValue) throws IOException {
        waitForElementVisible(element);
        Assert.assertTrue(element.isDisplayed(), "Element cannot be found.");
        Assert.assertEquals(element.getAttribute("label"), attributeValue, "Element's value is mismatched.");
    }

    public void isElementAccessibilityIdTextEquals(WebElement element, String elementText) throws IOException {
        waitForElementVisible(element);
        waitForElementVisible(element);
        Assert.assertEquals(element.getAttribute("name"), elementText, "Element text mismatched.");
    }

    public void isElementTextEquals(WebElement element, String elementText) throws IOException {
        waitForElementVisible(element);
        String actualTextInElement = element.getAttribute("text");
        Assert.assertEquals(actualTextInElement, elementText, "Element text mismatched.");
    }

    public void isTextEquals(WebElement element, String buttonText) throws IOException {
        waitForElementVisible(element);
        isElementNameEquals(element, buttonText);
    }

    public void isLabelTextEquals(String visibleText) {
        iosDriver.findElement(By.xpath("//XCUIElementTypeStaticText[@name=\""+visibleText+"\"]"));
    }

    public void isElementVisible(WebElement element) throws IOException {
        waitForElementVisible(element);
        Assert.assertTrue(element.isDisplayed(), "Element cannot be found.");
    }

    public void isElementNotVisible(WebElement element) throws IOException {
        try {
            Assert.assertFalse(element.isDisplayed(), "Element has found.");
            Assert.fail("\"" + element.getText() + "\"" + " Element has found");
        } catch (NoSuchElementException ex){
            ex.printStackTrace();
        }
    }

    public void isTextVisible(WebElement element, String elementText) throws IOException {
        try{
            scrollTo(elementText);
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            Assert.fail("\"" + elementText + "\"" + " Not found");
        }
    }

    public void isTextNotVisible(WebElement element, String elementText) throws IOException {
        try{
            scrollTo(elementText);
            Assert.fail("\"" + elementText + "\"" + " Element has found");
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
        }
    }

    public static void saveToScenarioDataStore(String variableNameOfValueToBeStoredInDataStore, String valueToBeStoredInDataStore) {
        // Adding value to the Data Store
        DataStore scenarioStore = DataStoreFactory.getScenarioDataStore();
        scenarioStore.put(variableNameOfValueToBeStoredInDataStore, valueToBeStoredInDataStore);
    }

    public static String getTextByScenarioDataStoreName(String variableNameOfValueStoredInDataStore) {
        // Fetching Value from the Data Store
        DataStore scenarioStore = DataStoreFactory.getScenarioDataStore();
        return (String) scenarioStore.get(variableNameOfValueStoredInDataStore);
    }

    public void scrollDown() {
        Dimension size = iosDriver.manage().window().getSize();
        int startY = (int) (size.height * 0.7);
        int endY = (int) (size.height * 0.2);
        int startX = size.height / 2;
        iosDriver.swipe(startX, startY, startX, endY, 800);
    }

    public void scrollTo(String visibleText) {
        iosDriver.findElementByIosUIAutomation("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""+visibleText+"\").instance(0))");
    }

    public void scrollAndTap(String visibleText) {
        try {
            iosDriver.findElementByIosUIAutomation("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""+visibleText+"\").instance(0))").click();
        } catch (Exception ex){
            iosDriver.findElement(By.xpath("//XCUIElementTypeStaticText[@name=\""+visibleText+"\"]")).click();
        }
    }

    public void tapMobileKeyboardEnter() {
        TouchAction touchAction = new TouchAction(iosDriver);
        touchAction.tap(750, 1150).perform();
    }

    public void isWebViewTextEquals(String text) {
        Assert.assertTrue(iosDriver.findElement(By.xpath("//XCUIElementTypeStaticText[@content-desc=\""+ text +"\"]")).isDisplayed(), "\"" + text + "\" cannot be found in webview");
    }

    public void swipeToElement(WebElement element, int duration) throws IOException {
        int topY = element.getLocation().getY();
        int bottomY = topY + element.getSize().getHeight();
        int centerX = element.getLocation().getX() + (element.getSize().getWidth()/2);
        iosDriver.swipe(centerX, bottomY, centerX, topY, duration);
    }

    public void swipeLeftToRightHorizontally() throws InterruptedException {
        //Get the size of screen.
        size = iosDriver.manage().window().getSize();
        System.out.println(size);

        //Find swipe start and end point from screen's with and height.
        //Find startx point which is at right side of screen.
        int startx = (int) (size.width * 0.90);
        //Find endx point which is at left side of screen.
        int endx = (int) (size.width * 0.10);
        //Find vertical point where you wants to swipe. It is in middle of screen height.
        int starty = size.height / 2;
        System.out.println("startx = " + startx + " ,endx = " + endx + " , starty = " + starty);

        //Swipe from Left to Right.
        iosDriver.swipe(endx, starty, startx, starty, 500);
        freeze(2);
    }

    public void swipeRightToLeftHorizontally() throws InterruptedException {
        //Get the size of screen.
        size = iosDriver.manage().window().getSize();
        System.out.println(size);

        //Find swipe start and end point from screen's with and height.
        //Find startx point which is at right side of screen.
        int startx = (int) (size.width * 0.99);
        //Find endx point which is at left side of screen.
        int endx = (int) (size.width * 0.01);
        //Find vertical point where you wants to swipe. It is in middle of screen height.
        int starty = size.height / 2;
        System.out.println("startx = " + startx + " ,endx = " + endx + " , starty = " + starty);

        //Swipe from Right to Left.
        iosDriver.swipe(startx, starty, endx, starty, 500);
        freeze(2);
    }

    public void swipeTopToBottomVertically() throws InterruptedException {
        //Get the size of screen.
        size = iosDriver.manage().window().getSize();
        System.out.println(size);

        //Find swipe start and end point from screen's with and height.
        //Find startY point which is at bottom side of screen.
        int startY = (int) (size.height * 0.90);
        //Find endY point which is at top side of screen.
        int endY = (int) (size.height * 0.10);
        //Find horizontal point where you wants to swipe. It is in middle of screen width.
        int startX = size.width / 2;
        System.out.println("startY = " + startY + " ,endY = " + endY + " , startX = " + startX);

        //Swipe from Top to Bottom.
        iosDriver.swipe(startX, endY, startX, startY, 500);
        freeze(2);
    }

    public void swipeBottomToTopVertically() throws InterruptedException {
        //Get the size of screen.
        size = iosDriver.manage().window().getSize();
        System.out.println(size);

        //Find swipe start and end point from screen's with and height.
        //Find startY point which is at bottom side of screen.
        int startY = (int) (size.height * 0.90);
        //Find endY point which is at top side of screen.
        int endY = (int) (size.height * 0.10);
        //Find horizontal point where you wants to swipe. It is in middle of screen width.
        int startX = size.width / 2;
        System.out.println("startY = " + startY + " ,endY = " + endY + " , startX = " + startX);

        //Swipe from Bottom to Top.
        iosDriver.swipe(startX, startY, startX, endY, 500);
        freeze(2);
    }

    public List<WebElement> getElementsByClassName(String classNameOfElementList, String elementId){
        elements = iosDriver.findElementByClassName(classNameOfElementList).findElements(By.id(elementId));
        return elements;
    }

    public void printElementsNameList(String classNameOfElementList, String elementId){
        System.out.println("Items found:");
        Gauge.writeMessage("Items found:");
        int i = 1;
        for(WebElement element : getElementsByClassName(classNameOfElementList, elementId)) {
            System.out.println(i + ") " + element.getText());
            Gauge.writeMessage(i + ") " + element.getText());
            elementNameList.add(element.getText());
            i++;
        }
    }

    public void addElementNamesToList(List<WebElement> webElementList, List <String> listToAddElementNames){
        for(WebElement element : webElementList) {
            listToAddElementNames.add(element.getText());
        }
    }

    public void IsElementEnable(WebElement element, boolean isEnable) throws IOException {
        if (element.isEnabled() == Boolean.TRUE) {
            System.out.println("Element is enabled");
            Gauge.writeMessage("Element is enabled");
        } else {
            System.out.println("Element is disabled");
            Gauge.writeMessage("Element is disabled");
        }
        Assert.assertEquals(element.isEnabled(), isEnable, "The actual enable/disable status of the element is not match with the expected status.");
    }

    public void isRadioButtonActive(WebElement element, String textToBeReplaced, String replacementText, Boolean expectedStatus) throws IOException {
        Assert.assertEquals(getElementByReplacingXpath(element, textToBeReplaced, replacementText).getAttribute("checked").toLowerCase(), expectedStatus.toString(), "Radio button's active/en-active status is differ from the expected.");
    }

    public String getHTMLPageSource(){
        switchContextTo(WEBVIEW);
        return iosDriver.getPageSource();
    }

    public String getHTMLPageSource(String url){
        switchContextTo(WEBVIEW);
        iosDriver.navigate().to(url);
        return iosDriver.getPageSource();
    }

    public void saveHTMLPageSource(String filePath){
        String FILE_PATH = DriverSetup.PROJECT_ROOT.concat(filePath);
        FileReadWrite.writeToFile(getHTMLPageSource().replaceAll("’", "'").replaceAll("é", "e"), FILE_PATH);
    }

    public void getContextNames(){
        Set<String> contextNames = iosDriver.getContextHandles();
        for (String contextName : contextNames) {
            System.out.println(contextNames); //prints out something like NATIVE_APP \n WEBVIEW_1
        }
    }

    public void switchContextTo(String context){
        if (context.toLowerCase().equals(WEBVIEW.toLowerCase())) {
            iosDriver.context(WEBVIEW); // set context to WEBVIEW_1
        } else {
            iosDriver.context(NATIVE_APP); // set context to NATIVE_APP
        }
    }
    public void freeze(int seconds){
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void navigateBackFromDevice(){
        iosDriver.navigate().back();
    }

    public void setDatePickerIOS(WebElement datePickerIOS, String month, String date, String year){
        ((IOSElement)iosDriver.findElements(By.className("UIAPickerWheel")).get(0)).sendKeys("Sun 25 Jan");
        datePickerIOS.click();
        MobileElement monthElement = (MobileElement) iosDriver.findElements(By.className("UIAPickerWheel")).get(0);
        monthElement.sendKeys(month);
        MobileElement dateElement = (MobileElement) iosDriver.findElementByXPath("//XCUIElementTypeApplication[@name=\"SmartFlashcards\"]/XCUIElementTypeWindow[4]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeDatePicker/XCUIElementTypeOther/XCUIElementTypePickerWheel[2]");
        dateElement.click();
        dateElement.sendKeys(date);
        MobileElement yearElement = (MobileElement) iosDriver.findElementByXPath("//XCUIElementTypeApplication[@name=\"SmartFlashcards\"]/XCUIElementTypeWindow[4]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeDatePicker/XCUIElementTypeOther/XCUIElementTypePickerWheel[3]");
        yearElement.sendKeys(year);
    }


}
