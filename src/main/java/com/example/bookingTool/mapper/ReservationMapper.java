package com.example.bookingTool.mapper;

import com.example.bookingTool.dto.ReservationDTO;
import com.example.bookingTool.entity.Reservation;

public class ReservationMapper {

	public static ReservationDTO convertToDTO(Reservation reservation) {
		ReservationDTO reservationDTO = new ReservationDTO();
		reservationDTO.setId(reservation.getId());
		reservationDTO.setDate(reservation.getDate());
		reservationDTO.setStartTime(reservation.getStartTime());
		reservationDTO.setEndTime(reservation.getEndTime());
		reservationDTO.setRoom(RoomMapper.convertToDTO(reservation.getRoom()));
		return reservationDTO;
	}
}