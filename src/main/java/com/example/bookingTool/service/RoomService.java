package com.example.bookingTool.service;

import java.util.List;

import com.example.bookingTool.dto.RoomDTO;

public interface RoomService {

	public List<RoomDTO> listRooms();
	public List<RoomDTO> getRoomByName(String name);
	public void deleteRoom(long id);
	public RoomDTO addRoom(RoomDTO room);
}