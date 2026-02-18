package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Alert;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class DeleteProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void testDeleteProduct(ChromeDriver driver) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Arrange new product
        driver.get(baseUrl + "/product/create");
        String productName = "Product to be Deleted";
        driver.findElement(By.id("nameInput")).sendKeys(productName);
        driver.findElement(By.id("quantityInput")).sendKeys("50");
        driver.findElement(By.tagName("button")).click();

        // wait for redirect to product list
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table")));

        // Verify product in the list
        String pageSourceBefore = driver.getPageSource();
        assertTrue(pageSourceBefore.contains(productName), "Product should be in the list before deletion");

        // click Delete button
        // Menggunakan XPath untuk memastikan kita mengklik tombol delete milik produk yang baru dibuat
        driver.findElement(By.xpath("//td[contains(text(), '" + productName + "')]/..//a[contains(text(), 'Delete')]")).click();

        // handle Alert confirmation (Browser Pop-up)
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (Exception e) {
            // test continue
        }

        // wait for page to reload after deletion
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table")));

        // Verify the product is delete and redirect to /product/list
        String pageSourceAfter = driver.getPageSource();
        assertFalse(pageSourceAfter.contains(productName), "Produk seharusnya sudah tidak ada di halaman list setelah dihapus.");
    }
}