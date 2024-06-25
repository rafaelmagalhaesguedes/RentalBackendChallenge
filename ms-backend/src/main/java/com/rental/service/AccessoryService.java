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

@Service
public class AccessoryService {

  // Inject repository
  private final AccessoryRepository accessoryRepository;

  @Autowired
  public AccessoryService(AccessoryRepository accessoryRepository) {
    this.accessoryRepository = accessoryRepository;
  }

  public Accessory getAccessoryById(UUID id) throws AccessoryNotFoundException {
    return accessoryRepository.findById(id)
        .orElseThrow(AccessoryNotFoundException::new);
  }

  public List<Accessory> getAllAccessories(int pageNumber, int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    Page<Accessory> page = accessoryRepository.findAll(pageable);
    return page.getContent();
  }

  public Accessory createAccessory(Accessory accessory) {
    return accessoryRepository.save(accessory);
  }

  public Accessory updateAccessory(Accessory accessory, UUID id) throws AccessoryNotFoundException {
    Accessory accessoryFromDb = getAccessoryById(id);

    accessoryFromDb.setName(accessory.getName());
    accessoryFromDb.setDailyRate(accessory.getDailyRate());

    return accessoryRepository.save(accessoryFromDb);
  }

  public Accessory deleteAccessory(UUID id) throws AccessoryNotFoundException {
    Accessory accessory = getAccessoryById(id);

    accessoryRepository.delete(accessory);
    return accessory;
  }
 }
