package factory;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

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

        // ✅ Disable extensions
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
}
