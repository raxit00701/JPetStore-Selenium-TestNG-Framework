package base;

import factory.DriverFactory;
import factory.BrowserOptionsFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    private final String baseUrl = "https://jpetstore.aspectran.com";
    private final long timeout = 60;

    @Parameters("browser")
    @BeforeClass(alwaysRun = true)   // ✅ One browser session for the whole class
    public void setUp(@Optional("chrome") String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                DriverFactory.setChromeOptions(BrowserOptionsFactory.getChromeOptions());
                break;
            case "firefox":
                DriverFactory.setFirefoxOptions(BrowserOptionsFactory.getFirefoxOptions());
                break;
            default:
                throw new IllegalArgumentException("❌ Browser not supported: " + browser);
        }

        driver = DriverFactory.init(browser);
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

        driver.manage().deleteAllCookies();
        try {
            driver.manage().window().maximize();
        } catch (Exception e) {
            System.out.println("⚠️ Window already maximized, skipping.");
        }
        driver.get(baseUrl);
    }

    @BeforeMethod(alwaysRun = true)
    public void resetState() {
        // ✅ Ensures each test iteration starts from homepage
        driver.manage().deleteAllCookies();
        driver.get(baseUrl);
    }

    @AfterClass(alwaysRun = true)   // ✅ Close only once at the end
    public void tearDown() {
        if (driver != null) {
            DriverFactory.quitDriver();
            System.out.println("🔻 Browser closed after all tests in class.");
        }
    }
}
