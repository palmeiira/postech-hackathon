package br.com.postech.fiap.telemedicine;

import br.com.postech.fiap.telemedicine.dto.CreateDoctorUserRequest;
import br.com.postech.fiap.telemedicine.dto.CreatePatientUserRequest;
import br.com.postech.fiap.telemedicine.entities.Doctor;
import br.com.postech.fiap.telemedicine.entities.Patient;
import br.com.postech.fiap.telemedicine.entities.Rating;
import br.com.postech.fiap.telemedicine.entities.Slot;
import br.com.postech.fiap.telemedicine.exceptions.HandledException;
import br.com.postech.fiap.telemedicine.exceptions.UnhandledException;
import br.com.postech.fiap.telemedicine.repository.DoctorRepository;
import br.com.postech.fiap.telemedicine.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DoctorServiceTests {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DoctorService doctorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findBySpecialtyOrRating_no_one_found() {
        String specialty = "";
        Double minRating = 1.0;

        when(doctorRepository.findDoctorsBySpecialtyOrRating(specialty, minRating)).thenReturn(Collections.emptyList());
        List<Doctor> doctors = doctorService.findDoctorsBySpecialtyOrRating(specialty, minRating);

        assertTrue(doctors.isEmpty());

    }

    @Test
    void findBySpecialtyOrRating_throw_exception() {
        String specialty = "Dermatologista";
        Double minRating = 1.0;

        when(doctorRepository.findDoctorsBySpecialtyOrRating(specialty, minRating))
                .thenThrow(new RuntimeException("Database error"));

        UnhandledException exception = assertThrows(
                UnhandledException.class,
                () -> doctorService.findDoctorsBySpecialtyOrRating(specialty, minRating)
        );

        assertEquals("Database error", exception.getMessage());
    }

    @Test
    void findBySpecialtyOrRating_found_by_specialty() {
        String specialty = "Dermatologista";

        Doctor d1 = new Doctor();
        d1.setId(1L);
        d1.setSpecialty(specialty);
        d1.setEmail("thomaz.palmeira@example.com");
        d1.setRatings(null);
        d1.setSlots(null);

        Doctor d2 = new Doctor();
        d2.setId(1L);
        d2.setSpecialty("Cardio");
        d2.setEmail("thomaz.palmeira2@example.com");
        d2.setRatings(null);
        d2.setSlots(null);

        when(doctorRepository.findDoctorsBySpecialtyOrRating(specialty, null))
                .thenReturn(List.of(d1));

        List<Doctor> doctors = doctorService.findDoctorsBySpecialtyOrRating(specialty, null);

        assertEquals(1, doctors.size());
    }

    @Test
    void findBySpecialtyOrRating_find_all() {
        String specialty = "Dermatologista";

        Doctor d1 = new Doctor();
        d1.setId(1L);
        d1.setSpecialty(specialty);
        d1.setEmail("thomaz.palmeira@example.com");
        d1.setRatings(null);
        d1.setSlots(null);

        Doctor d2 = new Doctor();
        d2.setId(1L);
        d2.setSpecialty("Cardio");
        d2.setEmail("thomaz.palmeira2@example.com");
        d2.setRatings(null);
        d2.setSlots(null);

        when(doctorRepository.findAll())
                .thenReturn(List.of(d1, d2));

        List<Doctor> doctors = doctorService.findDoctorsBySpecialtyOrRating(null, null);

        assertEquals(2, doctors.size());
    }

    @Test
    void findById_found() {
        Doctor d1 = new Doctor();
        d1.setId(1L);
        d1.setSpecialty("Dermatologista");
        d1.setEmail("thomaz.palmeira@example.com");
        d1.setRatings(null);
        d1.setSlots(null);

        when(doctorRepository.findById(1L))
                .thenReturn(Optional.of(d1));

        Doctor doctor = doctorService.findById(1L);

        assertEquals(1L, doctor.getId());
    }

    @Test
    void findById_not_found() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        HandledException exception = assertThrows(
                HandledException.class,
                () -> doctorService.findById(1L)
        );

        assertEquals("Couldn't find Doctor with ID = 1"  , exception.getMessage());
    }

    @Test
    void findSlots_found() {
        Doctor d1 = new Doctor();
        d1.setId(1L);
        d1.setSpecialty("specialty");
        d1.setEmail("thomaz.palmeira@example.com");
        d1.setRatings(null);
        d1.setSlots(null);

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(d1));

        List<Slot> slots = doctorService.findSlots(1L);

        assertNull(slots);
    }

    @Test
    void findSlots_handled_exception() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        HandledException exception = assertThrows(
                HandledException.class,
                () -> doctorService.findSlots(1L)
        );

        assertEquals("Couldn't find Doctor with ID = 1"  , exception.getMessage());
    }

    @Test
    void findSlots_unhandled_exception() {
        when(doctorRepository.findById(1L)).thenThrow(NullPointerException.class);

        assertThrows(
                UnhandledException.class,
                () -> doctorService.findSlots(1L)
        );
    }

    @Test
    void findRatings_found() {
        Doctor d1 = new Doctor();
        d1.setId(1L);
        d1.setSpecialty("specialty");
        d1.setEmail("thomaz.palmeira@example.com");
        d1.setRatings(null);
        d1.setSlots(null);

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(d1));

        List<Rating> ratings = doctorService.findRatings(1L);

        assertNull(ratings);
    }

    @Test
    void findRatings_handled_exception() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        HandledException exception = assertThrows(
                HandledException.class,
                () -> doctorService.findRatings(1L)
        );

        assertEquals("Couldn't find Doctor with ID = 1"  , exception.getMessage());
    }

    @Test
    void findRatings_unhandled_exception() {
        when(doctorRepository.findById(1L)).thenThrow(NullPointerException.class);

        assertThrows(
                UnhandledException.class,
                () -> doctorService.findRatings(1L)
        );
    }

    @Test
    void addNewSlots_success() {
        Doctor d1 = new Doctor();
        d1.setId(1L);
        d1.setSpecialty("specialty");
        d1.setEmail("thomaz.palmeira@example.com");

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(d1));
        when(doctorRepository.save(d1)).thenReturn(d1);

        Doctor d = doctorService.addNewSlots(1L, Collections.emptyList());

        assertEquals(d.getId(), d1.getId());
    }

    @Test
    void addNewSlots_handled_exception() {
        when(doctorRepository.findById(1L)).thenThrow(HandledException.class);

        assertThrows(
                HandledException.class,
                () -> doctorService.addNewSlots(1L, null)
        );
    }

    @Test
    void addNewSlots_unhandled_exception() {
        when(doctorRepository.findById(1L)).thenThrow(NullPointerException.class);

        assertThrows(
                UnhandledException.class,
                () -> doctorService.addNewSlots(1L, null)
        );
    }

    @Test
    void addNewRating_handled_exception() {
        when(doctorRepository.findById(1L)).thenThrow(HandledException.class);

        assertThrows(
                HandledException.class,
                () -> doctorService.addNewRating(1L, null)
        );
    }

    @Test
    void addNewRating_unhandled_exception() {
        when(doctorRepository.findById(1L)).thenThrow(NullPointerException.class);

        assertThrows(
                UnhandledException.class,
                () -> doctorService.addNewRating(1L, null)
        );
    }

    @Test
    void registerPatient_throw_exception() {
        CreateDoctorUserRequest requestDTO = new CreateDoctorUserRequest();
        requestDTO.setName("Thomaz Palmeira");
        requestDTO.setEmail("pacient@example.com");
        requestDTO.setCrm("027890330");
        requestDTO.setPassword("12345");

        when(passwordEncoder.encode(requestDTO.getPassword())).thenReturn("PASSWORD");
        when(doctorRepository.save(Doctor.fromRequest(requestDTO))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(HandledException.class, () -> doctorService.registerDoctor(requestDTO));

    }

    @Test
    void registerPatient_success() {
        CreateDoctorUserRequest requestDTO = new CreateDoctorUserRequest();
        requestDTO.setName("Thomaz Palmeira");
        requestDTO.setEmail("pacient@example.com");
        requestDTO.setCrm("027890330");
        requestDTO.setPassword("12345");

        when(passwordEncoder.encode(requestDTO.getPassword())).thenReturn("PASSWORD");
        when(doctorRepository.save(Doctor.fromRequest(requestDTO))).thenReturn(Doctor.fromRequest(requestDTO));

        Doctor p = doctorService.registerDoctor(requestDTO);

        assertEquals(p.getCrm(), requestDTO.getCrm());

    }

    @Test
    void deleteSlots_handled_exception() {
        when(doctorRepository.findById(1L)).thenThrow(HandledException.class);

        assertThrows(
                HandledException.class,
                () -> doctorService.deleteSlots(1L, null)
        );
    }

    @Test
    void deleteSlots_unhandled_exception() {
        when(doctorRepository.findById(1L)).thenThrow(NullPointerException.class);

        assertThrows(
                UnhandledException.class,
                () -> doctorService.deleteSlots(1L, null)
        );
    }

}
