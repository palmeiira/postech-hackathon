package br.com.postech.fiap.telemedicine.service;

import br.com.postech.fiap.telemedicine.dto.CreateDoctorUserRequest;
import br.com.postech.fiap.telemedicine.entities.Doctor;
import br.com.postech.fiap.telemedicine.entities.Rating;
import br.com.postech.fiap.telemedicine.entities.Slot;
import br.com.postech.fiap.telemedicine.exceptions.HandledException;
import br.com.postech.fiap.telemedicine.exceptions.UnhandledException;
import br.com.postech.fiap.telemedicine.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Doctor registerDoctor(CreateDoctorUserRequest doctor) {
        Doctor d = Doctor.fromRequest(doctor);
        String encodedPassword = passwordEncoder.encode(doctor.getPassword());
        d.setPassword(encodedPassword);
        try {
            return doctorRepository.save(d);
        } catch (DataIntegrityViolationException dive) {
            throw new HandledException("Failed to obey constraints. Check register.");
        }
    }

    public Doctor findById(Long id) {
        Optional<Doctor> opt = doctorRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new HandledException("Couldn't find Doctor with ID = " + id);

    }

    public List<Doctor> findDoctorsBySpecialtyOrRating(String specialty, Double minRating) {
        try {
            if (specialty == null && minRating == null) {
                return doctorRepository.findAll();
            }
            return doctorRepository.findDoctorsBySpecialtyOrRating(specialty, minRating);
        } catch (Exception e) {
            throw new UnhandledException(e.getMessage(), e.getCause());
        }
    }

    public List<Slot> findSlots(Long doctorId) {
        try {
            Doctor doctor = findById(doctorId);
            return doctor.getSlots();
        } catch (HandledException he) {
            throw he;
        } catch (Exception e) {
            throw new UnhandledException(e.getMessage(), e.getCause());
        }
    }

    public Doctor addNewSlots(Long doctorId, List<Slot> slots) {
        try {
            Doctor doctor = findById(doctorId);
            for (Slot slot : slots) {
                doctor.addSlot(slot);
            }
            return doctorRepository.save(doctor);
        } catch (HandledException he) {
            throw he;
        } catch (Exception e) {
            throw new UnhandledException(e.getMessage(), e.getCause());
        }
    }

    public Doctor addNewRating(Long doctorId, Rating r) {
        try {
            System.out.println("Test");
            Doctor doctor = findById(doctorId);
            doctor.addRating(r);
            r.setDoctor(doctor);
            return doctorRepository.save(doctor);
        } catch (HandledException he) {
            throw he;
        } catch (Exception e) {
            throw new UnhandledException(e.getMessage(), e.getCause());
        }
    }

    public Doctor deleteSlots(Long doctorId, List<Long> slotIds) {
        try {
            Doctor doctor = findById(doctorId);
            for (Slot slot : doctor.getSlots()) {
                if (slotIds.contains(slot.getId())) {
                    doctor.removeSlot(slot);
                }
            }
            return doctorRepository.save(doctor);
        } catch (HandledException he) {
            throw he;
        } catch (Exception e) {
            throw new UnhandledException(e.getMessage(), e.getCause());
        }
    }

    public List<Rating> findRatings(Long doctorId) {
        try {
            Doctor doctor = findById(doctorId);
            return doctor.getRatings();
        } catch (HandledException he) {
            throw he;
        } catch (Exception e) {
            throw new UnhandledException(e.getMessage(), e.getCause());
        }
    }
}
