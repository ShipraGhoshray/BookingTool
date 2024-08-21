package com.example.bookingTool.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO implements Serializable{
	
	@Serial
	private static final long serialVersionUID = 1L;
	
    private long id;
    private LocalDateTime date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private RoomDTO room;
}