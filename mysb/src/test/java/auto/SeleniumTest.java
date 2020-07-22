package auto;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class SeleniumTest {
    private static final String CHROME_WEB_DRIVER_PATH = "C:\\allenWrok\\tool\\webDriver\\chromedriver.exe";
    private static final String CHROME_PATH = "C:\\allenWrok\\tool\\GoogleChromePortable64_83\\App\\Chrome-bin\\chrome.exe";
    private static final String FIREFOX_WEB_DRIVER_PATH = "C:\\allenWrok\\tool\\webDriver\\geckodriver.exe";
    private static final String FIREFOX_PATH = "C:\\allenWrok\\tool\\FirefoxPortable\\App\\Firefox64\\firefox.exe";


    private static final String URL = "https://www.ctbcbank.com/twrbo/zh_tw/onlinecounter_index/cc_service/cc_service_register/cc_service_register_activity.html";

    //private static final String URL="https://www.yahoo.com.tw";
    private ChromeDriver chromeInit() {
        System.setProperty("webdriver.chrome.driver", CHROME_WEB_DRIVER_PATH);
        ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.setCapability(CapabilityType.SUPPORTS_NETWORK_CONNECTION,true);

        chromeOptions.addArguments("User-Agent= Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");
        //以最高權限運行
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("blink-settings=imagesEnabled=false");
        chromeOptions.setBinary(CHROME_PATH);
        ChromeDriver driver = new ChromeDriver(chromeOptions);
        return driver;
    }

    private FirefoxDriver firefoxInit() {
        System.setProperty("webdriver.gecko.driver", FIREFOX_WEB_DRIVER_PATH);
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(FIREFOX_PATH);
        FirefoxDriver firefoxDriver = new FirefoxDriver(firefoxOptions);
        return firefoxDriver;
    }

    @Test
    public void test() {
        WebDriver driver = firefoxInit();

        //頁面讀取逾時設定
        //driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

        //轉網頁的寫法，以下可以2選1
        //driver.get("https://selenium.dev");
        //driver.navigate().to(URL);
        try {
            login(driver);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            driver.quit();
        }


    }

    private void login(WebDriver driver) throws InterruptedException {
        driver.navigate().to(URL);
        Thread.sleep(50000);

        WebElement id = driver.findElement(new By.ByCssSelector("input[formcontrolname='inputCustId']"));
        //WebElement id = driver.findElementByCssSelector("input[formcontrolname='inputCustId']");
        System.out.println(id);
        id.sendKeys("N124152352");


    }


}
