package com.rental.service;

import com.rental.entity.Vehicle;
import com.rental.repository.VehicleRepository;
import com.rental.service.exception.VehicleNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * The type Vehicle service.
 */
@Service
public class VehicleService {

  private final VehicleRepository vehicleRepository;

  /**
   * Instantiates a new Vehicle service.
   *
   * @param vehicleRepository the vehicle repository
   */
  @Autowired
  public VehicleService(VehicleRepository vehicleRepository) {
    this.vehicleRepository = vehicleRepository;
  }

  /**
   * Gets vehicle by id.
   *
   * @param id the id
   * @return the vehicle by id
   * @throws VehicleNotFoundException the vehicle not found exception
   */
  public Vehicle getVehicleById(UUID id) throws VehicleNotFoundException {
    return vehicleRepository.findById(id)
        .orElseThrow(VehicleNotFoundException::new);
  }

  /**
   * Gets all vehicles.
   *
   * @param pageNumber the page number
   * @param pageSize   the page size
   * @return the all vehicles
   */
  public List<Vehicle> getAllVehicles(int pageNumber, int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<Vehicle> page = vehicleRepository.findAll(pageable);

    return page.toList();
  }

  /**
   * Create vehicle.
   *
   * @param vehicle the vehicle creation
   * @return the vehicle
   */
  @Transactional
  public Vehicle createVehicle(Vehicle vehicle) {
    return vehicleRepository.save(vehicle);
  }

  /**
   * Update vehicle.
   *
   * @param vehicle the vehicle creation
   * @param id the id
   * @return the vehicle
   * @throws VehicleNotFoundException the vehicle not found exception
   */
  @Transactional
  public Vehicle updateVehicle(Vehicle vehicle, UUID id) throws VehicleNotFoundException {
    Vehicle vehicleFromDb = getVehicleById(id);

    vehicleFromDb.setModel(vehicle.getModel());
    vehicleFromDb.setLicensePlate(vehicle.getLicensePlate());
    vehicleFromDb.setBrand(vehicle.getBrand());
    vehicleFromDb.setColor(vehicle.getColor());
    vehicleFromDb.setYearOfManufacture(vehicle.getYearOfManufacture());

    return vehicleRepository.save(vehicleFromDb);
  }

  /**
   * Delete vehicle.
   *
   * @param id the id
   * @return the vehicle
   * @throws VehicleNotFoundException the vehicle not found exception
   */
  @Transactional
  public Vehicle deleteVehicle(UUID id) throws VehicleNotFoundException {
    Vehicle vehicle = getVehicleById(id);

    vehicleRepository.delete(vehicle);
    return vehicle;
  }
}
