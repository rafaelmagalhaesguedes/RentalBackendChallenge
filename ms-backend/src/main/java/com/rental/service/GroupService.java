package com.rental.service;

import com.rental.entity.Group;
import com.rental.repository.GroupRepository;
import com.rental.service.exception.GroupNotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * The type Group service.
 */
@Service
public class GroupService {

  private final GroupRepository groupRepository;

  /**
   * Instantiates a new Group service.
   *
   * @param groupRepository the group repository
   */
  @Autowired
  public GroupService(GroupRepository groupRepository) {
    this.groupRepository = groupRepository;
  }

  /**
   * Gets group by id.
   *
   * @param id the id
   * @return the group by id
   * @throws GroupNotFoundException the group not found exception
   */
  public Group getGroupById(UUID id) throws GroupNotFoundException {
    return groupRepository.findById(id)
        .orElseThrow(GroupNotFoundException::new);
  }

  /**
   * Gets all groups.
   *
   * @param pageNumber the page number
   * @param pageSize   the page size
   * @return the all groups
   */
  public List<Group> getAllGroups(int pageNumber, int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<Group> page = groupRepository.findAll(pageable);

    return page.toList();
  }

  /**
   * Create group.
   *
   * @param group the group
   * @return the group
   */
  public Group createGroup(Group group) {
    return groupRepository.save(group);
  }

  /**
   * Update group.
   *
   * @param group the group
   * @param id    the id
   * @return the group
   * @throws GroupNotFoundException the group not found exception
   */
  public Group updateGroup(Group group, UUID id) throws GroupNotFoundException {
    Group groupFromDb = getGroupById(id);

    groupFromDb.setName(group.getName());
    groupFromDb.setVehicles(group.getVehicles());
    groupFromDb.setDailyRate(group.getDailyRate());
    groupFromDb.setImageURL(group.getImageURL());

    return groupRepository.save(groupFromDb);
  }

  /**
   * Delete group.
   *
   * @param id the id
   * @return the group
   * @throws GroupNotFoundException the group not found exception
   */
  public Group deleteGroup(UUID id) throws GroupNotFoundException {
    Group group = getGroupById(id);

    groupRepository.delete(group);

    return group;
  }
}
