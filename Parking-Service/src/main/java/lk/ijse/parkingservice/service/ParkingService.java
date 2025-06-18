package lk.ijse.parkingservice.service;

import lk.ijse.parkingservice.dto.ParkingSpotDTO;
import lk.ijse.parkingservice.entity.ParkingSpot;

import java.util.List;
import java.util.UUID;

public interface ParkingService {
    ParkingSpotDTO updateSpotStatus(UUID id, boolean isAvailable);
    List<ParkingSpotDTO> getAvailableSpots();
    ParkingSpotDTO addSpot(ParkingSpotDTO dto);
    String deleteSpot(UUID id);
}
