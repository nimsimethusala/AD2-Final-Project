package lk.ijse.vehicle_service.service;

import lk.ijse.vehicle_service.dto.VehicleDTO;
import lk.ijse.vehicle_service.entity.Vehicle;

import java.util.List;
import java.util.UUID;

public interface VehicleService {
    VehicleDTO addVehicle(Vehicle vehicle);
    List<VehicleDTO> getVehiclesByUserId(UUID userId);
    String simulateEntry(UUID vehicleId);
    String simulateExit(UUID vehicleId);
    VehicleDTO updateVehicle(UUID id, Vehicle updated);
    String deleteVehicle(UUID id);
}
