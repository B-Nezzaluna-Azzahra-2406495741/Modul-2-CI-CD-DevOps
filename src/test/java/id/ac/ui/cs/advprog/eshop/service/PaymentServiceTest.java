package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
    @InjectMocks
    PaymentServiceImpl paymentService; 
    @Mock
    PaymentRepository paymentRepository;

    Order order;

    @BeforeEach
    void setUp() {
        order = new Order("o1", null, 123L, "Nezza");
    }

    @Test
    void testAddPaymentVoucherSuccess() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP12345678ABC"); 
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);
        
        Payment payment = paymentService.addPayment(order, "VOUCHER", data);
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("SUCCESS", order.getStatus());
    }

    @Test
    void testAddPaymentVoucherRejected() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "INVALID"); 
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);
        
        Payment payment = paymentService.addPayment(order, "VOUCHER", data);
        assertEquals("REJECTED", payment.getStatus());
        assertEquals("FAILED", order.getStatus());
    }

    @Test
    void testAddPaymentCODSuccess() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "Depok");
        data.put("deliveryFee", "15000");
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);
        
        Payment payment = paymentService.addPayment(order, "COD", data);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testAddPaymentCODRejected() {
        Map<String, String> data = new HashMap<>();
        data.put("address", ""); 
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);
        
        Payment payment = paymentService.addPayment(order, "COD", data);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetStatusSuccess() {
        Payment payment = new Payment("p1", "VOUCHER", new HashMap<>());
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        paymentService.setStatus(payment, "SUCCESS", order);
        assertEquals("SUCCESS", order.getStatus());
    }
}