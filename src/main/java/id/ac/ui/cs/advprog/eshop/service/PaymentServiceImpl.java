package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String status = "REJECTED";

        if (method.equals("VOUCHER")) {
            String code = paymentData.get("voucherCode");
            if (code != null && code.length() == 16 && code.startsWith("ESHOP")) {
                long digits = code.chars().filter(Character::isDigit).count();
                if (digits == 8) status = "SUCCESS";
            }
        } else if (method.equals("COD")) {
            String addr = paymentData.get("address");
            String fee = paymentData.get("deliveryFee");
            if (addr != null && !addr.isBlank() && fee != null && !fee.isBlank()) {
                status = "SUCCESS";
            }
        }

        Payment payment = new Payment(UUID.randomUUID().toString(), method, paymentData);
        return setStatus(payment, status, order);
    }

    @Override
    public Payment setStatus(Payment payment, String status, Order order) {
        payment.setStatus(status);
        if (status.equals("SUCCESS")) {
            order.setStatus("SUCCESS");
        } else if (status.equals("REJECTED")) {
            order.setStatus("FAILED");
        }
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(String paymentId) { return paymentRepository.findById(paymentId); }

    @Override
    public List<Payment> getAllPayments() { return paymentRepository.findAll(); }
}