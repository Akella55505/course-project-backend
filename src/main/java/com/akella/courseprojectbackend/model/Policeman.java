package com.akella.courseprojectbackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "policeman")
public class Policeman {
    @Id
    @Column(name = "policeman_id")
    private Long policemanId;
    @Column(nullable = false)
    private String patronymic;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private String name;
    @Column(unique = true)
    private String email = null;
}
