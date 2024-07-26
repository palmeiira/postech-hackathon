package br.com.postech.fiap.telemedicine.controller;

import br.com.postech.fiap.telemedicine.dto.ApiResponse;
import br.com.postech.fiap.telemedicine.entities.Slot;
import br.com.postech.fiap.telemedicine.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/list")
    public ResponseEntity<Object> list(@RequestParam(required = false) String specialty,
                           @RequestParam(required = false) Double minRating) {
        ApiResponse response = new ApiResponse(doctorService.findDoctorsBySpecialtyOrRating(specialty, minRating), null, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{doctorId}/slots/add")
    public ResponseEntity<Object> addSlots(@PathVariable Long doctorId, @RequestBody List<Slot> slots) {
        ApiResponse response = new ApiResponse(doctorService.addNewSlots(doctorId, slots), "Slot added successfully", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{doctorId}/slots/delete")
    public ResponseEntity<Object> removeSlots(@PathVariable Long doctorId, @RequestBody List<Long> slotIds) {
        ApiResponse response = new ApiResponse(doctorService.deleteSlots(doctorId, slotIds), "Slot removed successfully", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{doctorId}/slots/list")
    public ResponseEntity<Object> listSlots(@PathVariable Long doctorId) {
        ApiResponse response = new ApiResponse(doctorService.findSlots(doctorId), null, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{doctorId}/ratings/list")
    public ResponseEntity<Object> listRatings(@PathVariable Long doctorId) {
        ApiResponse response = new ApiResponse(doctorService.findRatings(doctorId), null, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
