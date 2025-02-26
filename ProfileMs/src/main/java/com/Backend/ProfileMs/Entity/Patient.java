package com.Backend.ProfileMs.Entity;


import com.Backend.ProfileMs.DTO.BloodGroup;
import com.Backend.ProfileMs.DTO.PatientDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private LocalDate dob;
    private String phone;
    private String address;
    @Column(unique = true)
    private String addarNo;
    private BloodGroup bloodGroup;
    private String allergies;
    private String chronicDiseas;

}
