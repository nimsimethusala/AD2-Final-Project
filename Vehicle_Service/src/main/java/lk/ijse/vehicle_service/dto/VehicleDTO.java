package lk.ijse.vehicle_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VehicleDTO {
    private UUID id;
    private String licensePlate;
    private String brand;
    private String model;
    private UUID userId;
    private boolean isParked = false;
}
