package lk.ijse.parkingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ParkingDTO {
    private UUID id;
    private String location;
    private boolean isAvailable;
    private UUID ownerId;
}
