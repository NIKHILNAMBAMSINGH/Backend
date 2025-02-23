package com.Backend.ProfileMs.DTO;
import com.Backend.ProfileMs.Entity.Patient;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    private Long id;
    private String name;
    private String email;
    private LocalDate dob;
    private String phone;
    private String address;
    private String addarNo;
    private BloodGroup bloodGroup;

    public Patient toPatientDTO(){
        return new Patient
                (this.id,this.name,this.email,this.dob,this.phone,this.address,this.addarNo,this.bloodGroup);
    }

}
