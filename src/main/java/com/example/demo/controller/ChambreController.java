package com.example.demo.controller;

import com.example.demo.dto.ReservationRequest;
import com.example.demo.entity.Chambre;
import com.example.demo.repository.ChambreRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chambres")
public class ChambreController {

    @Autowired
    private ChambreRepository chambreRepository;

    // Endpoint to fetch available chambres
    @GetMapping("/available")
    public List<Chambre> getAvailableChambres() {
        return chambreRepository.findByReserved("no");
    }

    // Endpoint to reserve a chambre
    @PostMapping("/reserve")
    public ResponseEntity<String> reserveChambre(@RequestBody ReservationRequest reservationRequest) {
        // Find the chambre by ID
        Chambre chambre = chambreRepository.findById(reservationRequest.getChambreId()).orElse(null);
        if (chambre == null) {
            return new ResponseEntity<>("Chambre not found", HttpStatus.NOT_FOUND);
        }

        // Check if the chambre is already reserved
        if ("yes".equals(chambre.getReserved())) {
            return new ResponseEntity<>("Chambre already reserved", HttpStatus.BAD_REQUEST);
        }

        // Update chambre's reserved status and add reservation details
        chambre.setReserved("yes");
        chambre.setPrenom(reservationRequest.getPrenom());
        chambre.setNom(reservationRequest.getNom());
        chambre.setCin(reservationRequest.getCin());
        chambre.setTel(reservationRequest.getTel());
        chambre.setStartDate(reservationRequest.getStartDate());
        chambre.setEndDate(reservationRequest.getEndDate());

        // Save the updated chambre
        chambreRepository.save(chambre);

        return new ResponseEntity<>("Reservation successful", HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<String> addChambre(@RequestBody Chambre chambre) {
        // Set default reserved status to "no"
        chambre.setReserved("no");
        
        // Save the new chambre
        chambreRepository.save(chambre);
        
        return new ResponseEntity<>("Chambre added successfully", HttpStatus.CREATED);
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChambre(@PathVariable String id) {
        if (!chambreRepository.existsById(id)) {
            return new ResponseEntity<>("Chambre not found", HttpStatus.NOT_FOUND);
        }

        chambreRepository.deleteById(id);
        
        return new ResponseEntity<>("Chambre deleted successfully", HttpStatus.OK);
    }

}
