package com.rental.unit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.rental.entity.Accessory;
import com.rental.repository.AccessoryRepository;
import com.rental.service.AccessoryService;
import com.rental.service.exception.AccessoryNotFoundException;
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
public class AccessoryServiceTest {

  @Autowired
  AccessoryService accessoryService;

  @MockBean
  AccessoryRepository accessoryRepository;

  @Test
  public void testAccessoryRetrievalById() throws AccessoryNotFoundException {

    // Arrange
    UUID accessoryId = UUID.randomUUID();

    Accessory accessory = new Accessory();
    accessory.setId(accessoryId);
    accessory.setName("Baby Comfort");
    accessory.setDailyRate(50.00);
    accessory.setDescription("Baby comfort plus top");
    accessory.setQuantity(10);

    // Act
    Mockito.when(accessoryRepository.findById(eq(accessoryId)))
        .thenReturn(Optional.of(accessory));

    Accessory getAccessory = accessoryService.getAccessoryById(accessoryId);

    Mockito.verify(accessoryRepository).findById(accessoryId);

    // Assert
    assertEquals(getAccessory.getId(), accessory.getId());
    assertEquals(getAccessory.getName(), accessory.getName());
    assertEquals(getAccessory.getDailyRate(), accessory.getDailyRate());
    assertEquals(getAccessory.getDescription(), accessory.getDescription());
    assertEquals(getAccessory.getQuantity(), accessory.getQuantity());
  }

  @Test
  public void testGetAllAccessories() {

    // Arrange
    UUID accessory1Id = UUID.randomUUID();
    UUID accessory2Id = UUID.randomUUID();

    Accessory accessory1 = new Accessory();
    accessory1.setId(accessory1Id);
    accessory1.setName("Baby Comfort");
    accessory1.setDailyRate(50.00);
    accessory1.setDescription("Baby comfort plus top");
    accessory1.setQuantity(10);

    Accessory accessory2 = new Accessory();
    accessory2.setId(accessory2Id);
    accessory2.setName("GPS");
    accessory2.setDailyRate(100.00);
    accessory2.setDescription("GPS top master noob");
    accessory2.setQuantity(5);

    List<Accessory> accessories = Arrays.asList(accessory1, accessory2);

    Page<Accessory> page = new PageImpl<>(accessories);
    Pageable pageable = PageRequest.of(0, 2);

    // Act
    Mockito.when(accessoryRepository.findAll(pageable)).thenReturn(page);

    List<Accessory> getAllAccessories = accessoryService.getAllAccessories(0, 2);

    Mockito.verify(accessoryRepository).findAll(pageable);

    // Assert
    assertEquals(2, getAllAccessories.size());
    assertEquals(accessory1.getId(), accessories.get(0).getId());
    assertEquals(accessory1.getName(), accessories.get(0).getName());
    assertEquals(accessory1.getDailyRate(), accessories.get(0).getDailyRate());
    assertEquals(accessory1.getDescription(), accessories.get(0).getDescription());
    assertEquals(accessory1.getQuantity(), accessories.get(0).getQuantity());

    assertEquals(accessory2.getId(), accessories.get(1).getId());
    assertEquals(accessory2.getName(), accessories.get(1).getName());
    assertEquals(accessory2.getDailyRate(), accessories.get(1).getDailyRate());
    assertEquals(accessory2.getDescription(), accessories.get(1).getDescription());
    assertEquals(accessory2.getQuantity(), accessories.get(1).getQuantity());
  }

  @Test
  public void testCreateAccessory() {

    // Arrange
    Accessory accessory = new Accessory();
    accessory.setId(UUID.randomUUID());
    accessory.setName("Baby Comfort");
    accessory.setDailyRate(20.00);
    accessory.setDescription("Baby comfort plus top");
    accessory.setQuantity(5);

    // Act
    Mockito.when(accessoryRepository.save(accessory)).thenReturn(accessory);

    Accessory newAccessory = accessoryService.createAccessory(accessory);

    Mockito.verify(accessoryRepository).save(accessory);

    // Assert
    assertEquals(newAccessory.getId(), accessory.getId());
    assertEquals(newAccessory.getName(), accessory.getName());
    assertEquals(newAccessory.getDailyRate(), accessory.getDailyRate());
    assertEquals(newAccessory.getDescription(), accessory.getDescription());
    assertEquals(newAccessory.getQuantity(), accessory.getQuantity());
  }

  @Test
  public void testUpdateAccessory() throws AccessoryNotFoundException {

    // Arrange
    UUID id = UUID.randomUUID();
    Accessory existingAccessory = new Accessory();
    existingAccessory.setId(id);
    existingAccessory.setName("Baby");
    existingAccessory.setDailyRate(50.00);
    existingAccessory.setDescription("Baby comfort");
    existingAccessory.setQuantity(5);

    Accessory updatedAccessory = new Accessory();
    updatedAccessory.setName("Baby comfort plus");
    updatedAccessory.setDailyRate(60.00);
    existingAccessory.setDescription("Baby comfort plus pro");
    existingAccessory.setQuantity(10);

    Mockito.when(accessoryRepository.findById(id)).thenReturn(Optional.of(existingAccessory));
    Mockito.when(accessoryRepository.save(existingAccessory)).thenReturn(existingAccessory);

    // Act
    Accessory result = accessoryService.updateAccessory(updatedAccessory, id);

    // Assert
    assertEquals(updatedAccessory.getName(), result.getName());
    assertEquals(updatedAccessory.getDailyRate(), result.getDailyRate());
    assertEquals(updatedAccessory.getDescription(), result.getDescription());
    assertEquals(updatedAccessory.getQuantity(), result.getQuantity());
  }

  @Test
  public void testUpdateAccessoryNotFoundException() {

    // Arrange
    UUID id = UUID.randomUUID();
    Accessory updatedAccessory = new Accessory();
    updatedAccessory.setName("Baby comfort");
    updatedAccessory.setDailyRate(60.00);
    updatedAccessory.setDescription("Baby comfort");
    updatedAccessory.setQuantity(5);

    Mockito.when(accessoryRepository.findById(id)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(AccessoryNotFoundException.class,
        () -> accessoryService.updateAccessory(updatedAccessory, id));
  }

  @Test
  public void testDeleteAccessory() throws AccessoryNotFoundException {
    // Arrange
    UUID id = UUID.randomUUID();
    Accessory existingAccessory = new Accessory();
    existingAccessory.setId(id);
    existingAccessory.setName("Accessory");
    existingAccessory.setDailyRate(50.00);
    existingAccessory.setDescription("Accessory updated");
    existingAccessory.setQuantity(5);

    Mockito.when(accessoryRepository.findById(id)).thenReturn(Optional.of(existingAccessory));

    // Act
    Accessory result = accessoryService.deleteAccessory(id);

    // Assert
    assertEquals(existingAccessory, result);
    Mockito.verify(accessoryRepository).delete(existingAccessory);
  }

  @Test
  public void testDeleteAccessoryNotFoundException() {
    // Arrange
    UUID id = UUID.randomUUID();

    Mockito.when(accessoryRepository.findById(id)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(AccessoryNotFoundException.class,
        () -> accessoryService.deleteAccessory(id));
  }
}
