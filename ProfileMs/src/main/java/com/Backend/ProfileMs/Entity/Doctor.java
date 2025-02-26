package com.Backend.ProfileMs.Entity;

import com.Backend.ProfileMs.DTO.BloodGroup;
import com.Backend.ProfileMs.DTO.DoctorDTO;
import com.Backend.ProfileMs.DTO.PatientDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Doctor")
public class Doctor {
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
    private String licenseNo;
    private String specialization;
    private String department;
    private String totalExp;
    public DoctorDTO toDoctorDTO(){
        return new DoctorDTO
                (this.name,this.email,this.dob,this.phone,
                        this.address,this.licenseNo,this.specialization,this.department,
                        this.totalExp);
    }
}
