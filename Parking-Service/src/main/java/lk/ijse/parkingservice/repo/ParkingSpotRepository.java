package lk.ijse.parkingservice.repo;

import lk.ijse.parkingservice.entity.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, UUID> {
    List<ParkingSpot> findByIsAvailableTrue();
}
