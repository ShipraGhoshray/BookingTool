package com.example.bookingTool.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.bookingTool.dto.RoomDTO;
import com.example.bookingTool.entity.Room;
import com.example.bookingTool.handler.CustomGlobalException;
import com.example.bookingTool.mapper.RoomMapper;
import com.example.bookingTool.repository.RoomRepository;
import com.example.bookingTool.service.RoomService;
import com.example.bookingTool.util.Constants;

@Service
public class RoomServiceImpl implements RoomService{

    @Autowired
    RoomRepository roomRepository;

       public List<RoomDTO> listRooms() {
        List<Room> allRooms = roomRepository.findAll();
        List<RoomDTO> processedRooms = new ArrayList<>();
        for (Room room : allRooms) {
            processedRooms.add(RoomMapper.convertToDTO(room));
        }
        return processedRooms;
    }

       public List<RoomDTO> getRoomByName(String name) {
    	   List<Room> allRooms = roomRepository.findByName(name);
    	   if(allRooms.isEmpty()) {
    		   throw new CustomGlobalException(Constants.CUSTOM_EXCEPTION_ROOM_NOT_FOUND, 
    				   "Room not found for name: " + name, HttpStatus.BAD_REQUEST);
    	   }

    	   List<RoomDTO> processedRooms = new ArrayList<>();
    	   for (Room room : allRooms) {
    		   processedRooms.add(RoomMapper.convertToDTO(room));
    	   }
    	   return processedRooms;
       }

    public void deleteRoom(long id) {
        if (!roomRepository.existsById(id)) {
        	throw new CustomGlobalException(Constants.CUSTOM_EXCEPTION_ROOM_NOT_FOUND,
        			"Room not found for ID: " + id, HttpStatus.BAD_REQUEST);            
        }
        roomRepository.deleteById(id);
    }

    public RoomDTO addRoom(Room room) {
        String roomName = room.getName().toLowerCase();
        Optional<Room> existingRoom = roomRepository.findByName(roomName)
                .stream()
                .filter(r -> r.getName().equalsIgnoreCase(roomName))
                .findFirst();
        if (existingRoom.isPresent()) {
            throw new CustomGlobalException(Constants.CUSTOM_EXCEPTION_ROOM_ALREADY_EXISTS, 
            		"Room with the name '" + room.getName() + "' already exists", HttpStatus.BAD_REQUEST);
        }
        room.setName(roomName);
        room.setAvailability(true);
        Room roomEntity = roomRepository.save(room);
        return RoomMapper.convertToDTO(roomEntity);
    }
}