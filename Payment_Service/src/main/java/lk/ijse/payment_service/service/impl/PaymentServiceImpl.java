package lk.ijse.payment_service.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.payment_service.dto.PaymentDTO;
import lk.ijse.payment_service.dto.PaymentRequestDTO;
import lk.ijse.payment_service.entity.Payment;
import lk.ijse.payment_service.repo.PaymentRepository;
import lk.ijse.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public PaymentDTO processPayment(PaymentRequestDTO request) {
        Payment payment = new Payment();
        payment.setBookingId(request.getBookingId());
        payment.setAmount(request.getAmount());
        payment.setMethod(request.getMethod());
        payment.setStatus("SUCCESS");
        payment.setPaymentTime(LocalDateTime.now());

        // Save payment entity
        Payment savedPayment = paymentRepository.save(payment);

        // Convert saved Payment entity to PaymentDTO
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(savedPayment.getId());
        paymentDTO.setBookingId(savedPayment.getBookingId());
        paymentDTO.setAmount(savedPayment.getAmount());
        paymentDTO.setMethod(savedPayment.getMethod());
        paymentDTO.setStatus(savedPayment.getStatus());
        paymentDTO.setPaymentTime(savedPayment.getPaymentTime());

        return paymentDTO;
    }

    public PaymentDTO getByBookingId(UUID bookingId) {
        Payment payment = paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Payment not found for booking ID " + bookingId));

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getId());
        paymentDTO.setBookingId(payment.getBookingId());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setMethod(payment.getMethod());
        paymentDTO.setStatus(payment.getStatus());
        paymentDTO.setPaymentTime(payment.getPaymentTime());

        return paymentDTO;
    }
}
