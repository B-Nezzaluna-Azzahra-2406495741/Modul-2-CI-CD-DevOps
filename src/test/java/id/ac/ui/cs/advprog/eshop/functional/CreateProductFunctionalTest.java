package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {
    /**
     * The port number assigned to the running application during test execution.
     * Set automatically during each test run by Spring Framework's test context.
     */
    @LocalServerPort
    private int serverPort;

    /**
     * The base URL for testing. Default to {@code http://localhost}.
     */
    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void testCreateProductAndVerifyInList(ChromeDriver driver) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // navigation to the home page
        driver.get(baseUrl);

        // Wait for home page to load and click the button to the product list page
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Let's Create Product!"))).click();

        // wait for product list page to load and verify navigation
        wait.until(ExpectedConditions.titleIs("Product List"));
        String productListTitle = driver.getTitle();
        assertEquals("Product List", productListTitle);

        // click "Create Product" button on the product list page
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Create Product"))).click();

        // wait for create product page to load and verify navigation
        wait.until(ExpectedConditions.titleIs("Create New Product"));
        String createPageTitle = driver.getTitle();
        assertEquals("Create New Product", createPageTitle);

        // fill the form and submit
        String productName = "Sampo Cap Bambang";
        int productQuantity = 100;

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nameInput"))).sendKeys(productName);
        driver.findElement(By.id("quantityInput")).sendKeys(String.valueOf(productQuantity));
        driver.findElement(By.tagName("button")).click();

        // wait for redirect back to the product list page
        wait.until(ExpectedConditions.titleIs("Product List"));
        String productListTitleAfterCreation = driver.getTitle();
        assertEquals("Product List", productListTitleAfterCreation);

        // verify the new product is displayed in the product list
        String productListContent = driver.getPageSource();
        assertTrue(productListContent.contains(productName));
        assertTrue(productListContent.contains(String.valueOf(productQuantity)));
    }
}