package tests;

import base.BaseTest;
import pages.HomePage;
import utils.CsvUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Test_5 extends BaseTest {

    @DataProvider(name = "signInData")
    public Object[][] getSignInData() {
        List<String[]> csvData = CsvUtils.readResourceCsv("owner.csv", true);
        Object[][] testData = new Object[csvData.size()][1];  // ✅ only 1 column (lastName)
        for (int i = 0; i < csvData.size(); i++) {
            testData[i][0] = csvData.get(i)[1];  // ✅ assuming 2nd column = lastName
        }
        return testData;
    }

    @Test(dataProvider = "signInData")
    public void testFindOwner(String lastName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Step 1: Open clinic promo from HomePage POM
        HomePage homePage = new HomePage(driver);
        homePage.openClinicPromo();

        // Step 2: Click on Find Owners link
        WebElement findOwnersLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a[title='find owners']")));
        findOwnersLink.click();

        // Step 3: Enter last name into input field
        WebElement lastNameInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[name='lastName']")));
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);

        // Step 4: Submit the form
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[class='btn btn-primary']")));
        submitButton.click();

        // Step 5: If error message visible, end test
        boolean isErrorVisible = driver.findElements(By.cssSelector("span[class='help-inline']")).stream()
                .anyMatch(WebElement::isDisplayed);
        if (isErrorVisible) {
            System.out.println("⚠️ No owner found for lastName = " + lastName);
            return;
        }

        // Step 6: Navigate to Veterinarians
        WebElement veterinariansLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a[title='veterinarians']")));
        veterinariansLink.click();

        // Step 7: Go back to Home Page
        WebElement homePageLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a[title='home page']")));
        homePageLink.click();

        System.out.println("✅ Test completed for lastName = " + lastName);
    }
}
