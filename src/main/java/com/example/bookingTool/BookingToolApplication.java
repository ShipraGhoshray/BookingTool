package com.example.bookingTool;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.bookingTool.dto.MaintainenceDTO;
import com.example.bookingTool.dto.RoomDTO;
import com.example.bookingTool.enums.MaintenanceSlots;
import com.example.bookingTool.service.MaintainenceService;
import com.example.bookingTool.service.RoomService;
import com.example.bookingTool.util.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class BookingToolApplication implements CommandLineRunner{

	@Autowired
	RoomService roomService;
	@Autowired
	MaintainenceService maintainceService;
	
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(Constants.TIMESTAMP_PATTERN);
	
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
	
	private RoomDTO createRoom(long roomId, String roomName, int capacity) {
		return RoomDTO.builder()
        .roomName(roomName)
        .roomCapacity(capacity)
        .availability(true)
        .build();
	}
	
	private void addMaintaince() {
		LocalDate today = LocalDate.now() ;
		log.info("Current date:" + today);
		long i = 1L;
		for (MaintenanceSlots slot : MaintenanceSlots.values()) {
			maintainceService.addMaintainence(new MaintainenceDTO(i++, 
					LocalDateTime.parse((today + " " + slot.getStartTime()), DATE_TIME_FORMATTER), 
					LocalDateTime.parse((today + " " + slot.getEndTime()), DATE_TIME_FORMATTER)));
        }
	}
}
