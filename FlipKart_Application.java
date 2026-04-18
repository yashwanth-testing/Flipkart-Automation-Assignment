package escrowstack_Assignment;

import java.io.File;
import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

public class FlipKart_Application {
	public static void main(String[] args) throws Exception {
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.flipkart.com/");

        try {
            driver.findElement(By.xpath("//button[contains(text(),'✕')]")).click();
        } catch (Exception e) {
        }

        driver.findElement(By.name("q")).sendKeys("Bluetooth Speakers"+Keys.ENTER);
        Thread.sleep(2000);

        driver.findElement(By.xpath("//div[text()='Brand']")).click();
        driver.findElement(By.xpath("//div[text()='boAt']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//div[contains(text(),'4★ & above')]")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("//div[text()='Price -- Low to High']")).click();
        Thread.sleep(2000);

        driver.findElement(By.xpath("(//div[@class='lWX0_T'])[1]")).click();
        Thread.sleep(2000);

        String parent = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();

        for (String win : windows) {
            if (!win.equals(parent)) {
                driver.switchTo().window(win);
            }
        }

        Thread.sleep(2000);

        try {
            WebElement offers = driver.findElement(By.xpath("//*[contains(text(),'offers')]"));
            if (offers.isDisplayed()) {
                int count = driver.findElements(By.xpath("//div[contains(text(),'Cashback')]")).size();
                System.out.println("Number of offers: " + count);
            }
        } catch (Exception e) {
            System.out.println("No offers section");
        }

        try {
            WebElement addToCart = driver.findElement(By.xpath("//div[contains(text(),'Add to cart')"));

            if (addToCart.isDisplayed() && addToCart.isEnabled()) {

                addToCart.click();
                Thread.sleep(2000);

                takeScreenshot(driver, "cart_result.png");

            } else {
                throw new Exception("Button disabled");
            }

        } catch (Exception e) {

            System.out.println("Product unavailable – could not be added to cart.");
            takeScreenshot(driver, "result.png");
        }

        driver.quit();
    }

    public static void takeScreenshot(WebDriver driver, String fileName) throws Exception {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        File dest=new File("./screenshots/" + fileName);
        FileHandler.copy(src, dest);
	}

}
