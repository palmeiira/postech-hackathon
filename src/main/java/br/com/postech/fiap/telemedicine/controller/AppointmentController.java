package br.com.postech.fiap.telemedicine.controller;

import br.com.postech.fiap.telemedicine.dto.ApiResponse;
import br.com.postech.fiap.telemedicine.dto.ConfirmAppointmentRequest;
import br.com.postech.fiap.telemedicine.dto.CreateAppointmentRequest;
import br.com.postech.fiap.telemedicine.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/request")
    public ResponseEntity<ApiResponse> createAppointment(@RequestBody CreateAppointmentRequest dto) {
        ApiResponse response = new ApiResponse(appointmentService.registerAppointment(dto), null, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{appointmentId}/confirm")
    public ResponseEntity<ApiResponse> updateAppointmentStatus(@PathVariable Long appointmentId, @RequestBody ConfirmAppointmentRequest status) {
        System.out.println("Mock");
        ApiResponse response = new ApiResponse(appointmentService.updateAppointmentStatus(appointmentId, status), null, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
