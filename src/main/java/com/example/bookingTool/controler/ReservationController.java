package com.example.bookingTool.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingTool.dto.ReservationDTO;
import com.example.bookingTool.service.ReservationService;

@RestController
@RequestMapping("/api/v1/reservation")
@CrossOrigin(origins="*")
public class ReservationController {
    @Autowired
    ReservationService reservationService;

    @GetMapping()
    public ResponseEntity<List<ReservationDTO>> listReservations() {
    	return ResponseEntity.ok(reservationService.listReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable long id) {
    	 return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ReservationDTO> addReservation(@RequestBody ReservationDTO reservation) {
    	return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.addReservation(reservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
