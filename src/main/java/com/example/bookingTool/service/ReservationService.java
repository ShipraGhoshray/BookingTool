package com.example.bookingTool.service;

import java.util.List;

import com.example.bookingTool.dto.ReservationDTO;
import com.example.bookingTool.entity.Reservation;

public interface ReservationService {

    public List<ReservationDTO> listReservations();
    public ReservationDTO getReservationById(long id);
    public void deleteReservation(long id);
    public ReservationDTO addReservation(Reservation reservation);
}