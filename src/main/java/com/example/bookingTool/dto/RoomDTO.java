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
public class RoomDTO implements Serializable{
	
	@Serial
	private static final long serialVersionUID = 1L;
    private long roomId;
	private String roomName;
    private boolean availability;
    private Integer roomCapacity;
}