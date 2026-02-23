package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EshopApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testMain() {
        assertDoesNotThrow(() -> EshopApplication.main(new String[]{"--server.port=0"}));
    }
}