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

import com.example.bookingTool.dto.RoomDTO;
import com.example.bookingTool.service.RoomService;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    @Autowired
    RoomService roomService;

    @GetMapping()
    public ResponseEntity<List<RoomDTO>> listRooms() {
        return ResponseEntity.ok(roomService.listRooms());
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<RoomDTO>> getRoomByName(@PathVariable String name) {
        return ResponseEntity.ok(roomService.getRoomByName(name));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<RoomDTO> addRoom(@RequestBody RoomDTO roomDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.addRoom(roomDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok().build();
    }
}
