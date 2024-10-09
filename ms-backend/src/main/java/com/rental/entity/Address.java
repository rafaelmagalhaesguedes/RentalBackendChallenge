package com.rental.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_addresses")
@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cep;
    private String street;
    private String neighborhood;
    private String city;
    private String state;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
