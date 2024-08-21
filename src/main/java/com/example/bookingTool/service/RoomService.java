package com.example.bookingTool.service;

import java.util.List;

import com.example.bookingTool.dto.RoomDTO;
import com.example.bookingTool.entity.Room;

public interface RoomService {

	public List<RoomDTO> listRooms();
	public List<RoomDTO> getRoomByName(String name);
	public void deleteRoom(long id);
	public RoomDTO addRoom(Room room);
}