package com.example.bookingTool.mapper;

import com.example.bookingTool.dto.RoomDTO;
import com.example.bookingTool.entity.Room;

public class RoomMapper {

	private RoomMapper() {
	}

	public static RoomDTO convertToDTO(Room room) {
		RoomDTO roomDTO = new RoomDTO();
		roomDTO.setRoomId(room.getRoomId());
		roomDTO.setRoomName(room.getName());
		roomDTO.setAvailability(room.isAvailability());
		roomDTO.setRoomCapacity(room.getRoomCapacity());
		return roomDTO;
	}
}