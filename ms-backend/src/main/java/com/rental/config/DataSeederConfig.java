package com.rental.config;

import com.rental.entity.Accessory;
import com.rental.entity.Group;
import com.rental.entity.Vehicle;
import com.rental.repository.AccessoryRepository;
import com.rental.repository.GroupRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class DataSeederConfig implements CommandLineRunner {

    private final AccessoryRepository accessoryRepository;
    private final GroupRepository groupRepository;

    public DataSeederConfig(AccessoryRepository accessoryRepository, GroupRepository groupRepository) {
        this.accessoryRepository = accessoryRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        seedAccessories();
        seedGroups();
    }

    private void seedGroups() {
        List<Group> groups = new ArrayList<>();

        Group groupA = new Group(UUID.randomUUID(), "Grupo A", "Uno, Mobi, Kwid ou similar", 150.00);
        Group groupB = new Group(UUID.randomUUID(), "Grupo B", "Argo, Gol, HB20 ou similar", 200.00);
        Group groupC = new Group(UUID.randomUUID(), "Grupo C", "Chronos, Logan, HB20S ou similar", 250.00);
        Group groupD = new Group(UUID.randomUUID(), "Grupo D", "Creta, Captur, T-Cross ou similar", 280.00);
        Group groupE = new Group(UUID.randomUUID(), "Grupo E", "Renegade, Toro ou similar", 300.00);
        Group groupF = new Group(UUID.randomUUID(), "Grupo F", "Doblò, Spin ou similar", 350.00);
        Group groupG = new Group(UUID.randomUUID(), "Grupo G", "Hilux, S10, Ranger ou similar", 350.00);

        groups.add(groupA);
        groups.add(groupB);
        groups.add(groupC);
        groups.add(groupD);
        groups.add(groupE);
        groups.add(groupF);
        groups.add(groupG);

        groupRepository.saveAll(groups);
    }

    private void seedAccessories() {
        List<Accessory> accessories = new ArrayList<>();

        accessories.add(new Accessory(UUID.randomUUID(), "GPS", "SmartPhone Samsumg com GPS integrado.", 5, 60.00));
        accessories.add(new Accessory(UUID.randomUUID(), "Bebê Conforto", "Cadeira para bebê até 5 anos de idade.", 10, 30.00));
        accessories.add(new Accessory(UUID.randomUUID(), "Assento de Elevação", "Assento de Elevação  para bebê até 5 anos de idade.", 10, 25.00));

        accessoryRepository.saveAll(accessories);
    }
}
