package br.com.postech.fiap.telemedicine.service;

import br.com.postech.fiap.telemedicine.dto.CreatePatientUserRequest;
import br.com.postech.fiap.telemedicine.dto.RateDoctorRequest;
import br.com.postech.fiap.telemedicine.entities.Doctor;
import br.com.postech.fiap.telemedicine.entities.Patient;
import br.com.postech.fiap.telemedicine.entities.Rating;
import br.com.postech.fiap.telemedicine.exceptions.HandledException;
import br.com.postech.fiap.telemedicine.exceptions.UnhandledException;
import br.com.postech.fiap.telemedicine.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Patient findById(Long id) {
        Optional<Patient> opt = patientRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new HandledException("Couldn't find Patient with ID = " + id);
    }

    public Patient registerPatient(CreatePatientUserRequest patient) {
        Patient p = Patient.fromRequest(patient);
        String encodedPassword = passwordEncoder.encode(patient.getPassword());
        p.setPassword(encodedPassword);
        try {
            return patientRepository.save(p);
        } catch (DataIntegrityViolationException dive) {
            throw new HandledException("Failed to obey constraints. Check register.");
        }
    }

    public Rating rateDoctor(Long doctorId, RateDoctorRequest dto) {
        try {
            Doctor d = doctorService.findById(doctorId);
            Rating r = Rating.fromRequest(dto);
            doctorService.addNewRating(d.getId(), r);
            return r;
        } catch (HandledException he) {
            throw he;
        } catch (Exception e) {
            throw new UnhandledException(e.getMessage(), e.getCause());
        }
    }
}
