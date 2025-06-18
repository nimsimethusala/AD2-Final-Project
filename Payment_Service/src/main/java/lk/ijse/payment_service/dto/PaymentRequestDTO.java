package lk.ijse.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentRequestDTO {
    private UUID bookingId;
    private double amount;
    private String method;
}
