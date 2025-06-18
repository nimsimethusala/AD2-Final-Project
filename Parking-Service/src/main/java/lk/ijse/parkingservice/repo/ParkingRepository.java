package lk.ijse.parkingservice.repo;

import lk.ijse.parkingservice.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Parking, String> {

}
