package com.example.bookingTool.mapper;

import com.example.bookingTool.dto.RoomDTO;
import com.example.bookingTool.entity.RoomEntity;

public class RoomMapper {

	private RoomMapper() {
	}

	public static RoomDTO convertToDTO(RoomEntity room) {
		RoomDTO roomDTO = new RoomDTO();
		roomDTO.setRoomId(room.getRoomId());
		roomDTO.setRoomName(room.getName());
		roomDTO.setAvailability(room.isAvailability());
		roomDTO.setRoomCapacity(room.getRoomCapacity());
		return roomDTO;
	}
	
	public static RoomEntity toEntity(RoomDTO roomDTO) {
		RoomEntity roomEntity = new RoomEntity();
		roomEntity.setRoomId(roomDTO.getRoomId());
		roomEntity.setName(roomDTO.getRoomName());
		roomEntity.setAvailability(roomDTO.isAvailability());
		roomEntity.setRoomCapacity(roomDTO.getRoomCapacity());
		return roomEntity;
	}
}