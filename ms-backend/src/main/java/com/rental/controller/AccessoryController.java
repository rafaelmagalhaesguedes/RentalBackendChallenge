package com.rental.controller;

import com.rental.controller.dto.accessory.AccessoryCreationDto;
import com.rental.controller.dto.accessory.AccessoryDto;
import com.rental.entity.Accessory;
import com.rental.service.AccessoryService;
import com.rental.service.exception.AccessoryNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Caching;
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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;


/**
 * REST controller for managing accessories.
 */
@RestController
@RequestMapping("/accessory")
@Validated
public class AccessoryController {

  private final AccessoryService accessoryService;

  @Autowired
  public AccessoryController(AccessoryService accessoryService) {
    this.accessoryService = accessoryService;
  }

  /**
   * Gets accessory by id.
   *
   * @param id the id
   * @return the accessory by id
   * @throws AccessoryNotFoundException the accessory not found exception
   */
  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('MANAGER')")
  @Operation(summary = "Get accessory by ID", description = "Retrieve a specific accessory by its ID")
  @ApiResponse(responseCode = "200", description = "Accessory successfully retrieved")
  @ApiResponse(responseCode = "404", description = "Accessory not found")
  @Cacheable(value = "accessoryCache", key = "#id")
  public AccessoryDto getAccessoryById(@PathVariable UUID id) throws AccessoryNotFoundException {
    return AccessoryDto.fromEntity(
        accessoryService.getAccessoryById(id)
    );
  }

  /**
   * Gets all accessories with pagination.
   *
   * @param pageNumber the page number to retrieve (default is 0)
   * @param pageSize the number of items per page (default is 10)
   * @return a list of accessory DTOs
   */
  @GetMapping
  @Operation(summary = "List all accessories", description = "List all accessories with pagination")
  @ApiResponse(responseCode = "200", description = "List of accessories successfully retrieved")
  @Cacheable(value = "accessoriesCache")
  public List<AccessoryDto> getAllAccessories(
      @RequestParam(required = false, defaultValue = "0") int pageNumber,
      @RequestParam(required = false, defaultValue = "20") int pageSize) {
    List<Accessory> paginatedAccessories = accessoryService.getAllAccessories(pageNumber, pageSize);

    return paginatedAccessories.stream()
        .map(AccessoryDto::fromEntity)
        .toList();
  }

  /**
   * Creates a new accessory.
   *
   * @param accessoryCreationDto the DTO containing the accessory creation data
   * @return the created accessory DTO
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('MANAGER')")
  @Operation(summary = "Create accessory", description = "Create a new accessory")
  @ApiResponse(responseCode = "201", description = "Accessory successfully created")
  @CacheEvict(value = "accessoriesCache", allEntries = true)
  public AccessoryDto createAccessory(@RequestBody @Valid AccessoryCreationDto accessoryCreationDto) {
    return AccessoryDto.fromEntity(
        accessoryService.createAccessory(accessoryCreationDto.toEntity())
    );
  }

  /**
   * Updates an existing accessory.
   *
   * @param accessoryCreationDto the DTO containing the updated accessory data
   * @param id the UUID of the accessory to update
   * @return the updated accessory DTO
   * @throws AccessoryNotFoundException if the accessory with the given ID is not found
   */
  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('MANAGER')")
  @Operation(summary = "Update accessory", description = "Update an existing accessory")
  @ApiResponse(responseCode = "200", description = "Accessory successfully updated")
  @ApiResponse(responseCode = "404", description = "Accessory not found")
  @Caching(evict = {
      @CacheEvict(value = "accessoryCache", key = "#id"),
      @CacheEvict(value = "accessoriesCache", allEntries = true)
  })
  public AccessoryDto updateAccessory(@Valid @RequestBody AccessoryCreationDto accessoryCreationDto, @PathVariable UUID id) throws AccessoryNotFoundException {
    return AccessoryDto.fromEntity(
        accessoryService.updateAccessory(accessoryCreationDto.toEntity(), id)
    );
  }

  /**
   * Deletes an accessory by its ID.
   *
   * @param id the UUID of the accessory to be deleted.
   * @return the deleted AccessoryDto.
   * @throws AccessoryNotFoundException if no accessory with the given ID is found.
   */
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('MANAGER')")
  @Operation(summary = "Delete accessory", description = "Delete an accessory by its ID")
  @ApiResponse(responseCode = "200", description = "Accessory successfully deleted")
  @ApiResponse(responseCode = "404", description = "Accessory not found")
  @Caching(evict = {
      @CacheEvict(value = "accessoryCache", key = "#id"),
      @CacheEvict(value = "accessoriesCache", allEntries = true)
  })
  public AccessoryDto deleteAccessory(@PathVariable UUID id) throws AccessoryNotFoundException {
    return AccessoryDto.fromEntity(
        accessoryService.deleteAccessory(id)
    );
  }
}
