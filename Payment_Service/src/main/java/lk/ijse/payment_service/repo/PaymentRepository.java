package lk.ijse.payment_service.repo;

import java.util.Optional;
import lk.ijse.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, String> {
    Optional<Payment> findByBookingId(UUID bookingId);
}
