package com.rental.service;

import com.rental.entity.Group;
import com.rental.repository.GroupRepository;
import com.rental.service.exception.GroupNotFoundException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
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
}
