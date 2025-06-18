package lk.ijse.parkingservice.service.impl;

import lk.ijse.parkingservice.dto.ParkingSpotDTO;
import lk.ijse.parkingservice.entity.ParkingSpot;
import lk.ijse.parkingservice.repo.ParkingSpotRepository;
import lk.ijse.parkingservice.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ParkingServiceImpl implements ParkingService {
    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    public List<ParkingSpotDTO> getAvailableSpots() {
        List<ParkingSpot> spots = parkingSpotRepository.findByIsAvailableTrue();
        List<ParkingSpotDTO> dtoList = new ArrayList<>();

        for (ParkingSpot spot : spots) {
            ParkingSpotDTO dto = new ParkingSpotDTO();
            dto.setId(spot.getId());
            dto.setLocation(spot.getLocation());
            dto.setIsAvailable(spot.isAvailable());
            dto.setOwnerId(spot.getOwnerId());
            dtoList.add(dto);
        }

        return dtoList;
    }

    public ParkingSpotDTO addSpot(ParkingSpotDTO dto) {
        ParkingSpot spot = new ParkingSpot();
        spot.setLocation(dto.getLocation());
        spot.setIsAvailable(true);
        spot.setOwnerId(dto.getOwnerId());

        ParkingSpot savedSpot = parkingSpotRepository.save(spot);

        ParkingSpotDTO result = new ParkingSpotDTO();
        result.setId(savedSpot.getId());
        result.setLocation(savedSpot.getLocation());
        result.setIsAvailable(savedSpot.isAvailable());
        result.setOwnerId(savedSpot.getOwnerId());

        return result;
    }

    public ParkingSpotDTO updateSpotStatus(UUID id, boolean isAvailable) {
        ParkingSpot spot = parkingSpotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking spot not found"));

        spot.setIsAvailable(isAvailable);
        ParkingSpot updatedSpot = parkingSpotRepository.save(spot);

        ParkingSpotDTO dto = new ParkingSpotDTO();
        dto.setId(updatedSpot.getId());
        dto.setLocation(updatedSpot.getLocation());
        dto.setIsAvailable(updatedSpot.isAvailable());
        dto.setOwnerId(updatedSpot.getOwnerId());

        return dto;
    }

    public String deleteSpot(UUID id) {
        ParkingSpot spot = parkingSpotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking spot not found"));

        if (!spot.isAvailable()) {
            throw new RuntimeException("Cannot delete an occupied spot");
        }

        parkingSpotRepository.deleteById(id);
        return "Parking spot with ID " + id + " has been deleted.";
    }

}
