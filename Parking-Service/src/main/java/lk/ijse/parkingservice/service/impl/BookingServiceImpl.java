package lk.ijse.parkingservice.service.impl;

import lk.ijse.parkingservice.dto.BookingDTO;
import lk.ijse.parkingservice.entity.Booking;
import lk.ijse.parkingservice.entity.ParkingSpot;
import lk.ijse.parkingservice.repo.BookingRepository;
import lk.ijse.parkingservice.repo.ParkingSpotRepository;
import lk.ijse.parkingservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    public BookingDTO reserve(UUID userId, UUID vehicleId, UUID spotId) {
        ParkingSpot spot = parkingSpotRepository.findById(spotId)
                .orElseThrow(() -> new RuntimeException("Spot not found"));

        if (!spot.isAvailable()) {
            throw new RuntimeException("Spot already occupied");
        }

        // Mark spot as occupied
        spot.setIsAvailable(false);
        parkingSpotRepository.save(spot);

        // Create booking
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setVehicleId(vehicleId);
        booking.setParkingSpotId(spotId);
        booking.setStartTime(LocalDateTime.now());
        booking.setEndTime(null); // Not ended yet
        booking.setActive(true);

        Booking saved = bookingRepository.save(booking);

        // Manually map to DTO
        BookingDTO dto = new BookingDTO();
        dto.setId(saved.getId());
        dto.setUserId(saved.getUserId());
        dto.setVehicleId(saved.getVehicleId());
        dto.setParkingSpotId(saved.getParkingSpotId());
        dto.setStartTime(saved.getStartTime());
        dto.setEndTime(saved.getEndTime());
        dto.setActive(saved.isActive());

        return dto;
    }
}
