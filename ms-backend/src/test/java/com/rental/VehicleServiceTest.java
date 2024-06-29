package com.rental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;

import com.rental.controller.dto.vehicle.VehicleCreationDto;
import com.rental.controller.dto.vehicle.VehicleDto;
import com.rental.entity.Vehicle;
import com.rental.repository.VehicleRepository;
import com.rental.service.VehicleService;
import com.rental.service.exception.GroupNotFoundException;
import com.rental.service.exception.VehicleNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class VehicleServiceTest {

  @Autowired
  VehicleService service;

  @MockBean
  VehicleRepository repository;

  private UUID id;
  private Vehicle vehicle;
  private VehicleCreationDto vehicleCreationDto;

  @BeforeEach
  public void setup() {
    id = UUID.randomUUID();

    vehicle = new Vehicle();
    vehicle.setId(id);
    vehicle.setModel("Renegade");
    vehicle.setBrand("Jeep");
    vehicle.setColor("Black Carbon");
    vehicle.setLicensePlate("RJP-4405");
    vehicle.setYearOfManufacture("2022/2023");

    vehicleCreationDto = new VehicleCreationDto(
        "Renegade",
        "Jeep",
        "Black Carbon",
        "RJP-4405",
        "2022/2023"
    );
  }

  @Test
  public void testVehicleRetrievalById() throws VehicleNotFoundException {

    // Act
    Mockito.when(repository.findById(eq(id)))
        .thenReturn(Optional.of(vehicle));

    Vehicle vehicleFromDb = service.getVehicleById(id);

    Mockito.verify(repository).findById(id);

    // Assert
    assertEquals(vehicleFromDb.getId(), vehicle.getId());
    assertEquals(vehicleFromDb.getModel(), vehicle.getModel());
    assertEquals(vehicleFromDb.getLicensePlate(), vehicle.getLicensePlate());
    assertEquals(vehicleFromDb.getBrand(), vehicle.getBrand());
    assertEquals(vehicleFromDb.getYearOfManufacture(), vehicle.getYearOfManufacture());
  }

  @Test
  public void testRetrievalAllVehicles() {

    // Arrange
    Vehicle vehicle2 = new Vehicle();
    vehicle2.setId(UUID.randomUUID());
    vehicle2.setModel("Creta");
    vehicle2.setBrand("Hyundai");
    vehicle2.setColor("Cinza");
    vehicle2.setLicensePlate("RJT-4402");
    vehicle2.setYearOfManufacture("2022/2023");

    List<Vehicle> vehicles = Arrays.asList(vehicle, vehicle2);

    Page<Vehicle> page = new PageImpl<>(vehicles);
    Pageable pageable = PageRequest.of(0, 2);

    // Act
    Mockito.when(repository.findAll(pageable)).thenReturn(page);

    List<Vehicle> getAllVehicles = service.getAllVehicles(0, 2);

    Mockito.verify(repository).findAll(pageable);

    // Assert
    assertEquals(2, getAllVehicles.size());
    assertEquals(vehicle.getId(), vehicles.get(0).getId());
    assertEquals(vehicle.getModel(), vehicles.get(0).getModel());
    assertEquals(vehicle.getBrand(), vehicles.get(0).getBrand());
    assertEquals(vehicle.getColor(), vehicles.get(0).getColor());
    assertEquals(vehicle.getLicensePlate(), vehicles.get(0).getLicensePlate());
    assertEquals(vehicle.getYearOfManufacture(), vehicles.get(0).getYearOfManufacture());

    assertEquals(vehicle2.getId(), vehicles.get(1).getId());
    assertEquals(vehicle2.getModel(), vehicles.get(1).getModel());
    assertEquals(vehicle2.getBrand(), vehicles.get(1).getBrand());
    assertEquals(vehicle2.getColor(), vehicles.get(1).getColor());
    assertEquals(vehicle2.getLicensePlate(), vehicles.get(1).getLicensePlate());
    assertEquals(vehicle2.getYearOfManufacture(), vehicles.get(1).getYearOfManufacture());
  }

  @Test
  public void testCreateVehicle() throws GroupNotFoundException {

    // Act
    Mockito.when(repository.save(Mockito.any(Vehicle.class))).thenReturn(vehicle);

    VehicleDto createdVehicle = service.createVehicle(vehicleCreationDto);

    // Assert
    assertEquals(vehicle.getId(), createdVehicle.id());
    assertEquals(vehicle.getModel(), createdVehicle.model());
    assertEquals(vehicle.getBrand(), createdVehicle.brand());
    assertEquals(vehicle.getColor(), createdVehicle.color());
    assertEquals(vehicle.getLicensePlate(), createdVehicle.licensePlate());
    assertEquals(vehicle.getYearOfManufacture(), createdVehicle.yearOfManufacture());

    Mockito.verify(repository).save(Mockito.any(Vehicle.class));
  }

  @Test
  public void testUpdateVehicle() throws VehicleNotFoundException, GroupNotFoundException {

    // Arrange
    Vehicle updatedVehicle = new Vehicle();
    updatedVehicle.setId(id);
    updatedVehicle.setModel("Renegade Updated");
    updatedVehicle.setBrand("Jeep");
    updatedVehicle.setColor("Black Carbon");
    updatedVehicle.setLicensePlate("RJP-4406");
    updatedVehicle.setYearOfManufacture("2023");

    VehicleCreationDto vehicleUpdateDto = new VehicleCreationDto(
        "Renegade Updated",
        "Jeep",
        "Black Carbon",
        "RJP-4406",
        "2023"
    );

    // Act
    Mockito.when(repository.findById(eq(id))).thenReturn(Optional.of(vehicle));
    Mockito.when(repository.save(Mockito.any(Vehicle.class))).thenReturn(updatedVehicle);

    VehicleDto vehicleFromDb = service.updateVehicle(vehicleUpdateDto, id);

    // Assert
    assertEquals(updatedVehicle.getId(), vehicleFromDb.id());
    assertEquals(updatedVehicle.getModel(), vehicleFromDb.model());
    assertEquals(updatedVehicle.getBrand(), vehicleFromDb.brand());
    assertEquals(updatedVehicle.getColor(), vehicleFromDb.color());
    assertEquals(updatedVehicle.getLicensePlate(), vehicleFromDb.licensePlate());
    assertEquals(updatedVehicle.getYearOfManufacture(), vehicleFromDb.yearOfManufacture());

    Mockito.verify(repository).findById(id);
    Mockito.verify(repository).save(Mockito.any(Vehicle.class));
  }

  @Test
  public void testDeleteVehicle() throws VehicleNotFoundException {

    // Act
    Mockito.when(repository.findById(eq(id))).thenReturn(Optional.of(vehicle));
    Vehicle deletedVehicle = service.deleteVehicle(id);

    // Assert
    assertEquals(vehicle.getId(), deletedVehicle.getId());
    assertEquals(vehicle.getModel(), deletedVehicle.getModel());
    assertEquals(vehicle.getBrand(), deletedVehicle.getBrand());
    assertEquals(vehicle.getColor(), deletedVehicle.getColor());
    assertEquals(vehicle.getLicensePlate(), deletedVehicle.getLicensePlate());
    assertEquals(vehicle.getYearOfManufacture(), deletedVehicle.getYearOfManufacture());
  }
}
