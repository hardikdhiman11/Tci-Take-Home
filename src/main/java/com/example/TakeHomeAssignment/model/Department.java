package com.example.TakeHomeAssignment.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String departmentName;
    @OneToMany(mappedBy = "department",fetch = FetchType.LAZY,
            orphanRemoval = true,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<Employee> employees;
}
