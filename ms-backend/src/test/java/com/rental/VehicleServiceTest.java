package com.rental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;

import com.rental.entity.Vehicle;
import com.rental.repository.VehicleRepository;
import com.rental.service.VehicleService;
import com.rental.service.exception.VehicleNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

  @Test
  public void testVehicleRetrievalById() throws VehicleNotFoundException {

    // Arrange
    UUID id = UUID.randomUUID();

    Vehicle vehicle = new Vehicle();
    vehicle.setId(id);
    vehicle.setModel("Renegade");
    vehicle.setBrand("Jeep");
    vehicle.setColor("Black Carbon");
    vehicle.setLicensePlate("RJP-4405");
    vehicle.setYearOfManufacture("2022/2023");

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
    UUID vehicle1Id = UUID.randomUUID();
    UUID vehicle2Id = UUID.randomUUID();

    Vehicle vehicle1 = new Vehicle();
    vehicle1.setId(vehicle1Id);
    vehicle1.setModel("Renegade");
    vehicle1.setBrand("Jeep");
    vehicle1.setColor("Black Carbon");
    vehicle1.setLicensePlate("RJP-4405");
    vehicle1.setYearOfManufacture("2022/2023");

    Vehicle vehicle2 = new Vehicle();
    vehicle2.setId(vehicle2Id);
    vehicle2.setModel("Creta");
    vehicle2.setBrand("Hyundai");
    vehicle2.setColor("Cinza");
    vehicle2.setLicensePlate("RJT-4402");
    vehicle2.setYearOfManufacture("2022/2023");

    List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);

    Page<Vehicle> page = new PageImpl<>(vehicles);
    Pageable pageable = PageRequest.of(0, 2);

    // Act
    Mockito.when(repository.findAll(pageable)).thenReturn(page);

    List<Vehicle> getAllVehicles = service.getAllVehicles(0, 2);

    Mockito.verify(repository).findAll(pageable);

    // Assert
    assertEquals(2, getAllVehicles.size());
    assertEquals(vehicle1.getId(), vehicles.get(0).getId());
    assertEquals(vehicle1.getModel(), vehicles.get(0).getModel());
    assertEquals(vehicle1.getBrand(), vehicles.get(0).getBrand());
    assertEquals(vehicle1.getColor(), vehicles.get(0).getColor());
    assertEquals(vehicle2.getLicensePlate(), vehicles.get(1).getLicensePlate());
    assertEquals(vehicle1.getYearOfManufacture(), vehicles.get(0).getYearOfManufacture());

    assertEquals(vehicle2.getId(), vehicles.get(1).getId());
    assertEquals(vehicle2.getModel(), vehicles.get(1).getModel());
    assertEquals(vehicle2.getBrand(), vehicles.get(1).getBrand());
    assertEquals(vehicle2.getColor(), vehicles.get(1).getColor());
    assertEquals(vehicle2.getLicensePlate(), vehicles.get(1).getLicensePlate());
    assertEquals(vehicle2.getYearOfManufacture(), vehicles.get(1).getYearOfManufacture());
  }

  @Test
  public void testCreateVehicle() {

    // Arrange
    UUID id = UUID.randomUUID();

    Vehicle vehicle = new Vehicle();
    vehicle.setId(id);
    vehicle.setModel("Renegade");
    vehicle.setBrand("Jeep");
    vehicle.setColor("Black Carbon");
    vehicle.setLicensePlate("RJP-4405");
    vehicle.setYearOfManufacture("2022/2023");

    // Act
    Mockito.when(repository.save(vehicle)).thenReturn(vehicle);

    Vehicle newVehicle = service.createVehicle(vehicle);

    Mockito.verify(repository).save(newVehicle);

    // Assert
    assertEquals(newVehicle.getId(), vehicle.getId());
    assertEquals(newVehicle.getModel(), vehicle.getModel());
    assertEquals(newVehicle.getBrand(), vehicle.getBrand());
    assertEquals(newVehicle.getColor(), vehicle.getColor());
    assertEquals(newVehicle.getLicensePlate(), vehicle.getLicensePlate());
    assertEquals(newVehicle.getYearOfManufacture(), vehicle.getYearOfManufacture());
  }

  @Test
  public void testUpdateVehicle() throws VehicleNotFoundException {

    // Arrange
    UUID id = UUID.randomUUID();

    Vehicle vehicleExist = new Vehicle();
    vehicleExist.setId(id);
    vehicleExist.setModel("Renegade");
    vehicleExist.setBrand("Jeep");
    vehicleExist.setColor("Black Carbon");
    vehicleExist.setLicensePlate("RJP-4405");
    vehicleExist.setYearOfManufacture("2022/2023");

    Vehicle vehicleUpdated = new Vehicle();
    vehicleUpdated.setId(id);
    vehicleUpdated.setModel("Renegade");
    vehicleUpdated.setBrand("Jeep");
    vehicleUpdated.setColor("Blue");
    vehicleUpdated.setLicensePlate("RJP-4405");
    vehicleUpdated.setYearOfManufacture("2022/2023");

    Mockito.when(repository.findById(id)).thenReturn(Optional.of(vehicleExist));
    Mockito.when(repository.save(vehicleExist)).thenReturn(vehicleExist);

    // Act
    Vehicle result = service.updateVehicle(vehicleUpdated, id);

    // Assert
    assertEquals(vehicleUpdated.getId(), result.getId());
    assertEquals(vehicleUpdated.getModel(), result.getModel());
    assertEquals(vehicleUpdated.getBrand(), result.getBrand());
    assertEquals(vehicleUpdated.getColor(), result.getColor());
    assertEquals(vehicleUpdated.getLicensePlate(), result.getLicensePlate());
    assertEquals(vehicleUpdated.getYearOfManufacture(), result.getYearOfManufacture());
  }

  @Test
  public void testUpdateVehicleNotFoundException() {

    // Arrange
    UUID id = UUID.randomUUID();

    Vehicle vehicleExist = new Vehicle();
    vehicleExist.setId(id);
    vehicleExist.setModel("Renegade");
    vehicleExist.setBrand("Jeep");
    vehicleExist.setColor("Black Carbon");
    vehicleExist.setLicensePlate("RJP-4405");
    vehicleExist.setYearOfManufacture("2022/2023");

    Mockito.when(repository.findById(id))
        .thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(VehicleNotFoundException.class,
        () -> service.updateVehicle(vehicleExist, id));
  }

  @Test
  public void testDeleteVehicle() throws VehicleNotFoundException {

    // Arrange
    UUID id = UUID.randomUUID();

    Vehicle vehicleExist = new Vehicle();
    vehicleExist.setId(id);
    vehicleExist.setModel("Renegade");
    vehicleExist.setBrand("Jeep");
    vehicleExist.setColor("Black Carbon");
    vehicleExist.setLicensePlate("RJP-4405");
    vehicleExist.setYearOfManufacture("2022/2023");

    Mockito.when(repository.findById(id))
        .thenReturn(Optional.of(vehicleExist));

    // Act
    Vehicle result = service.deleteVehicle(id);

    // Assert
    assertEquals(vehicleExist, result);
    Mockito.verify(repository).delete(vehicleExist);
  }

  @Test
  public void testVehicleNotFoundException() {

    // Arrange
    UUID id = UUID.randomUUID();

    Mockito.when(repository.findById(id))
        .thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(VehicleNotFoundException.class,
        () -> service.deleteVehicle(id));
  }
}
