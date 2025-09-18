package factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> TL_DRIVER = new ThreadLocal<>();

    private static ChromeOptions chromeOptions;
    private static FirefoxOptions firefoxOptions;

    public static void setChromeOptions(ChromeOptions options) {
        chromeOptions = options;
    }

    public static void setFirefoxOptions(FirefoxOptions options) {
        firefoxOptions = options;
    }

    public static WebDriver init(String browser) {
        if (TL_DRIVER.get() == null) {
            switch (browser.toLowerCase()) {
                case "chrome":
                    if (chromeOptions == null) {
                        chromeOptions = new ChromeOptions();
                        chromeOptions.addArguments("--start-maximized", "--incognito");
                    }
                    TL_DRIVER.set(new ChromeDriver(chromeOptions));
                    break;

                case "firefox":
                    if (firefoxOptions == null) {
                        firefoxOptions = new FirefoxOptions();
                        firefoxOptions.addArguments("-private");
                    }
                    TL_DRIVER.set(new FirefoxDriver(firefoxOptions));
                    break;

                default:
                    throw new IllegalArgumentException("❌ Unsupported browser: " + browser);
            }
        }
        return getDriver();
    }

    public static WebDriver getDriver() {
        return TL_DRIVER.get();
    }

    // ✅ New helper
    public static WebDriver getActiveDriverOrThrow() {
        WebDriver driver = TL_DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("❌ No active WebDriver found. Make sure driver is initialized in BaseTest before using it.");
        }
        return driver;
    }

    public static void quitDriver() {
        if (TL_DRIVER.get() != null) {
            TL_DRIVER.get().quit();
            TL_DRIVER.remove();
        }
    }
}
