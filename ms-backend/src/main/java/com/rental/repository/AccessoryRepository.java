package com.rental.repository;

import com.rental.entity.Accessory;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Accessory repository.
 */
@Repository
public interface AccessoryRepository extends JpaRepository <Accessory, UUID> {

  /**
   * Find all by id in list.
   *
   * @param accessoryIds the accessory ids
   * @return the list
   */
  List<Accessory> findAllByIdIn(List<UUID> accessoryIds);
}