package com.Backend.ProfileMs.Controller;

import com.Backend.ProfileMs.DTO.DoctorDTO;
import com.Backend.ProfileMs.DTO.PatientDTO;
import com.Backend.ProfileMs.Entity.Doctor;
import com.Backend.ProfileMs.Exception.HsException;
import com.Backend.ProfileMs.Service.DoctorService;
import com.Backend.ProfileMs.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile/doctor")
public class DoctorController {


    @Autowired
    private DoctorService doctorService;
    @PostMapping("/add")
    public ResponseEntity<Long> addDoctor(@RequestBody DoctorDTO doctorDTO) throws HsException {
        System.out.println(doctorDTO);
        Long patientId=doctorService.addDoctor(doctorDTO);
        return new ResponseEntity<>(patientId, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<DoctorDTO>getDoctorById(@PathVariable Long id) throws HsException {
        return new ResponseEntity<>(doctorService.getDoctorById(id),HttpStatus.OK);
    }


    @PostMapping("/update")
    public ResponseEntity<DoctorDTO> updateDoctor(@RequestBody DoctorDTO doctorDTO) throws HsException {
        System.out.println(doctorDTO);
        DoctorDTO updatedDoctorDto=doctorService.updateDoctor(doctorDTO);
        return new ResponseEntity<>(updatedDoctorDto, HttpStatus.ACCEPTED);
    }
}