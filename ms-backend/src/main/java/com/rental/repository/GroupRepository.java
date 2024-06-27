package com.rental.repository;

import com.rental.entity.Group;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Group repository.
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {

}
