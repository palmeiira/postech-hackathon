package br.com.postech.fiap.telemedicine.service;

import br.com.postech.fiap.telemedicine.dto.CreateDoctorUserRequest;
import br.com.postech.fiap.telemedicine.dto.CreatePatientUserRequest;
import br.com.postech.fiap.telemedicine.dto.LoginRequest;
import br.com.postech.fiap.telemedicine.dto.LoginResponse;
import br.com.postech.fiap.telemedicine.entities.Doctor;
import br.com.postech.fiap.telemedicine.entities.Patient;
import br.com.postech.fiap.telemedicine.exceptions.HandledException;
import br.com.postech.fiap.telemedicine.exceptions.UnhandledException;
import br.com.postech.fiap.telemedicine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AuthenticationService authenticationService;

    public LoginResponse authenticate(LoginRequest login) {
        return authenticationService.authenticate(login);
    }

    public Doctor registerDoctor(CreateDoctorUserRequest user) {
        try {
            return doctorService.registerDoctor(user);
        } catch (HandledException he) {
            throw he;
        } catch (Exception e) {
            throw new UnhandledException(e.getMessage(), e.getCause());
        }
    }

    public Patient registerPatient(CreatePatientUserRequest user) {
        try {
            return patientService.registerPatient(user);
        } catch (HandledException he) {
            throw he;
        } catch (Exception e) {
            throw new UnhandledException(e.getMessage(), e.getCause());
        }
    }
}
