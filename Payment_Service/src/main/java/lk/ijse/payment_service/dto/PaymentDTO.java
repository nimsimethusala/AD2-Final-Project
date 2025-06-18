package lk.ijse.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentDTO {
    private UUID id;
    private UUID bookingId;
    private double amount;
    private String method; // e.g., CARD, PAYPAL, MOCK
    private String status; // e.g., SUCCESS, FAILED
    private LocalDateTime paymentTime;
}
