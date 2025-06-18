package lk.ijse.vehicle_service.controller;

import lk.ijse.vehicle_service.dto.VehicleDTO;
import lk.ijse.vehicle_service.entity.Vehicle;
import lk.ijse.vehicle_service.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/vehicle")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('USER','OWNER')")
    public VehicleDTO addVehicle(@RequestBody Vehicle vehicle) {
        return vehicleService.addVehicle(vehicle);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    public List<VehicleDTO> getVehiclesByUserId(@PathVariable String userId) {
        return vehicleService.getVehiclesByUserId(UUID.fromString(userId));
    }

    @PostMapping("/entry")
    @PreAuthorize("hasAnyRole('USER','OWNER')")
    public String simulateEntry(@RequestParam String vehicleId) {
        return vehicleService.simulateEntry(UUID.fromString(vehicleId));
    }

    @PostMapping("/exit")
    @PreAuthorize("hasAnyRole('USER','OWNER')")
    public String simulateExit(@RequestParam String vehicleId) {
        return vehicleService.simulateExit(UUID.fromString(vehicleId));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('USER','OWNER')")
    public VehicleDTO updateVehicle(@PathVariable String id, @RequestBody Vehicle updatedVehicle) {
        return vehicleService.updateVehicle(UUID.fromString(id), updatedVehicle);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('USER','OWNER','ADMIN')")
    public String deleteVehicle(@PathVariable String id) {
        return vehicleService.deleteVehicle(UUID.fromString(id));
    }
}
