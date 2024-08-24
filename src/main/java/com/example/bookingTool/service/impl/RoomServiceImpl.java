package com.example.bookingTool.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.bookingTool.dto.RoomDTO;
import com.example.bookingTool.entity.RoomEntity;
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
        List<RoomEntity> allRooms = roomRepository.findAll();
        List<RoomDTO> processedRooms = new ArrayList<>();
        for (RoomEntity room : allRooms) {
            processedRooms.add(RoomMapper.convertToDTO(room));
        }
        return processedRooms;
    }

       public List<RoomDTO> getRoomByName(String name) {
    	   List<RoomEntity> allRooms = roomRepository.findByName(name);
    	   if(allRooms.isEmpty()) {
    		   throw new CustomGlobalException(Constants.CUSTOM_EXCEPTION_ROOM_NOT_FOUND, 
    				   "Room not found for name: " + name, HttpStatus.NOT_FOUND);
    	   }

    	   List<RoomDTO> processedRooms = new ArrayList<>();
    	   for (RoomEntity room : allRooms) {
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

    public RoomDTO addRoom(RoomDTO roomDto) {
        if (null == roomDto.getRoomName() || roomDto.getRoomName().isEmpty()) {
            throw new CustomGlobalException(Constants.CUSTOM_EXCEPTION_ROOM_NAME_EMPTY, 
            		"Room name must not be blank", HttpStatus.BAD_REQUEST);
        }
        
        if (roomDto.getRoomCapacity() < 3) {
            throw new CustomGlobalException(Constants.CUSTOM_EXCEPTION_ROOM_CAPACITY_EMPTY, 
            		"Minimum Room capacity is 3", HttpStatus.BAD_REQUEST);
        }
        
        Optional<RoomEntity> existingRoom = roomRepository.findByName(roomDto.getRoomName())
                .stream()
                .filter(r -> r.getName().equalsIgnoreCase(roomDto.getRoomName()))
                .findFirst();
        if (existingRoom.isPresent()) {
            throw new CustomGlobalException(Constants.CUSTOM_EXCEPTION_ROOM_ALREADY_EXISTS, 
            		"Room with the name '" + roomDto.getRoomName() + "' already exists", HttpStatus.BAD_REQUEST);
        }
        roomDto.setAvailability(true);
        RoomEntity roomEntity = roomRepository.save(RoomMapper.toEntity(roomDto));
        return RoomMapper.convertToDTO(roomEntity);
    }
}