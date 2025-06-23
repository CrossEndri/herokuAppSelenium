import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class Login {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void prepare() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito"); // Run in incognito mode

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @BeforeMethod
    public void goToLoginPage() {
        driver.navigate().to("https://the-internet.herokuapp.com/login");
    }

    @Test(priority = 1)
    public void LP001() {
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        WebElement successMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("flash"))
        );

        String actualText = successMessage.getText();
        Assert.assertTrue(actualText.contains("You logged into a secure area!"),
                "Success message not found!");

        driver.findElement(By.linkText("Logout")).click();
    }

    @Test(priority = 2)
    public void LP002() {
        driver.findElement(By.id("username")).sendKeys("Tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("flash"))
        );

        String actualText = errorMessage.getText();
        Assert.assertTrue(actualText.contains("Your username is invalid!"),
                "Error message not found or incorrect. Actual: " + actualText);
    }

    @Test(priority = 3)
    public void LP003() {
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("supersecretpassword!");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("flash"))
        );

        String actualText = errorMessage.getText();
        Assert.assertTrue(actualText.contains("Your password is invalid!"),
                "Error message not found or incorrect. Actual: " + actualText);
    }

    @Test(priority = 4)
    public void LP004() {
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SUPERSECRETPASSWORD!");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("flash"))
        );

        String actualText = errorMessage.getText();
        Assert.assertTrue(actualText.contains("Your password is invalid!"),
                "Error message not found or incorrect. Actual: " + actualText);
    }

    @Test(priority = 5)
    public void LP005() {
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys(" SuperSecretPassword!");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("flash"))
        );

        String actualText = errorMessage.getText();
        Assert.assertTrue(actualText.contains("Your password is invalid!"),
                "Error message not found or incorrect. Actual: " + actualText);
    }

    @Test(priority = 6)
    public void LP006() {
        driver.findElement(By.id("username")).sendKeys(" tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("flash"))
        );

        String actualText = errorMessage.getText();
        Assert.assertTrue(actualText.contains("Your username is invalid!"),
                "Error message not found or incorrect. Actual: " + actualText);
    }

    @Test(priority = 7)
    public void LP010() {
        driver.findElement(By.id("username")).sendKeys(" ");
        driver.findElement(By.id("password")).sendKeys(" ");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("flash"))
        );

        String actualText = errorMessage.getText();
        Assert.assertTrue(actualText.contains("Your username is invalid!"),
                "Error message not found or incorrect. Actual: " + actualText);
    }


    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}