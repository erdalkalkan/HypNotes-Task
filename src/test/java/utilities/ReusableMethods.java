package utilities;

import static stepDefinitions.Hooks.actions;
import static stepDefinitions.Hooks.driver;

import com.github.javafaker.Faker;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ReusableMethods {

    private static WebDriverWait wait;

    //stale Element hatası alıyorsan bunu deneyebilirsin, Ömer Hoca'nın tavsiyesi
    public static void staleElementSolution(WebElement webElement) {
        Duration timeout = Duration.ofSeconds(30);
        new WebDriverWait(driver, timeout)
                .ignoring(StaleElementReferenceException.class)
                .until((WebDriver d) -> {
                    webElement.click();
                    return true;
                });
    }

    public static String getScreenshot() throws IOException {
        // naming the screenshot with the current date to avoid duplication
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        // TakesScreenshot is an interface of selenium that takes the screenshot
        TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        // full path to the screenshot location
        String target = System.getProperty("user.dir") + "/test-output/Screenshots/" + date + ".png";
        File finalDestination = new File(target);
        // save the screenshot to the path given
        FileUtils.copyFile(source, finalDestination);
        return target;
    }

    //========Switching Window=====//
    public static Object switchToWindow(String targetTitle) {
        String origin = Driver.getDriver().getWindowHandle();
        for (String handle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(handle);
            if (Driver.getDriver().getTitle().equals(targetTitle)) {
                return null;
            }
        }
        Driver.getDriver().switchTo().window(origin);
        return null;
    }

    //========Hover Over(ScrollDown)=====//
    public static void hover(WebElement element) {
        //      Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(element).perform();
    }

    //==========Return a list of string given a list of Web Element====////
    public static List<String> getElementsText(List<WebElement> list) {
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : list) {
            if (!el.getText().isEmpty()) {
                elemTexts.add(el.getText());
            }
        }
        return elemTexts;
    }

    //========Returns the Text of the element given an element locator==//
    public static List<String> getElementsText(By locator) {
        List<WebElement> elems = Driver.getDriver().findElements(locator);
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : elems) {
            if (!el.getText().isEmpty()) {
                elemTexts.add(el.getText());
            }
        }
        return elemTexts;
    }

    //   HARD WAIT WITH THREAD.SLEEP
