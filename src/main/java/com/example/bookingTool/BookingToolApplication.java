package com.example.bookingTool;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.bookingTool.entity.Maintainence;
import com.example.bookingTool.entity.Room;
import com.example.bookingTool.enums.MaintenanceSlots;
import com.example.bookingTool.service.MaintainenceService;
import com.example.bookingTool.service.RoomService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SpringBootApplication
public class BookingToolApplication implements CommandLineRunner{

	@Autowired
	RoomService roomService;
	@Autowired
	MaintainenceService maintainceService;
	
	private static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN);
	   
	public static void main(String[] args){
		SpringApplication.run(BookingToolApplication.class, args);
	}
	
	public void run(String... args) throws Exception {
		addRoom();
		addMaintaince();
	}
	
	private void addRoom() {
		roomService.addRoom(createRoom(1L, "Amaze", 3));
		roomService.addRoom(createRoom(2L, "Beauty", 7));
		roomService.addRoom(createRoom(3L, "Inspire", 12));
		roomService.addRoom(createRoom(4L, "Strive", 20));
	}
	
	private Room createRoom(long roomId, String roomName, int capacity) {
		return new Room(roomId, roomName, true, capacity, null);
	}
	
	private void addMaintaince() {
		LocalDate today = LocalDate.now() ;
		long i = 1L;
		for (MaintenanceSlots slot : MaintenanceSlots.values()) {
			maintainceService.addMaintainence(new Maintainence(i++, 
					LocalDateTime.parse((today + " " + slot.getStartTime()), DATE_TIME_FORMATTER), 
					LocalDateTime.parse((today + " " + slot.getEndTime()), DATE_TIME_FORMATTER)));
        }
	}
}
