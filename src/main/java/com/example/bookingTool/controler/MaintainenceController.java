package com.example.bookingTool.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingTool.dto.MaintainenceDTO;
import com.example.bookingTool.entity.Maintainence;
import com.example.bookingTool.service.MaintainenceService;

@RestController
@RequestMapping("/api/v1/maintainence")
public class MaintainenceController {

    @Autowired
    MaintainenceService maintainenceService;

    @GetMapping()
    public ResponseEntity<List<MaintainenceDTO>> listMaintainenceSlots() {
        return ResponseEntity.ok(maintainenceService.listMaintainenceSlots());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintainenceDTO> getMaintainenceBySlotId(@PathVariable long id) {
        return ResponseEntity.ok(maintainenceService.getMaintainenceById(id));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<MaintainenceDTO> addMaintainence(@RequestBody Maintainence maintainence) {
        return ResponseEntity.status(HttpStatus.CREATED).body(maintainenceService.addMaintainence(maintainence));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintainence(@PathVariable long id) {
    	maintainenceService.deleteMaintainence(id);
        return ResponseEntity.ok().build();
    }
}
