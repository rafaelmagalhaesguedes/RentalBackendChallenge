package com.rental.controller;

import com.rental.controller.dto.group.GroupCreationDto;
import com.rental.controller.dto.group.GroupDto;
import com.rental.entity.Group;
import com.rental.service.GroupService;
import com.rental.service.exception.GroupNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Group controller.
 */
@RestController
@RequestMapping("/group")
@Validated
public class GroupController {

  private final GroupService groupService;

  /**
   * Instantiates a new Group controller.
   *
   * @param groupService the group service
   */
  @Autowired
  public GroupController(GroupService groupService) {
    this.groupService = groupService;
  }

  /**
   * Gets group by id.
   *
   * @param id the id
   * @return the group by id
   * @throws GroupNotFoundException the group not found exception
   */
  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('MANAGER')")
  public GroupDto getGroupById(@PathVariable UUID id) throws GroupNotFoundException {
    return GroupDto.fromEntity(
        groupService.getGroupById(id)
    );
  }

  /**
   * Gets all groups.
   *
   * @param pageNumber the page number
   * @param pageSize   the page size
   * @return the all groups
   */
  @GetMapping
  @PreAuthorize("hasAuthority('MANAGER')")
  public List<GroupDto> getAllGroups(
      @RequestParam(required = false, defaultValue = "0") int pageNumber,
      @RequestParam(required = false, defaultValue = "20") int pageSize
  ) {
    List<Group> paginatedCustomers = groupService.getAllGroups(pageNumber, pageSize);
    return paginatedCustomers.stream()
        .map(GroupDto::fromEntity)
        .toList();
  }

  /**
   * Create group dto.
   *
   * @param groupCreationDto the group creation dto
   * @return the group dto
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('MANAGER')")
  public GroupDto createGroup(@RequestBody @Valid GroupCreationDto groupCreationDto) {
    return GroupDto.fromEntity(
        groupService.createGroup(groupCreationDto.toEntity())
    );
  }

  /**
   * Update group group dto.
   *
   * @param groupCreationDto the group creation dto
   * @param id               the id
   * @return the group dto
   * @throws GroupNotFoundException the group not found exception
   */
  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('MANAGER')")
  public GroupDto updateGroup(@RequestBody @Valid GroupCreationDto groupCreationDto, @PathVariable UUID id) throws GroupNotFoundException {
    return GroupDto.fromEntity(
        groupService.updateGroup(groupCreationDto.toEntity(), id)
    );
  }

  /**
   * Delete group group dto.
   *
   * @param id the id
   * @return the group dto
   * @throws GroupNotFoundException the group not found exception
   */
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('MANAGER')")
  public GroupDto deleteGroup(@PathVariable UUID id) throws GroupNotFoundException {
    return GroupDto.fromEntity(
        groupService.deleteGroup(id)
    );
  }
}
