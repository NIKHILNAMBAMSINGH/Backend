package com.Backend.ProfileMs.Controller;

import com.Backend.ProfileMs.DTO.DoctorDTO;
import com.Backend.ProfileMs.DTO.PatientDTO;
import com.Backend.ProfileMs.Exception.HsException;
import com.Backend.ProfileMs.Service.DoctorService;
import com.Backend.ProfileMs.Service.PatientService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile/patient")
public class PatientController {


    @Autowired
    private PatientService patientService;
    @PostMapping("/add")
    public ResponseEntity<Long>addPatient(@RequestBody PatientDTO patientDTO) throws HsException {
        Long patientId=patientService.addPatient(patientDTO);
        return new ResponseEntity<>(patientId, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PatientDTO>getPatientById(@PathVariable Long id) throws HsException {
        PatientDTO patientDTO=patientService.getPatientById(id);
        System.out.println(patientDTO);
        return new ResponseEntity<>(patientDTO,HttpStatus.OK);
    }
    @PostMapping("/update")
    public ResponseEntity<PatientDTO>updatePatient(@RequestBody PatientDTO patientDTO) throws HsException {
        PatientDTO patientDto=patientService.updatePatient(patientDTO);
        return new ResponseEntity<>(patientDto, HttpStatus.ACCEPTED);
    }

}
