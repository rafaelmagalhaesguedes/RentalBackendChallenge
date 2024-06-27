package com.rental;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
