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





----------------------------------------------------------------------------------------------------------------------------------------------------------

    package factory;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;

public class BrowserOptionsFactory {

    /**
     * Build ChromeOptions with clean, incognito, popup-free setup
     */
    public static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();

        // ✅ Incognito mode
        options.addArguments("--incognito");

        // ✅ Maximize window
        options.addArguments("--start-maximized");

        // ✅ Disable distractions
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--no-default-browser-check");
        options.addArguments("--disable-save-password-bubble");

        return options;
    }

    /**
     * Build FirefoxOptions with private mode and popup-free setup
     */
    public static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();

        // ✅ Private browsing
        options.addArguments("-private");

        // ✅ Disable notifications
        options.addPreference("dom.webnotifications.enabled", false);
        options.addPreference("dom.push.enabled", false);

        // ✅ Disable extensions (best-effort)
        options.addPreference("extensions.enabled", false);

        // ✅ Disable password manager prompts
        options.addPreference("signon.rememberSignons", false);
        options.addPreference("signon.autofillForms", false);
        options.addPreference("signon.formlessCapture.enabled", false);
        options.addPreference("signon.generation.available", false);
        options.addPreference("signon.management.page.breach-alerts.enabled", false);
        options.addPreference("extensions.formautofill.creditCards.enabled", false);
        options.addPreference("extensions.formautofill.addresses.enabled", false);

        return options;
    }

    /**
     * Build EdgeOptions with InPrivate, start maximized, and distraction-free setup
     */
    public static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();

        // ✅ InPrivate mode (+) start maximized
        options.addArguments("--inprivate");
        options.addArguments("--start-maximized");

        // ✅ Disable distractions (Chromium flags work on Edge)
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--no-default-browser-check");
        options.addArguments("--disable-save-password-bubble");

        return options;
    }
}

