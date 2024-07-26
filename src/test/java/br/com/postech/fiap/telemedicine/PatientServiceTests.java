package br.com.postech.fiap.telemedicine;

import br.com.postech.fiap.telemedicine.dto.CreatePatientUserRequest;
import br.com.postech.fiap.telemedicine.dto.RateDoctorRequest;
import br.com.postech.fiap.telemedicine.entities.Doctor;
import br.com.postech.fiap.telemedicine.entities.Patient;
import br.com.postech.fiap.telemedicine.entities.Rating;
import br.com.postech.fiap.telemedicine.exceptions.HandledException;
import br.com.postech.fiap.telemedicine.exceptions.UnhandledException;
import br.com.postech.fiap.telemedicine.repository.PatientRepository;
import br.com.postech.fiap.telemedicine.service.DoctorService;
import br.com.postech.fiap.telemedicine.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PatientServiceTests {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_found() {
        Patient p = new Patient();
        p.setId(1L);

        when(patientRepository.findById(1L)).thenReturn(Optional.of(p));
        Patient patient = patientService.findById(1L);

        assertTrue(patient.getId() == p.getId());

    }

    @Test
    void findById_not_found() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(HandledException.class, () -> patientService.findById(1L));

    }

    @Test
    void registerPatient_throw_exception() {
        CreatePatientUserRequest requestDTO = new CreatePatientUserRequest();
        requestDTO.setName("Thomaz Palmeira");
        requestDTO.setEmail("pacient@example.com");
        requestDTO.setCpf("02789033200");
        requestDTO.setPassword("12345");

        when(passwordEncoder.encode(requestDTO.getPassword())).thenReturn("PASSWORD");
        when(patientRepository.save(Patient.fromRequest(requestDTO))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(HandledException.class, () -> patientService.registerPatient(requestDTO));

    }

    @Test
    void registerPatient_success() {
        CreatePatientUserRequest requestDTO = new CreatePatientUserRequest();
        requestDTO.setName("Thomaz Palmeira");
        requestDTO.setEmail("pacient@example.com");
        requestDTO.setCpf("02789033200");
        requestDTO.setPassword("12345");

        when(passwordEncoder.encode(requestDTO.getPassword())).thenReturn("PASSWORD");
        when(patientRepository.save(Patient.fromRequest(requestDTO))).thenReturn(Patient.fromRequest(requestDTO));

        Patient p = patientService.registerPatient(requestDTO);

        assertEquals(p.getCpf(), requestDTO.getCpf());

    }

    @Test
    void rateDoctor_handled_exception() {
        RateDoctorRequest rdr = new RateDoctorRequest();
        rdr.setRating(4.4);
        when(doctorService.findById(1L)).thenThrow(HandledException.class);

        assertThrows(HandledException.class, () -> patientService.rateDoctor(1L, rdr));
    }

    @Test
    void rateDoctor_unhandled_exception() {
        RateDoctorRequest rdr = new RateDoctorRequest();
        rdr.setRating(4.4);
        when(doctorService.findById(1L)).thenThrow(NullPointerException.class);

        assertThrows(UnhandledException.class, () -> patientService.rateDoctor(1L, rdr));
    }

    @Test
    void rateDoctor_success() {
        RateDoctorRequest rdr = new RateDoctorRequest();
        rdr.setRating(4.4);

        Doctor d = new Doctor();
        d.setId(1L);
        d.setRatings(Collections.emptyList());

        when(doctorService.findById(1L)).thenReturn(d);

        Rating r = patientService.rateDoctor(1L, rdr);

        assertEquals(4.4, r.getRating());

    }

}
