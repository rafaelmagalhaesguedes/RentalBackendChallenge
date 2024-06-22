package com.rental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;

import com.rental.entity.Group;
import com.rental.repository.GroupRepository;
import com.rental.service.GroupService;
import com.rental.service.exception.GroupNotFoundException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class GroupServiceTest {

  @Autowired
  GroupService service;

  @MockBean
  GroupRepository repository;

  @Test
  public void testVehicleGroupRetrievalById() throws GroupNotFoundException {

    // Arrange
    UUID id = UUID.randomUUID();

    Group group = new Group();
    group.setId(id);
    group.setName("Group A");
    group.setVehicles("Mercedez c180, BMW x6, Porche 911");
    group.setDailyRate(200.50);

    // Act
    Mockito.when(repository.findById(eq(id)))
        .thenReturn(Optional.of(group));

    Group groupFromDb = service.getGroupById(id);

    Mockito.verify(repository).findById(id);

    // Assert
    assertEquals(groupFromDb.getId(), group.getId());
    assertEquals(groupFromDb.getName(), group.getName());
    assertEquals(groupFromDb.getVehicles(), group.getVehicles());
    assertEquals(groupFromDb.getDailyRate(), group.getDailyRate());
  }
}
