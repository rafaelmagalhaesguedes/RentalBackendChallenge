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

@Service
public class GroupService {

  private final GroupRepository groupRepository;

  @Autowired
  public GroupService(GroupRepository groupRepository) {
    this.groupRepository = groupRepository;
  }

  public Group getGroupById(UUID id) throws GroupNotFoundException {
    return groupRepository.findById(id)
        .orElseThrow(GroupNotFoundException::new);
  }

  public List<Group> getAllGroups(int pageNumber, int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<Group> page = groupRepository.findAll(pageable);

    return page.toList();
  }

  public Group createGroup(Group group) {
    return groupRepository.save(group);
  }

  public Group updateGroup(Group group, UUID id) throws GroupNotFoundException {
    Group groupFromDb = getGroupById(id);

    groupFromDb.setName(group.getName());
    groupFromDb.setVehicles(group.getVehicles());
    groupFromDb.setDailyRate(group.getDailyRate());

    return groupRepository.save(groupFromDb);
  }

  public Group deleteGroup(UUID id) throws GroupNotFoundException {
    Group group = getGroupById(id);

    groupRepository.delete(group);

    return group;
  }
}
