package com.rental.unit;

import static com.rental.mock.AccessoryMock.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rental.entity.Accessory;
import com.rental.repository.AccessoryRepository;
import com.rental.service.AccessoryService;
import com.rental.service.exception.AccessoryNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

@ExtendWith(MockitoExtension.class)
public class AccessoryServiceTest {

  @InjectMocks
  AccessoryService accessoryService;

  @Mock
  AccessoryRepository accessoryRepository;

  @Test
  public void testAccessoryRetrievalById() throws AccessoryNotFoundException {
    // Arrange
   when(accessoryRepository.findById(eq(ACCESSORY_WITH_ID_01.getId())))
        .thenReturn(Optional.of(ACCESSORY_WITH_ID_01));

   // Act
   Accessory getAccessory = accessoryService.getAccessoryById(ACCESSORY_WITH_ID_01.getId());

    // Assert
    assertThat(getAccessory).isEqualTo(ACCESSORY_WITH_ID_01);
  }

  @Test
  public void testGetAllAccessories() {
    // Arrange
    List<Accessory> accessories = Arrays.asList(ACCESSORY_WITH_ID_01, ACCESSORY_WITH_ID_02);
    Page<Accessory> page = new PageImpl<>(accessories);
    Pageable pageable = PageRequest.of(0, 2);

    // Act
    when(accessoryRepository.findAll(pageable)).thenReturn(page);
    List<Accessory> getAllAccessories = accessoryService.getAllAccessories(0, 2);
    verify(accessoryRepository).findAll(pageable);

    // Assert
    assertEquals(2, getAllAccessories.size());
    assertThat(getAllAccessories).isEqualTo(accessories);
  }

  @Test
  public void testCreateAccessory() {
    // Arrange
    when(accessoryRepository.save(ACCESSORY_CREATION)).thenReturn(ACCESSORY_CREATION);

    // Act
    Accessory newAccessory = accessoryService.createAccessory(ACCESSORY_CREATION);
    verify(accessoryRepository).save(ACCESSORY_CREATION);

    // Assert
    assertThat(newAccessory).isEqualTo(ACCESSORY_CREATION);
  }

  @Test
  public void testUpdateAccessory() throws AccessoryNotFoundException {

    // Arrange
    when(accessoryRepository.findById(ACCESSORY_WITH_ID_01.getId()))
            .thenReturn(Optional.of(ACCESSORY_WITH_ID_01));

    when(accessoryRepository.save(ACCESSORY_WITH_ID_01))
            .thenReturn(ACCESSORY_WITH_ID_01);

    // Act
    Accessory result = accessoryService.updateAccessory(ACCESSORY_UPDATED, ACCESSORY_WITH_ID_01.getId());

    // Assert
    assertThat(result).isEqualTo(ACCESSORY_WITH_ID_01);
  }

  @Test
  public void testUpdateAccessoryNotFoundException() {
    // Arrange
    when(accessoryRepository.findById(ACCESSORY_WITH_ID_01.getId()))
            .thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(AccessoryNotFoundException.class,
        () -> accessoryService.updateAccessory(ACCESSORY_UPDATED, ACCESSORY_WITH_ID_01.getId()));
  }

  @Test
  public void testDeleteAccessory() throws AccessoryNotFoundException {
    // Arrange
    when(accessoryRepository.findById(ACCESSORY_WITH_ID_01.getId()))
            .thenReturn(Optional.of(ACCESSORY_WITH_ID_01));

    // Act
    Accessory result = accessoryService.deleteAccessory(ACCESSORY_WITH_ID_01.getId());

    // Assert
    assertEquals(ACCESSORY_WITH_ID_01, result);
    verify(accessoryRepository).delete(ACCESSORY_WITH_ID_01);
  }

  @Test
  public void testDeleteAccessoryNotFoundException() {
    // Arrange
    UUID id = UUID.randomUUID();

    when(accessoryRepository.findById(id)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(AccessoryNotFoundException.class,
        () -> accessoryService.deleteAccessory(id));
  }
}
