package br.com.postech.fiap.telemedicine.service;

import br.com.postech.fiap.telemedicine.dto.ConfirmAppointmentRequest;
import br.com.postech.fiap.telemedicine.dto.CreateAppointmentRequest;
import br.com.postech.fiap.telemedicine.enums.AppointmentStatus;
import br.com.postech.fiap.telemedicine.enums.SlotStatus;
import br.com.postech.fiap.telemedicine.entities.*;
import br.com.postech.fiap.telemedicine.exceptions.HandledException;
import br.com.postech.fiap.telemedicine.exceptions.UnhandledException;
import br.com.postech.fiap.telemedicine.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private SlotService slotService;

    @Autowired
    RatingService ratingService;

    public Appointment updateAppointmentStatus(Long appointmentId, ConfirmAppointmentRequest dto) {
        AppointmentStatus status = dto.getStatus();

        try {
            Appointment appointment = findById(appointmentId);
            appointment.setStatus(status);

            if (status == AppointmentStatus.CANCELED) {
                appointment.getSlot().setStatus(SlotStatus.FREE);
            } else if (status == AppointmentStatus.CONFIRMED) {
                appointment.getSlot().setStatus(SlotStatus.SCHEDULED);
                appointment.setMeetingLink(UUID.randomUUID().toString());
            } else {
                throw new HandledException("Invalid status while updating Appointment.");
            }

            slotService.save(appointment.getSlot());
            return appointmentRepository.save(appointment);
        } catch (HandledException he) {
            throw he;
        } catch (Exception e) {
            throw new UnhandledException(e.getMessage(), e.getCause());
        }
    }

    public Appointment findById(Long id) {
        Optional<Appointment> opt = appointmentRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new HandledException("Couldn't find Appointment with ID = " + id);
    }

    public Appointment registerAppointment(CreateAppointmentRequest dto) {
        try {
            Doctor d = doctorService.findById(dto.getDoctorId());
            Patient p = patientService.findById(dto.getPatientId());
            Slot s = slotService.findById(dto.getSlotId());
            Appointment ap = new Appointment(null, d, p, s, AppointmentStatus.SCHEDULED, null);
            s.setStatus(SlotStatus.WAITING_CONFIRMATION);
            slotService.save(s);

            return appointmentRepository.save(ap);
        } catch (HandledException he) {
            throw he;
        } catch (Exception e) {
            throw new UnhandledException(e.getMessage(), e.getCause());
        }
    }

    public List<Appointment> findAppointments(Long doctorId, Long patientId) {
        if (doctorId == null && patientId == null) {
            throw new HandledException("At least one parameter is necessary.");
        }

        if (doctorId != null) {
                return appointmentRepository.findByDoctorId(doctorId);
        } else {
            return appointmentRepository.findByPatientId(patientId);
        }
    }
}
