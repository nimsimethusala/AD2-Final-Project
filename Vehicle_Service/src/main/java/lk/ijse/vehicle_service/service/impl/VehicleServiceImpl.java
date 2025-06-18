package lk.ijse.vehicle_service.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.vehicle_service.dto.VehicleDTO;
import lk.ijse.vehicle_service.entity.Vehicle;
import lk.ijse.vehicle_service.repo.VehicleRepository;
import lk.ijse.vehicle_service.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public VehicleDTO addVehicle(Vehicle vehicle) {
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        VehicleDTO dto = new VehicleDTO();
        dto.setId(savedVehicle.getId());
        dto.setLicensePlate(savedVehicle.getLicensePlate());
        dto.setBrand(savedVehicle.getBrand());
        dto.setModel(savedVehicle.getModel());
        dto.setUserId(savedVehicle.getUserId());  // adjust type if needed
        dto.setParked(savedVehicle.isParked());

        return dto;
    }

    public List<VehicleDTO> getVehiclesByUserId(UUID userId) {
        List<Vehicle> vehicles = vehicleRepository.findByUserId(userId);

        List<VehicleDTO> vehicleDTOs = vehicles.stream().map(vehicle -> {
            VehicleDTO dto = new VehicleDTO();
            dto.setId(vehicle.getId());
            dto.setLicensePlate(vehicle.getLicensePlate());
            dto.setBrand(vehicle.getBrand());
            dto.setModel(vehicle.getModel());
            dto.setUserId(vehicle.getUserId()); // assuming userId is Long or UUID, adjust type accordingly
            dto.setParked(vehicle.isParked());
            return dto;
        }).toList();

        return vehicleDTOs;
    }

    public String simulateEntry(UUID vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() ->
                new RuntimeException("Vehicle not found"));
        vehicle.setParked(true);
        vehicleRepository.save(vehicle);
        return "Vehicle " + vehicleId + " has entered the parking.";
    }

    public String simulateExit(UUID vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() ->
                new RuntimeException("Vehicle not found"));
        vehicle.setParked(false);
        vehicleRepository.save(vehicle);
        return "Vehicle " + vehicleId + " has exited the parking.";
    }

    public VehicleDTO updateVehicle(UUID id, Vehicle updated) {
        Vehicle existing = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        existing.setLicensePlate(updated.getLicensePlate());
        existing.setBrand(updated.getBrand());
        existing.setModel(updated.getModel());
        existing.setUserId(updated.getUserId());

        Vehicle saved = vehicleRepository.save(existing);

        VehicleDTO dto = new VehicleDTO();
        dto.setId(saved.getId());
        dto.setLicensePlate(saved.getLicensePlate());
        dto.setBrand(saved.getBrand());
        dto.setModel(saved.getModel());
        dto.setUserId(saved.getUserId());
        dto.setParked(saved.isParked());

        return dto;
    }

    public String deleteVehicle(UUID id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        vehicleRepository.delete(vehicle);
        return "Vehicle with ID " + id + " has been deleted.";
    }
}
