package com.rental.entity;

import com.rental.service.CryptoService;
import com.rental.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@DiscriminatorValue("CUSTOMER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends Person {

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "document")
    private String encryptedDocument;

    @Transient
    private String rawDocument;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    @Builder()
    public Customer(UUID id, String name, String email, String password, Role role, String rawDocument, String phoneNumber, Address address) {
        super(id, name, email, password, role);
        this.phoneNumber = phoneNumber;
        this.rawDocument = rawDocument;
        this.address = address;
    }

    @PrePersist
    @PreUpdate
    public void encryptFields() {
        this.encryptedDocument = CryptoService.encrypt(rawDocument);
    }

    @PostLoad
    public void decryptFields() {
        this.rawDocument = CryptoService.decrypt(encryptedDocument);
    }
}