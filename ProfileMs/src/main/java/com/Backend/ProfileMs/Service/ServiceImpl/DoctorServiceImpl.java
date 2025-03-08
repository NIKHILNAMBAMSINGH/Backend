package com.Backend.ProfileMs.Service.ServiceImpl;

import com.Backend.ProfileMs.DTO.DoctorDTO;
import com.Backend.ProfileMs.DTO.PatientDTO;
import com.Backend.ProfileMs.Entity.Doctor;
import com.Backend.ProfileMs.Exception.HsException;
import com.Backend.ProfileMs.Repository.DoctorRepository;
import com.Backend.ProfileMs.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    @Override
    @Transactional
    public Long addDoctor(DoctorDTO doctorDTO) throws HsException {
        Optional<Doctor> doctorOptional = doctorRepository.findByEmail(doctorDTO.getEmail());

        if (doctorOptional.isPresent()) {
            throw new HsException("Doctor already present: " + doctorDTO.getName());
        }
        System.out.println("111");

        Doctor doctor = new Doctor();
        doctor.setName(doctorDTO.getName());
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setDob(doctorDTO.getDob());
        doctor.setPhone(doctorDTO.getPhone());
        doctor.setAddress(doctorDTO.getAddress());
        doctor.setLicenseNo(doctorDTO.getLicenseNo());
        doctor.setSpecialization(doctorDTO.getSpecialization());
        doctor.setDepartment(doctorDTO.getDepartment());
        doctor.setTotalExp(doctorDTO.getTotalExp());

       Doctor savedDoctor = doctorRepository.save(doctor);
        System.out.println("222");
        if (doctor.getId() == null) {
            throw new HsException("Failed to save doctor, ID is null.");
        }

        return savedDoctor.getId();
    }

    @Override
    public Map<String, Object> getDoctorById(Long id) throws HsException {
        Optional<Doctor> doctorOptional= doctorRepository.findById(id);
       DoctorDTO doctorDTO=doctorOptional.get().toDoctorDTO();

        if (!doctorOptional.isPresent()) {
            throw new HsException("Doctor not found with id: " + id);
        }

        Doctor doctor = doctorOptional.get();

        Map<String, Object> doctorDetailsMap = new LinkedHashMap<>();
        doctorDetailsMap.put("id", doctor.getId());
        doctorDetailsMap.put("name", doctor.getName());
        doctorDetailsMap.put("email", doctor.getEmail());
        doctorDetailsMap.put("dob", doctor.getDob());
        doctorDetailsMap.put("phone", doctor.getPhone());
        doctorDetailsMap.put("address", doctor.getAddress());
        doctorDetailsMap.put("licenseN", doctor.getLicenseNo());
        doctorDetailsMap.put("specializatio", doctor.getSpecialization());
        doctorDetailsMap.put("department", doctor.getDepartment());
        doctorDetailsMap.put("totalExp", doctor.getTotalExp());


        return doctorDetailsMap;
    }

    @Override
    public DoctorDTO updateDoctor(DoctorDTO doctorDTO) throws HsException {
        Doctor doctor = doctorRepository.findById(doctorDTO.getId())
                .orElseThrow(() -> new HsException("Doctor not found"));
        doctor.setName(doctorDTO.getName());
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setDob(doctorDTO.getDob());
        doctor.setPhone(doctorDTO.getPhone());
        doctor.setAddress(doctorDTO.getAddress());
        doctor.setLicenseNo(doctorDTO.getLicenseNo());
        doctor.setSpecialization(doctorDTO.getSpecialization());
        doctor.setDepartment(doctorDTO.getDepartment());
        doctor.setTotalExp(doctorDTO.getTotalExp());

        Doctor updatedDoctor = doctorRepository.save(doctor);

        return new DoctorDTO(
                updatedDoctor.getId(),
                updatedDoctor.getName(),
                updatedDoctor.getEmail(),
                updatedDoctor.getDob(),
                updatedDoctor.getPhone(),
                updatedDoctor.getAddress(),
                updatedDoctor.getLicenseNo(),
                updatedDoctor.getSpecialization(),
                updatedDoctor.getDepartment(),
                updatedDoctor.getTotalExp()
        );
    }
}
