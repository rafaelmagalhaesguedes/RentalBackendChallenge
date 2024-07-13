package com.rental.config.database;

import com.rental.entity.Accessory;
import com.rental.repository.AccessoryRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class AccessorySeeder {

    private final AccessoryRepository accessoryRepository;

    public AccessorySeeder(AccessoryRepository accessoryRepository) {
        this.accessoryRepository = accessoryRepository;
    }

    public void seed() {
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
}
