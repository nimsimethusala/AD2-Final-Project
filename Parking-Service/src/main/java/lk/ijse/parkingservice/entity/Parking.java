package lk.ijse.parkingservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String location;
    private boolean isAvailable;
    private UUID ownerId;
}
