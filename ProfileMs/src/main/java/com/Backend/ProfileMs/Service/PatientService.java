package com.Backend.ProfileMs.Service;

import com.Backend.ProfileMs.DTO.PatientDTO;
import com.Backend.ProfileMs.Entity.Patient;
import com.Backend.ProfileMs.Exception.HsException;
import org.springframework.stereotype.Service;

public interface PatientService {
    public Long addPatient(PatientDTO patientDTO) throws HsException;
    public PatientDTO getPatientById(Long id) throws HsException;

    public PatientDTO updatePatient(PatientDTO patientDTO) throws HsException;
}
