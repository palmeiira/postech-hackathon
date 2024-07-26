package br.com.postech.fiap.telemedicine;

import br.com.postech.fiap.telemedicine.dto.CreateAppointmentRequest;
import br.com.postech.fiap.telemedicine.entities.Appointment;
import br.com.postech.fiap.telemedicine.entities.Doctor;
import br.com.postech.fiap.telemedicine.entities.Patient;
import br.com.postech.fiap.telemedicine.entities.Slot;
import br.com.postech.fiap.telemedicine.enums.AppointmentStatus;
import br.com.postech.fiap.telemedicine.enums.SlotStatus;
import br.com.postech.fiap.telemedicine.exceptions.HandledException;
import br.com.postech.fiap.telemedicine.exceptions.UnhandledException;
import br.com.postech.fiap.telemedicine.repository.AppointmentRepository;
import br.com.postech.fiap.telemedicine.service.AppointmentService;
import br.com.postech.fiap.telemedicine.service.DoctorService;
import br.com.postech.fiap.telemedicine.service.PatientService;
import br.com.postech.fiap.telemedicine.service.SlotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.print.Doc;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AppointmentServiceTests {
    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorService doctorService;

    @Mock
    private PatientService patientService;

    @Mock
    private SlotService slotService;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_found() {
        Appointment ap = new Appointment();
        ap.setId(1L);

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(ap));
        Appointment patient = appointmentService.findById(1L);

        assertTrue(patient.getId() == ap.getId());
    }

    @Test
    void findById_not_found() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(HandledException.class, () -> appointmentService.findById(1L));
    }

    @Test
    void registerAppointment_success() {
        // Arrange
        CreateAppointmentRequest request = new CreateAppointmentRequest();
        request.setDoctorId(1L);
        request.setPatientId(1L);
        request.setSlotId(1L);

        Doctor doctor = new Doctor();
        Patient patient = new Patient();
        Slot slot = new Slot();
        slot.setStatus(SlotStatus.FREE); // Estado inicial do slot

        when(doctorService.findById(1L)).thenReturn(doctor);
        when(patientService.findById(1L)).thenReturn(patient);
        when(slotService.findById(1L)).thenReturn(slot);
        when(appointmentRepository.save(any(Appointment.class))).thenAnswer(invocation -> invocation.getArguments()[0]);
//        when(slotService.save(any(Slot.class))).thenAnswer(invocation -> invocation.getArguments()[0]);

        // Act
        Appointment result = appointmentService.registerAppointment(request);

        // Assert
        assertNotNull(result);
        assertEquals(AppointmentStatus.SCHEDULED, result.getStatus());
        assertEquals(SlotStatus.WAITING_CONFIRMATION, slot.getStatus());
        verify(doctorService).findById(1L);
        verify(patientService).findById(1L);
        verify(slotService).findById(1L);
        verify(appointmentRepository).save(any(Appointment.class));
        verify(slotService).save(slot);
    }

    @Test
    void registerAppointment_handled_exception() {
        CreateAppointmentRequest dto = new CreateAppointmentRequest();
        dto.setDoctorId(1L);
        dto.setPatientId(1L);
        dto.setSlotId(1L);
        when(doctorService.findById(1L)).thenThrow(HandledException.class);
        assertThrows(HandledException.class, () -> appointmentService.registerAppointment(dto));
    }

    @Test
    void registerAppointment_unhandled_exception() {
        CreateAppointmentRequest dto = new CreateAppointmentRequest();
        dto.setDoctorId(1L);
        dto.setPatientId(1L);
        dto.setSlotId(1L);
        when(doctorService.findById(1L)).thenThrow(NullPointerException.class);
        assertThrows(UnhandledException.class, () -> appointmentService.registerAppointment(dto));
    }
}
