package com.example.bookingTool.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.bookingTool.dto.ReservationDTO;
import com.example.bookingTool.entity.ReservationEntity;
import com.example.bookingTool.util.Constants;

public class ReservationMapper {

	public static ReservationDTO convertToDTO(ReservationEntity reservation) {
		ReservationDTO reservationDTO = new ReservationDTO();
		reservationDTO.setId(reservation.getId());
		reservationDTO.setDate(reservation.getDate().format(DateTimeFormatter.ofPattern(Constants.DATE_PATTERN)));
		reservationDTO.setStartTime(reservation.getDate().format(DateTimeFormatter.ofPattern(Constants.TIMESTAMP_PATTERN)));
		reservationDTO.setEndTime(reservation.getDate().format(DateTimeFormatter.ofPattern(Constants.TIMESTAMP_PATTERN)));
		reservationDTO.setRoom(RoomMapper.convertToDTO(reservation.getRoom()));
		return reservationDTO;
	}
	
	public static ReservationEntity toEntity(ReservationDTO reservationDTO) {
		
		ReservationEntity reservation = new ReservationEntity();
		reservation.setNoOfPeople(reservationDTO.getNoOfPeople());
		reservation.setStartTime(LocalDateTime.parse(reservationDTO.getStartTime(), DateTimeFormatter.ofPattern(Constants.TIMESTAMP_PATTERN)));
		reservation.setEndTime(LocalDateTime.parse(reservationDTO.getEndTime(), DateTimeFormatter.ofPattern(Constants.TIMESTAMP_PATTERN)));
		return reservation;
	}
}