package com.rental.controller;

import com.rental.controller.dto.group.GroupCreationDto;
import com.rental.controller.dto.group.GroupDto;
import com.rental.entity.Group;
import com.rental.service.GroupService;
import com.rental.service.exception.GroupNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
  @Operation(summary = "Get group by ID", description = "Retrieve a group by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Group found",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupDto.class))),
      @ApiResponse(responseCode = "404", description = "Group not found",
          content = @Content(mediaType = "application/json"))
  })
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
  @Operation(summary = "Get all groups", description = "Retrieve all groups with pagination")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Groups retrieved",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupDto.class)))
  })
  public List<GroupDto> getAllGroups(
      @RequestParam(required = false, defaultValue = "0") int pageNumber,
      @RequestParam(required = false, defaultValue = "20") int pageSize
  ) {
    List<Group> paginatedGroups = groupService.getAllGroups(pageNumber, pageSize);
    return paginatedGroups.stream()
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
  @Operation(summary = "Create group", description = "Create a new group")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Group created",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupDto.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input",
          content = @Content(mediaType = "application/json"))
  })
  public GroupDto createGroup(@RequestBody @Valid GroupCreationDto groupCreationDto) {
    return GroupDto.fromEntity(
        groupService.createGroup(groupCreationDto.toEntity())
    );
  }

  /**
   * Update group dto.
   *
   * @param groupCreationDto the group creation dto
   * @param id               the id
   * @return the group dto
   * @throws GroupNotFoundException the group not found exception
   */
  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('MANAGER')")
  @Operation(summary = "Update group", description = "Update an existing group")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Group updated",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupDto.class))),
      @ApiResponse(responseCode = "404", description = "Group not found",
          content = @Content(mediaType = "application/json")),
      @ApiResponse(responseCode = "400", description = "Invalid input",
          content = @Content(mediaType = "application/json"))
  })
  public GroupDto updateGroup(@RequestBody @Valid GroupCreationDto groupCreationDto, @PathVariable UUID id) throws GroupNotFoundException {
    return GroupDto.fromEntity(
        groupService.updateGroup(groupCreationDto.toEntity(), id)
    );
  }

  /**
   * Delete group dto.
   *
   * @param id the id
   * @return the group dto
   * @throws GroupNotFoundException the group not found exception
   */
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('MANAGER')")
  @Operation(summary = "Delete group", description = "Delete a group by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Group deleted",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = GroupDto.class))),
      @ApiResponse(responseCode = "404", description = "Group not found",
          content = @Content(mediaType = "application/json"))
  })
  public GroupDto deleteGroup(@PathVariable UUID id) throws GroupNotFoundException {
    return GroupDto.fromEntity(
        groupService.deleteGroup(id)
    );
  }
}
