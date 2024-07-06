package com.rental.unit;

import static com.rental.mock.VehicleMock.*;
import static org.mockito.ArgumentMatchers.eq;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rental.entity.Vehicle;
import com.rental.repository.VehicleRepository;
import com.rental.service.VehicleService;
import com.rental.service.exception.GroupNotFoundException;
import com.rental.service.exception.VehicleNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Unit Tests to Vehicle Service Class
 * *
 * Created by Rafa Guedes
 * */
@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

  @InjectMocks
  VehicleService service;

  @Mock
  VehicleRepository repository;

  @Test
  public void testVehicleRetrievalById() throws VehicleNotFoundException {
    // Arrange
    when(repository.findById(eq(VEHICLE_01.getId()))).thenReturn(Optional.of(VEHICLE_01));

    // Act
    Vehicle vehicleFromDb = service.getVehicleById(VEHICLE_01.getId());
    verify(repository).findById(VEHICLE_01.getId());

    // Assert
    assertThat(vehicleFromDb).isEqualTo(VEHICLE_01);
  }

  @Test
  public void testVehicleRetrievalByLicensePlate() throws VehicleNotFoundException {
    // Arrange
    when(repository.findByLicensePlate(eq(VEHICLE_01.getLicensePlate()))).thenReturn(VEHICLE_01);

    // Act
    Vehicle vehicleFromDb = service.getVehicleByLicensePlate(VEHICLE_01.getLicensePlate());

    // Assert
    assertThat(vehicleFromDb).isEqualTo(VEHICLE_01);
  }

  @Test
  public void testRetrievalAllVehicles() {
    // Arrange
    List<Vehicle> vehicles = Arrays.asList(VEHICLE_01, VEHICLE_02);
    Page<Vehicle> page = new PageImpl<>(vehicles);
    Pageable pageable = PageRequest.of(0, 2);

    // Act
    when(repository.findAll(pageable)).thenReturn(page);
    List<Vehicle> getAllVehicles = service.getAllVehicles(0, 2);
    verify(repository).findAll(pageable);

    // Assert
    assertThat(getAllVehicles).isNotEmpty();
    assertThat(getAllVehicles).hasSize(2);
    assertThat(getAllVehicles.get(0)).isEqualTo(VEHICLE_01);
    assertThat(getAllVehicles.get(1)).isEqualTo(VEHICLE_02);
  }

  @Test
  public void testCreateVehicle() throws VehicleNotFoundException {
    // Arrange
    Mockito.when(repository.save(VEHICLE_01)).thenReturn(VEHICLE_01);

    // Act
    Vehicle createdVehicle = service.createVehicle(VEHICLE_01);

    // Assert
    assertThat(createdVehicle).isEqualTo(VEHICLE_01);
  }

  @Test
  public void testUpdateVehicle() throws VehicleNotFoundException, GroupNotFoundException {
    // Arrange
    when(repository.findById(eq(VEHICLE_01.getId()))).thenReturn(Optional.of(VEHICLE_01));
    when(repository.save(VEHICLE_01)).thenReturn(VEHICLE_01);

    // Act
    Vehicle vehicleFromDb = service.updateVehicle(VEHICLE_UPDATED, VEHICLE_01.getId());

    // Assert
    assertThat(vehicleFromDb).isEqualTo(VEHICLE_01);
  }

  @Test
  public void testDeleteVehicle() throws VehicleNotFoundException {
    // Act
    Mockito.when(repository.findById(eq(VEHICLE_01.getId())))
            .thenReturn(Optional.of(VEHICLE_01));

    Vehicle deletedVehicle = service.deleteVehicle(VEHICLE_01.getId());

    // Assert
    assertThat(deletedVehicle).isEqualTo(VEHICLE_01);
  }
}
