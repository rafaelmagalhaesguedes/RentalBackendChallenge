package com.rental.config;

import com.rental.entity.Accessory;
import com.rental.repository.AccessoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class DataSeederConfig implements CommandLineRunner {

    private final AccessoryRepository accessoryRepository;

    public DataSeederConfig(AccessoryRepository accessoryRepository) {
        this.accessoryRepository = accessoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        seedAccessories();
    }

    private void seedAccessories() {
        List<Accessory> accessories = new ArrayList<>();

        accessories.add(new Accessory(UUID.randomUUID(), "GPS", "SmartPhone Samsumg com GPS integrado.", 5, 60.00));
        accessories.add(new Accessory(UUID.randomUUID(), "Bebê Conforto", "Cadeira para bebê até 5 anos de idade.", 10, 30.00));
        accessories.add(new Accessory(UUID.randomUUID(), "Assento de Elevação", "Assento de Elevação  para bebê até 5 anos de idade.", 10, 25.00));

        accessoryRepository.saveAll(accessories);
    }
}