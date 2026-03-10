package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<>();
        this.paymentData.put("voucherCode", "ESHOP1234ABC5678");
    }

    @Test
    void testCreatePayment() {
        Payment payment = new Payment("p1", "VOUCHER", "PENDING", paymentData);
        assertEquals("p1", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals("PENDING", payment.getStatus());
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testSetStatusValid() {
        Payment payment = new Payment("p1", "VOUCHER", "PENDING", paymentData);
        payment.setStatus("SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testSetStatusInvalid() {
        Payment payment = new Payment("p1", "VOUCHER", "PENDING", paymentData);
        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("MEOW");
        });
    }
}