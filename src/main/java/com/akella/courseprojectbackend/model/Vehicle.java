package com.akella.courseprojectbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String vin;
    @Column(nullable = false)
    private String make;
    @Column(nullable = false)
    private String model;
    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;
    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    private Person person;
}
