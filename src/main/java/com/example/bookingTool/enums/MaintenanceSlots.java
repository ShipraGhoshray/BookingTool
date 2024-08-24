package com.example.bookingTool.enums;

import lombok.Getter;

@Getter
public enum MaintenanceSlots {

	A("09:00:00", "09:15:00"),
	B("13:00:00", "13:15:00"),
	C("17:00:00", "17:15:00");

	private final String startTime;
	private final String endTime;
	
	MaintenanceSlots(String startTime, String endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getStartTime() {
		return startTime;
	}
	public String getEndTime() {
		return endTime;
	}
}