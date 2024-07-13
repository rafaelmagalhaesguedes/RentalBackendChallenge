package com.rental.config.database;

import com.rental.entity.Group;
import com.rental.repository.GroupRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
class GroupSeeder implements CommandLineRunner {

    private final GroupRepository groupRepository;

    public GroupSeeder(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        seedGroups();
    }

    private void seedGroups() {
        List<Group> groups = new ArrayList<>();

        groups.add(new Group(UUID.randomUUID(), "Grupo A", "Uno, Mobi, Kwid ou similar", 150.00));
        groups.add(new Group(UUID.randomUUID(), "Grupo B", "Argo, HB20, Gol ou similar", 180.00));
        groups.add(new Group(UUID.randomUUID(), "Grupo C", "Chronos, HB20S, Voyage ou similar", 200.00));
        groups.add(new Group(UUID.randomUUID(), "Grupo D", "Creta, Captur, T-Cross ou similar", 220.00));
        groups.add(new Group(UUID.randomUUID(), "Grupo E", "Renegade, Nivus, Tracker ou similar", 350.00));
        groups.add(new Group(UUID.randomUUID(), "Grupo G", "Compass, Taos, Tiggo 7 ou similar", 450.00));

        groupRepository.saveAll(groups);
    }
}