//   waitFor(5);  => waits for 5 second => Thread.sleep(5000)
    public static void waitFor(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //===============Explicit Wait==============//
    public static WebElement waitForVisibility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForVisibility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickablility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForClickablility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void clickWithTimeOut(WebElement element, int timeout) {
        for (int i = 0; i < timeout; i++) {
            try {
                element.click();
                return;
            } catch (WebDriverException e) {
                waitFor(1);
            }
        }
    }

    public static void waitForPageToLoad(int timeout) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            System.out.println("Waiting for page to load...");
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
            wait.until(expectation);
        } catch (Throwable error) {
            System.out.println(
                    "Timeout waiting for Page Load Request to complete after " + timeout + " seconds");
        }
    }

    //======Fluent Wait====//
    public static WebElement fluentWait(final WebElement webElement, int timeout) {
        //FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver()).withTimeout(timeinsec, TimeUnit.SECONDS).pollingEvery(timeinsec, TimeUnit.SECONDS);
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver())
                .withTimeout(Duration.ofSeconds(3))//Wait 3 second each time
                .pollingEvery(Duration.ofSeconds(1))////Check for the element every 1 second
                .ignoring(NoSuchMethodException.class);
        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return webElement;
            }
        });
        return element;
    }

    public static void doubleClick(WebElement element) {
        new Actions(Driver.getDriver()).doubleClick(element).build().perform();
    }

    public static void selectCheckBox(WebElement element, boolean check) {
        if (check) {
            if (!element.isSelected()) {
                element.click();
            }
        } else {
            if (element.isSelected()) {
                element.click();
            }
        }
    }

    public static WebElement selectRandomTextFromDropdown(Select select) {
        Random random = new Random();
        List<WebElement> weblist = select.getOptions();
        int optionIndex = 1 + random.nextInt(weblist.size() - 1);
        select.selectByIndex(optionIndex);
        return select.getFirstSelectedOption();
    }

    public static void verifyElementDisplayed(By by) {
        try {
            assertTrue("Element not visible: " + by, Driver.getDriver().findElement(by).isDisplayed());
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + by);
        }
    }

    public static void verifyElementNotDisplayed(By by) {
        try {
            assertFalse("Element should not be visible: " + by, Driver.getDriver().findElement(by).isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    public static void verifyElementNotDisplayed(WebElement element) {
        try {
            assertFalse("Element should not be visible: " + element, element.isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    public static void verifyElementDisplayed(WebElement element) {
        try {
            assertTrue("Element not visible: " + element, element.isDisplayed());
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + element);
        }
    }

    public static void verifyElementEnabled(WebElement element) {
        try {
            assertTrue("Element not enabled: " + element, element.isEnabled());
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + element);
        }
    }

    protected static WebElement waitClickableByOfElement(WebElement webElement) {
        WebElement element = null;

        try {
            element = wait.until(ExpectedConditions.elementToBeClickable(webElement));
        } catch (Exception e) {
        }
        return element;
    }

    protected WebElement waitVisibleByLocator(By locator) {
        WebElement element = null;

        try {
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
        }
        return element;
    }

    public static void clickElement(WebElement element) {
        element.click();
    }

    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void switchToWindow(int windowNumber) {
        List<String> list = new ArrayList<>(Driver.getDriver().getWindowHandles());
        Driver.getDriver().switchTo().window(list.get(windowNumber));
    }

    public static void scrollAndClickWithJS(WebElement webElement) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", webElement);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", webElement);
    }


    public static void uploadFilePath(String filePath) {
        try {
            ReusableMethods.waitFor(3);
            StringSelection stringSelection = new StringSelection(filePath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
            Robot robot = new Robot();
            //pressing ctrl+v
            robot.keyPress(KeyEvent.VK_CONTROL);
            ReusableMethods.waitFor(3);
            robot.keyPress(KeyEvent.VK_V);
            ReusableMethods.waitFor(3);
            //releasing ctrl+v
            robot.keyRelease(KeyEvent.VK_CONTROL);
            ReusableMethods.waitFor(3);
            robot.keyRelease(KeyEvent.VK_V);
            ReusableMethods.waitFor(3);
            System.out.println("PASSED");
            //pressing enter
            ReusableMethods.waitFor(3);
            robot.keyPress(KeyEvent.VK_ENTER);
            ReusableMethods.waitFor(3);
            //releasing enter
            robot.keyRelease(KeyEvent.VK_ENTER);
            ReusableMethods.waitFor(3);
            System.out.println("ENTER");
        } catch (Exception e) {
        }

    }

    public static void clearValue(WebElement element) {
        String value = element.getAttribute("value");
        for (int i = 0; i < value.length(); i++) {
            element.sendKeys(Keys.BACK_SPACE);
        }
    }


    public static void clearValue(WebElement element, String text) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value','" + text + "')", element);
    }

    public static void nullCheck(WebElement element, String text) {
        if (text != null) {
            element.sendKeys(text);
        } else System.err.println("Null is not allowed for this method");
    }

    public static void sendText(WebElement element, String text) {
        try {
            waitForClickablility(element, 15).sendKeys(text);
        } catch (ElementNotInteractableException e) {
            scrollToElement(element);
            sendText(element, text);
        }
    }

    /*
   This method accepts a String "expectedTitle" and Asserts if it is true
    */
    public static void verifyTitle(String expectedTitle) {

        Assert.assertEquals(Driver.getDriver().getTitle(), expectedTitle);

    }

    public static void verifyURL(String expectedURL) {
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().toLowerCase().contains(expectedURL));
    }

    public static void waitAndClickElement(WebElement element, int seconds) {
        for (int i = 0; i < seconds; i++) {
            try {
                element.click();
                break;
            } catch (Exception e) {
                waitFor(1);
            }
        }
    }

    public static String getElementText(WebElement element) {
        waitForVisibility(element, 5);
        return element.getText();
    }

    // Metinden sadece rakamları içeren bir liste döndüren fonksiyon
    public static List<String> extractNumbers(String text) {
        List<String> numbers = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+"); // \d ifadesi bir veya daha fazla rakamı temsil eder
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            numbers.add(matcher.group()); // Eşleşen rakamları listeye ekle
        }
        return numbers;
    }

    public static Random random = new Random();
    public static StringBuilder sb = new StringBuilder();
    public static String text = "abcdefghijklmnopqrstuvwxyz";

    // Random text olusturma

    public static String randomText(int textSize) {


        for (int i = 0; i < textSize; i++) {
            char randomChar = text.charAt(random.nextInt(text.length()));
            sb.append(randomChar);
        }

        return sb.toString();
    }

    // Random email olusturma

    public static String randomEmail(int initialTextSize) {
        for (int i = 0; i < initialTextSize; i++) {
            char randomChar = text.charAt(random.nextInt(text.length()));
            sb.append(randomChar);
        }

        return sb.toString() + "@gmail.com";

    }

    public static String randomPassword(int textSize) {


        for (int i = 0; i < textSize; i++) {
            int randomInt=random.nextInt(textSize);
            char randomChar = text.charAt(random.nextInt(text.length()));
            sb.append(randomChar);
            sb.append(randomInt);
        }

        return sb.toString();
    }



    public static void clearAndSend(WebElement webElement, String str) {
        webElement.clear();
        webElement.sendKeys(str);

    }

    public static void selectByVisibleText(WebElement webElement, String text) {
        Select select = new Select(webElement);
        select.selectByVisibleText(text);
    }

    public static void selectByValue(WebElement webElement, String value) {
        Select select = new Select(webElement);
        select.selectByValue(value);

    }

    public static void selectByIndex(WebElement webElement, int index) {
        Select select = new Select(webElement);
        select.selectByIndex(index);

    }

    //bir isim oluşturur Faker classını kullanarak
    public static String createName() {
        Faker faker = new Faker();
        String name = faker.funnyName().name();

        return name;
    }

    //şimdiki tarihi ve saati oluşturur parametre olarak ileri geri ayarlayabilirsin
    //        format: "2025-01-01 00:00:00"
    public static String createDateWithTime(int plusDay, int plusHour) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, plusDay);
        calendar.add(Calendar.HOUR, plusHour);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return simpleDateFormat.format(calendar.getTime());
    }

    // bir dosyaya birşeyler yazdırmak için kullanabilirisiniz
    // parametre olarak dosyanın path'ini ve yazılacak data adını girmelisiniz
    // String path = System.getProperty("user.dir") + "/src/test/resources/dummy.txt";
    public static void writeOverTxt(String path, int keepData) {
        FileWriter file = null;
        try {
            file = new FileWriter(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            file.write(keepData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //dolu bir dosyaya ulaşıp veri kullanmak için kullanabilirisiniz
    // parametre olarak dosyanın path'ini girmelisiniz, int olarak datayı döner
    //String path = System.getProperty("user.dir") + "/src/test/resources/dummy.txt";
    public static int readTxt(String path) {
        //güncel datayı okur
        FileReader reader = null;
        try {
            reader = new FileReader(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        BufferedReader file2 = new BufferedReader(reader);
        int keepedData;
        try {
            keepedData = Integer.parseInt(file2.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            file2.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return keepedData;
    }


}

