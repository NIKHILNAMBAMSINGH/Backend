package com.Backend.ProfileMs.Service.ServiceImpl;

import com.Backend.ProfileMs.DTO.PatientDTO;
import com.Backend.ProfileMs.Entity.Patient;
import com.Backend.ProfileMs.Exception.HsException;
import com.Backend.ProfileMs.Repository.PatientRepository;
import com.Backend.ProfileMs.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;
    @Override
    @Transactional
    public Long addPatient(PatientDTO patientDTO) throws HsException {
        Optional<Patient> patientOptional = patientRepository.findByEmail(patientDTO.getEmail());

        if (patientOptional.isPresent()) {
            throw new HsException("Patient already exists with ID: " + patientDTO.getName());
        }

        Patient patient = new Patient();
        patient.setName(patientDTO.getName());
        patient.setEmail(patientDTO.getEmail());
        patient.setDob(patientDTO.getDob());
        patient.setPhone(patientDTO.getPhone());
        patient.setAddress(patientDTO.getAddress());
        patient.setAddarNo(patientDTO.getAddarNo());
        patient.setBloodGroup(patientDTO.getBloodGroup());
        patient.setAllergies(patientDTO.getAllergies());
        patient.setChronicDiseas(patientDTO.getChronicDiseas());
        Patient savedPatient=patientRepository.save(patient);
        return savedPatient.getId();


    }

    @Override
    public PatientDTO getPatientById(Long id) throws HsException {
        Optional<Patient> patientOptional=patientRepository.findById(id);
        Patient patient=patientOptional.get();
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(patient.getId());
        patientDTO.setName(patient.getName());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setDob(patient.getDob());
        patientDTO.setPhone(patient.getPhone());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setAddarNo(patient.getAddarNo());
        patientDTO.setBloodGroup(patient.getBloodGroup());
        patientDTO.setAllergies(patient.getAllergies());
        patientDTO.setChronicDiseas(patient.getChronicDiseas());
        return patientDTO;
    }

    @Override
    public PatientDTO updatePatient(PatientDTO patientDTO) throws HsException {

        Patient patient = patientRepository.findById(patientDTO.getId())
                .orElseThrow(() -> new HsException("Patient not found"));
        patient.setName(patientDTO.getName());
        patient.setEmail(patientDTO.getEmail());
        patient.setDob(patientDTO.getDob());
        patient.setPhone(patientDTO.getPhone());
        patient.setAddress(patientDTO.getAddress());
        patient.setAddarNo(patientDTO.getAddarNo());
        patient.setBloodGroup(patientDTO.getBloodGroup());
        patient.setAllergies(patientDTO.getAllergies());
        patient.setChronicDiseas(patientDTO.getChronicDiseas());
        Patient updatedPatient = patientRepository.save(patient);
        return new PatientDTO(
                updatedPatient.getId(),
                updatedPatient.getName(),
                updatedPatient.getEmail(),
                updatedPatient.getDob(),
                updatedPatient.getPhone(),
                updatedPatient.getAddress(),
                updatedPatient.getAddarNo(),
                updatedPatient.getBloodGroup(),
                updatedPatient.getAllergies(),
                updatedPatient.getChronicDiseas()
        );
    }
}
