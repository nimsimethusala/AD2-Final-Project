package lk.ijse.parkingservice.service;

import lk.ijse.parkingservice.dto.BookingDTO;
import lk.ijse.parkingservice.entity.Booking;

import java.util.UUID;

public interface BookingService {
    BookingDTO reserve(UUID userId, UUID vehicleId, UUID spotId);
}
