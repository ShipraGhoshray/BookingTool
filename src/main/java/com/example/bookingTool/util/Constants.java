package com.example.bookingTool.util;

public class Constants {

	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";
    
	public static String CUSTOM_EXCEPTION_INVALID_DATE = "INVALID DATE";
	public static String CUSTOM_EXCEPTION_INVALID_TIMESLOT = "INVALID TIMESLOT";
	public static String CUSTOM_EXCEPTION_INVALID_CAPACITY = "INVALID CAPACITY";
	public static String CUSTOM_EXCEPTION_SLOT_OVERLAP = "SLOT OVERLAP";
	public static String CUSTOM_EXCEPTION_NO_SLOT_AVAIABLE = "NO SLOT AVAIABLE";
	
	public static String CUSTOM_EXCEPTION_ROOM_NAME_EMPTY = "ROOM NAME EMPTY";
	public static String CUSTOM_EXCEPTION_ROOM_CAPACITY_EMPTY = "ROOM CAPACITY EMPTY";
	public static String CUSTOM_EXCEPTION_ROOM_NOT_FOUND = "ROOM NOT FOUND";
	public static String CUSTOM_EXCEPTION_ROOM_ALREADY_EXISTS = "ROOM ALREADY EXISTS";

	public static String RESERVATION_MIN_CAPACITY = "1";
	public static String RESERVATION_MAX_CAPACITY = "20";
}
