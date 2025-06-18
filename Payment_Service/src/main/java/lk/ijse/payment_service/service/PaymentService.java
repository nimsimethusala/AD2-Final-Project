package lk.ijse.payment_service.service;

import lk.ijse.payment_service.dto.PaymentDTO;
import lk.ijse.payment_service.dto.PaymentRequestDTO;
import lk.ijse.payment_service.entity.Payment;

import java.util.UUID;

public interface PaymentService {
    PaymentDTO getByBookingId(UUID bookingId);
    PaymentDTO processPayment(PaymentRequestDTO request);
}
