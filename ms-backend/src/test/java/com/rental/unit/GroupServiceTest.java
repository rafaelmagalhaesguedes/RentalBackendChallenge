package com.rental.unit;

import static com.rental.mock.GroupMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
 * Unit Tests to Group Service Class
 * *
 * Created by Rafa Guedes
 * */
@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {

  @InjectMocks
  GroupService service;

  @Mock
  GroupRepository repository;

  @Test
  public void testGroupRetrievalById() throws GroupNotFoundException {
    // Arrange
    when(repository.findById(eq(GROUP_01.getId())))
        .thenReturn(Optional.of(GROUP_01));

    // Act
    Group groupFromDb = service.getGroupById(GROUP_01.getId());

    // Assert
    assertThat(groupFromDb).isEqualTo(GROUP_01);
  }

  @Test
  public void testRetrievalAllGroups() {
    // Arrange
    List<Group> groups = Arrays.asList(GROUP_01, GROUP_02);
    Page<Group> page = new PageImpl<>(groups);
    Pageable pageable = PageRequest.of(0, 2);

    // Act
    when(repository.findAll(pageable)).thenReturn(page);
    List<Group> getAllGroups = service.getAllGroups(0, 2);
    verify(repository).findAll(pageable);

    // Assert
    assertEquals(2, getAllGroups.size());
    assertThat(getAllGroups).isEqualTo(groups);
  }

  @Test
  public void testCreateGroup() throws PersonExistingException {
    // Arrange
    when(repository.save(GROUP_CREATION)).thenReturn(GROUP_CREATION);

    // Act
    Group newGroup = service.createGroup(GROUP_CREATION);
    verify(repository).save(GROUP_CREATION);

    // Assert
    assertThat(newGroup).isEqualTo(GROUP_CREATION);
  }

  @Test
  public void testUpdateGroup() throws GroupNotFoundException {
    // Arrange
    when(repository.findById(GROUP_01.getId())).thenReturn(Optional.of(GROUP_01));
    when(repository.save(GROUP_01)).thenReturn(GROUP_01);

    // Act
    Group result = service.updateGroup(GROUP_UPDATED, GROUP_01.getId());

    // Assert
    assertThat(result).isEqualTo(GROUP_01);
  }

  @Test
  public void testUpdateGroupNotFoundException() {

    // Arrange
    when(repository.findById(GROUP_01.getId()))
        .thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(GroupNotFoundException.class,
        () -> service.updateGroup(GROUP_UPDATED, GROUP_01.getId()));
  }

  @Test
  public void testDeleteGroup() throws GroupNotFoundException {
    // Arrange
    when(repository.findById(GROUP_01.getId()))
        .thenReturn(Optional.of(GROUP_01));

    // Act
    Group result = service.deleteGroup(GROUP_01.getId());

    // Assert
    assertEquals(GROUP_01, result);
    Mockito.verify(repository).delete(GROUP_01);
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
