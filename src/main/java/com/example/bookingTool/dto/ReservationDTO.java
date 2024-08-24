package com.example.bookingTool.dto;

import java.io.Serial;
import java.io.Serializable;

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
    private String date;
    private String startTime;
    private String endTime;
    private int noOfPeople;
    private RoomDTO room;
}