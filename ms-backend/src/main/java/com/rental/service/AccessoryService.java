package com.rental.service;

import com.rental.entity.Accessory;
import com.rental.repository.AccessoryRepository;
import com.rental.service.exception.AccessoryNotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * The type Accessory service.
 */
@Service
public class AccessoryService {

  // Inject repository
  private final AccessoryRepository accessoryRepository;

  /**
   * Instantiates a new Accessory service.
   *
   * @param accessoryRepository the accessory repository
   */
  @Autowired
  public AccessoryService(AccessoryRepository accessoryRepository) {
    this.accessoryRepository = accessoryRepository;
  }

  /**
   * Gets accessory by id.
   *
   * @param id the id
   * @return the accessory by id
   * @throws AccessoryNotFoundException the accessory not found exception
   */
  public Accessory getAccessoryById(UUID id) throws AccessoryNotFoundException {
    return accessoryRepository.findById(id)
        .orElseThrow(AccessoryNotFoundException::new);
  }

  /**
   * Gets all accessories.
   *
   * @param pageNumber the page number
   * @param pageSize   the page size
   * @return the all accessories
   */
  public List<Accessory> getAllAccessories(int pageNumber, int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<Accessory> page = accessoryRepository.findAll(pageable);
    return page.getContent();
  }

  /**
   * Create accessory.
   *
   * @param accessory the accessory
   * @return the accessory
   */
  public Accessory createAccessory(Accessory accessory) {
    return accessoryRepository.save(accessory);
  }

  /**
   * Update accessory.
   *
   * @param accessory the accessory
   * @param id        the id
   * @return the accessory
   * @throws AccessoryNotFoundException the accessory not found exception
   */
  public Accessory updateAccessory(Accessory accessory, UUID id) throws AccessoryNotFoundException {
    Accessory accessoryFromDb = getAccessoryById(id);

    accessoryFromDb.setName(accessory.getName());
    accessoryFromDb.setDailyRate(accessory.getDailyRate());
    accessoryFromDb.setDescription(accessory.getDescription());
    accessoryFromDb.setQuantity(accessory.getQuantity());

    return accessoryRepository.save(accessoryFromDb);
  }

  /**
   * Delete accessory.
   *
   * @param id the id
   * @return the accessory
   * @throws AccessoryNotFoundException the accessory not found exception
   */
  public Accessory deleteAccessory(UUID id) throws AccessoryNotFoundException {
    Accessory accessory = getAccessoryById(id);

    accessoryRepository.delete(accessory);
    return accessory;
  }
 }
