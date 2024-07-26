package br.com.postech.fiap.telemedicine.controller;

import br.com.postech.fiap.telemedicine.dto.ApiResponse;
import br.com.postech.fiap.telemedicine.dto.RateDoctorRequest;
import br.com.postech.fiap.telemedicine.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @PostMapping("/{doctorId}/rate")
    public ResponseEntity<ApiResponse> updateAppointmentStatus(@PathVariable Long doctorId, @RequestBody RateDoctorRequest rating) {
        ApiResponse response = new ApiResponse(patientService.rateDoctor(doctorId, rating), "Rating created successfully", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
