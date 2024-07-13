package com.rental.config;

import com.rental.entity.Accessory;
import com.rental.entity.Group;
import com.rental.entity.Payment;
import com.rental.entity.Person;
import com.rental.entity.Reservation;
import com.rental.enums.PaymentStatus;
import com.rental.enums.ReservationStatus;
import com.rental.repository.AccessoryRepository;
import com.rental.repository.GroupRepository;
import com.rental.repository.PaymentRepository;
import com.rental.repository.PersonRepository;
import com.rental.repository.ReservationRepository;
import com.rental.security.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class DataSeederConfig implements CommandLineRunner {

    private final AccessoryRepository accessoryRepository;
    private final GroupRepository groupRepository;
    private final PersonRepository personRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;

    public DataSeederConfig(AccessoryRepository accessoryRepository, GroupRepository groupRepository,
                            PersonRepository personRepository, ReservationRepository reservationRepository,
                            PaymentRepository paymentRepository) {
        this.accessoryRepository = accessoryRepository;
        this.groupRepository = groupRepository;
        this.personRepository = personRepository;
        this.reservationRepository = reservationRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        seedAccessories();
        seedGroups();
        seedPersons();
        seedReservations();
        seedPayments();
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

    private void seedAccessories() {
        List<Accessory> accessories = new ArrayList<>();

        accessories.add(new Accessory(UUID.randomUUID(), "Proteção Básica", "Adicione a Proteção Básica do Carro, e viaje com a cobertura contra danos no casco (partes do carro) e roubo. Conte com cobrança limitada a um valor máximo preestabelecido nos casos de avarias (quaisquer danos), perda total, roubo e furto.", 1, 12.00));
        accessories.add(new Accessory(UUID.randomUUID(), "Proteção Especial", "Essa proteção te deixa seguro com relação a danos aos vidros laterais, para-brisas, vidro traseiro, retrovisores, faróis, lanternas e/ou pneus do veículo. Assim, você tem isenção de custo pré-fixado de limite de danos para esses itens e não paga nada em caso de avarias.", 1, 20.00));
        accessories.add(new Accessory(UUID.randomUUID(), "Limpeza Simples Garantida", "Com a Limpeza Garantida você não precisa nem se preocupar em lavar o carro antes de devolver. Você antecipa uma lavagem simples, que ficará por nossa conta.", 1, 30.00));
        accessories.add(new Accessory(UUID.randomUUID(), "Limpeza Especial", "A lavagem especial é solicitada naquelas situações em que são necessários processos mais cuidadosos e demorados de higienização. Exemplos: se o carro for devolvido com areia ou pelos de animais, será cobrada o valor da lavagem especial, independente da quantidade do material.", 5, 150.00));
        accessories.add(new Accessory(UUID.randomUUID(), "GPS", "SmartPhone Samsumg com GPS integrado.", 5, 60.00));
        accessories.add(new Accessory(UUID.randomUUID(), "Bebê Conforto", "Cadeira para bebê até 5 anos de idade.", 10, 30.00));
        accessories.add(new Accessory(UUID.randomUUID(), "Assento de Elevação", "Assento de Elevação para bebê até 5 anos de idade.", 10, 25.00));

        accessoryRepository.saveAll(accessories);
    }

    private void seedPersons() {
        List<Person> persons = new ArrayList<>();

        persons.add(new Person(UUID.randomUUID(), "João da Silva", "joao.silva", "joao.silva@example.com", "password", Role.USER));
        persons.add(new Person(UUID.randomUUID(), "Maria Oliveira", "maria.oliveira", "maria.oliveira@example.com", "password", Role.USER));
        persons.add(new Person(UUID.randomUUID(), "Admin User", "admin.user", "admin.user@example.com", "password", Role.ADMIN));

        personRepository.saveAll(persons);
    }

    private void seedReservations() {
        List<Reservation> reservations = new ArrayList<>();

        Person person1 = personRepository.findByEmail("joao.silva@example.com").orElseThrow();
        Person person2 = personRepository.findByEmail("maria.oliveira@example.com").orElseThrow();
        Group groupA = groupRepository.findByName("Grupo A").orElseThrow();
        Group groupB = groupRepository.findByName("Grupo B").orElseThrow();

        List<Accessory> accessories = accessoryRepository.findAll();

        reservations.add(new Reservation(UUID.randomUUID(), person1, groupA, accessories, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5), 200.00, 4, ReservationStatus.CONFIRMED, "Online"));
        reservations.add(new Reservation(UUID.randomUUID(), person2, groupB, accessories, LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(6), 220.00, 4, ReservationStatus.CONFIRMED, "Counter"));

        reservationRepository.saveAll(reservations);
    }

    private void seedPayments() {
        List<Payment> payments = new ArrayList<>();

        Reservation reservation1 = reservationRepository.findByPersonEmail("joao.silva@example.com").orElseThrow();
        Reservation reservation2 = reservationRepository.findByPersonEmail("maria.oliveira@example.com").orElseThrow();

        payments.add(new Payment(UUID.randomUUID(), reservation1, 200.00, PaymentStatus.CONFIRMED, LocalDateTime.now(), "Online"));
        payments.add(new Payment(UUID.randomUUID(), reservation2, 220.00, PaymentStatus.CONFIRMED, LocalDateTime.now(), "Offline"));

        paymentRepository.saveAll(payments);
    }
}
