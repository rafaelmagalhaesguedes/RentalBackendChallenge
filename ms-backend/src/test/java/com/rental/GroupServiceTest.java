package com.rental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;

import com.rental.entity.Group;
import com.rental.repository.GroupRepository;
import com.rental.service.GroupService;
import com.rental.service.exception.PersonExistingException;
import com.rental.service.exception.GroupNotFoundException;
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
public class GroupServiceTest {

  @Autowired
  GroupService service;

  @MockBean
  GroupRepository repository;

  @Test
  public void testGroupRetrievalById() throws GroupNotFoundException {

    // Arrange
    UUID id = UUID.randomUUID();

    Group group = new Group();
    group.setId(id);
    group.setName("Group A");
    group.setVehicles("Mercedez, c180, BMW, x6, Porche 911");
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

  @Test
  public void testRetrievalAllGroups() {

    // Arrange
    UUID group1Id = UUID.randomUUID();
    UUID group2Id = UUID.randomUUID();

    Group group1 = new Group();
    group1.setId(group1Id);
    group1.setName("Grupo C");
    group1.setVehicles("Uno, Mobi, Kwid");

    Group group2 = new Group();
    group2.setId(group2Id);
    group2.setName("Grupo D");
    group2.setVehicles("Onix, HB20, Gol");

    List<Group> groups = Arrays.asList(group1, group2);

    Page<Group> page = new PageImpl<>(groups);
    Pageable pageable = PageRequest.of(0, 2);

    // Act
    Mockito.when(repository.findAll(pageable)).thenReturn(page);

    List<Group> getAllGroups = service.getAllGroups(0, 2);

    Mockito.verify(repository).findAll(pageable);

    // Assert
    assertEquals(2, getAllGroups.size());
    assertEquals(group1.getId(), groups.get(0).getId());
    assertEquals(group1.getName(), groups.get(0).getName());
    assertEquals(group1.getVehicles(), groups.get(0).getVehicles());
    assertEquals(group1.getDailyRate(), groups.get(0).getDailyRate());

    assertEquals(group2.getId(), groups.get(1).getId());
    assertEquals(group2.getName(), groups.get(1).getName());
    assertEquals(group2.getVehicles(), groups.get(1).getVehicles());
    assertEquals(group2.getDailyRate(), groups.get(1).getDailyRate());
  }

  @Test
  public void testCreateGroup() throws PersonExistingException {

    // Arrange
    Group group = new Group();
    group.setId(UUID.randomUUID());
    group.setName("Group D");
    group.setVehicles("Creta, Renegade, Tracker, Compass");
    group.setDailyRate(250.00);

    // Act
    Mockito.when(repository.save(group)).thenReturn(group);

    Group newGroup = service.createGroup(group);

    Mockito.verify(repository).save(group);

    // Assert
    assertEquals(newGroup.getId(), group.getId());
    assertEquals(newGroup.getName(), group.getName());
    assertEquals(newGroup.getVehicles(), group.getVehicles());
    assertEquals(newGroup.getDailyRate(), group.getDailyRate());
  }

  @Test
  public void testUpdateGroup() throws GroupNotFoundException {

    // Arrange
    UUID id = UUID.randomUUID();

    Group existingGroup = new Group();
    existingGroup.setName("Group A");
    existingGroup.setVehicles("Uno, Mobi, Onix");
    existingGroup.setDailyRate(150.00);

    Group updatedGroup= new Group();
    updatedGroup.setName("Group A+");
    updatedGroup.setVehicles("Uno, Mobi, Onix e Kwid");
    updatedGroup.setDailyRate(180.00);

    Mockito.when(repository.findById(id)).thenReturn(Optional.of(existingGroup));
    Mockito.when(repository.save(existingGroup)).thenReturn(existingGroup);

    // Act
    Group result = service.updateGroup(updatedGroup, id);

    // Assert
    assertEquals(updatedGroup.getId(), result.getId());
    assertEquals(updatedGroup.getName(), result.getName());
    assertEquals(updatedGroup.getVehicles(), result.getVehicles());
    assertEquals(updatedGroup.getDailyRate(), result.getDailyRate());
  }

  @Test
  public void testUpdateGroupNotFoundException() {

    // Arrange
    UUID id = UUID.randomUUID();

    Group updatedGroup = new Group();
    updatedGroup.setName("Group A");
    updatedGroup.setVehicles("Uno, Mobi, Onix");
    updatedGroup.setDailyRate(150.00);

    Mockito.when(repository.findById(id))
        .thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(GroupNotFoundException.class,
        () -> service.updateGroup(updatedGroup, id));
  }

  @Test
  public void testDeleteGroup() throws GroupNotFoundException {

    // Arrange
    UUID id = UUID.randomUUID();
    Group existingGroup = new Group();
    existingGroup.setId(id);
    existingGroup.setName("Group B+");
    existingGroup.setVehicles("Gol, Argo, Onix");
    existingGroup.setDailyRate(200.00);

    Mockito.when(repository.findById(id))
        .thenReturn(Optional.of(existingGroup));

    // Act
    Group result = service.deleteGroup(id);

    // Assert
    assertEquals(existingGroup, result);
    Mockito.verify(repository).delete(existingGroup);
  }

  @Test
  public void testDeleteGroupNotFoundException() {

    // Arrange
    UUID id = UUID.randomUUID();

    Mockito.when(repository.findById(id))
        .thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(GroupNotFoundException.class,
        () -> service.deleteGroup(id));
  }
}
