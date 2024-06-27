package com.rental.controller;

import com.rental.controller.dto.vehicle.VehicleCreationDto;
import com.rental.controller.dto.vehicle.VehicleDto;
import com.rental.entity.Vehicle;
import com.rental.service.VehicleService;
import com.rental.service.exception.VehicleNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Vehicle controller.
 */
@RestController
@RequestMapping("/vehicles")
@Validated
public class VehicleController {

  private final VehicleService vehicleService;

  /**
   * Instantiates a new Vehicle controller.
   *
   * @param vehicleService the vehicle service
   */
  @Autowired
  public VehicleController(VehicleService vehicleService) {
    this.vehicleService = vehicleService;
  }

  /**
   * Gets vehicle by id.
   *
   * @param id the id
   * @return the vehicle by id
   * @throws VehicleNotFoundException the vehicle not found exception
   */
  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('MANAGER')")
  public VehicleDto getVehicleById(@PathVariable UUID id) throws VehicleNotFoundException {
    return VehicleDto.fromEntity(
        vehicleService.getVehicleById(id)
    );
  }

  /**
   * Gets all vehicles.
   *
   * @param pageNumber the page number
   * @param pageSize   the page size
   * @return the all vehicles
   */
  @GetMapping
  @PreAuthorize("hasAuthority('MANAGER')")
  public List<VehicleDto> getAllVehicles(
      @RequestParam(required = false, defaultValue = "0") int pageNumber,
      @RequestParam(required = false, defaultValue = "20") int pageSize
  ) {
    List<Vehicle> paginatedVehicles = vehicleService.getAllVehicles(pageNumber, pageSize);
    return paginatedVehicles.stream()
        .map(VehicleDto::fromEntity)
        .toList();
  }

  /**
   * Create vehicle dto.
   *
   * @param vehicleCreationDto the vehicle creation dto
   * @return the vehicle dto
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('MANAGER')")
  public VehicleDto createVehicle(@RequestBody @Valid VehicleCreationDto vehicleCreationDto) {
    return VehicleDto.fromEntity(
        vehicleService.createVehicle(vehicleCreationDto.toEntity())
    );
  }

  /**
   * Update vehicle dto.
   *
   * @param vehicleCreationDto the vehicle creation dto
   * @param id the id
   * @return the vehicle dto
   * @throws VehicleNotFoundException the vehicle not found exception
   */
  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('MANAGER')")
  public VehicleDto updateVehicle(@RequestBody @Valid VehicleCreationDto vehicleCreationDto, @PathVariable UUID id) throws VehicleNotFoundException {
    return VehicleDto.fromEntity(
        vehicleService.updateVehicle(vehicleCreationDto.toEntity(), id)
    );
  }

  /**
   * Delete vehicle dto.
   *
   * @param id the id
   * @return the vehicle dto
   * @throws VehicleNotFoundException the vehicle not found exception
   */
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('MANAGER')")
  public VehicleDto deleteVehicle(@PathVariable UUID id) throws VehicleNotFoundException {
    return VehicleDto.fromEntity(
        vehicleService.deleteVehicle(id)
    );
  }
}
