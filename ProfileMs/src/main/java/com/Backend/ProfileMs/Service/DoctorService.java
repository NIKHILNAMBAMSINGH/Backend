package com.Backend.ProfileMs.Service;

import com.Backend.ProfileMs.DTO.DoctorDTO;
import com.Backend.ProfileMs.DTO.PatientDTO;
import com.Backend.ProfileMs.Entity.Doctor;
import com.Backend.ProfileMs.Exception.HsException;
import org.springframework.stereotype.Service;

public interface DoctorService {
    public Long addDoctor(DoctorDTO doctorDTO)throws HsException;
    public DoctorDTO getDoctorById(Long id) throws HsException;

    DoctorDTO updateDoctor(DoctorDTO doctorDTO) throws HsException;
}
