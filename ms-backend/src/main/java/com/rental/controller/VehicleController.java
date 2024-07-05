package com.rental.controller;

import com.rental.controller.dto.vehicle.VehicleCreationDto;
import com.rental.controller.dto.vehicle.VehicleDto;
import com.rental.entity.Vehicle;
import com.rental.service.VehicleService;
import com.rental.service.exception.VehicleNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
  @Operation(summary = "Get Vehicle by ID", description = "Fetch a vehicle by its ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Vehicle fetched successfully"),
      @ApiResponse(responseCode = "404", description = "Vehicle not found")
  })
  @Cacheable(value = "vehicleById", key = "#id")
  public VehicleDto getVehicleById(@PathVariable UUID id) throws VehicleNotFoundException {
    return VehicleDto.fromEntity(vehicleService.getVehicleById(id));
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
  @Operation(summary = "Get All Vehicles", description = "Fetch all vehicles with pagination support.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of vehicles fetched successfully")
  })
  @Cacheable(value = "allVehicles", key = "#pageNumber + '-' + #pageSize")
  public List<VehicleDto> getAllVehicles(
      @RequestParam(required = false, defaultValue = "0") int pageNumber,
      @RequestParam(required = false, defaultValue = "20") int pageSize) {
    List<Vehicle> paginatedVehicles = vehicleService.getAllVehicles(pageNumber, pageSize);
    return paginatedVehicles.stream().map(VehicleDto::fromEntity).toList();
  }

  /**
   * Create vehicle.
   *
   * @param vehicleCreationDto the vehicle creation dto
   * @return the vehicle dto
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('MANAGER')")
  @Operation(summary = "Create Vehicle", description = "Create a new vehicle.")
  @ApiResponse(responseCode = "201", description = "Vehicle created successfully")
  @CacheEvict(value = {"vehicleById", "allVehicles"}, allEntries = true)
  public VehicleDto createVehicle(@RequestBody @Valid VehicleCreationDto vehicleCreationDto) {
    return VehicleDto.fromEntity(
            vehicleService.createVehicle(vehicleCreationDto.toEntity())
    );
  }

  /**
   * Update vehicle.
   *
   * @param vehicleCreationDto the vehicle creation dto
   * @param id                 the id
   * @return the vehicle dto
   * @throws VehicleNotFoundException the vehicle not found exception
   */
  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('MANAGER')")
  @Operation(summary = "Update Vehicle", description = "Update an existing vehicle.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Vehicle updated successfully"),
      @ApiResponse(responseCode = "404", description = "Vehicle not found")
  })
  @CacheEvict(value = {"vehicleById", "allVehicles"}, allEntries = true)
  public VehicleDto updateVehicle(@RequestBody @Valid VehicleCreationDto vehicleCreationDto, @PathVariable UUID id) throws VehicleNotFoundException {
    return VehicleDto.fromEntity(
            vehicleService.updateVehicle(vehicleCreationDto.toEntity(), id)
    );
  }

  /**
   * Delete vehicle.
   *
   * @param id the id
   * @return the vehicle dto
   * @throws VehicleNotFoundException the vehicle not found exception
   */
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('MANAGER')")
  @Operation(summary = "Delete Vehicle", description = "Delete a vehicle by its ID.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Vehicle deleted successfully"),
      @ApiResponse(responseCode = "404", description = "Vehicle not found")
  })
  @CacheEvict(value = {"vehicleById", "allVehicles"}, allEntries = true)
  public VehicleDto deleteVehicle(@PathVariable UUID id) throws VehicleNotFoundException {
    return VehicleDto.fromEntity(vehicleService.deleteVehicle(id));
  }
}
