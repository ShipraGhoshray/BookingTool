package com.example.BookingTool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.bookingTool.dto.ReservationDTO;
import com.example.bookingTool.entity.Reservation;
import com.example.bookingTool.entity.Room;
import com.example.bookingTool.mapper.ReservationMapper;
import com.example.bookingTool.repository.ReservationRepository;
import com.example.bookingTool.service.ReservationService;

public class ReservationTests {

    /*@Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void convertToDTOShouldConvertReservationToDTO() {
        // Given
        Room room = new Room();
        room.setRoomId(1);
        room.setName("Test Room");
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setDate(LocalDateTime.of(2024, 8, 20, 0, 0, 0, 0));
        reservation.setStartTime(LocalDateTime.of(2024, 8, 20, 0, 0, 0, 0));
        reservation.setEndTime(LocalDateTime.of(2024, 8, 20, 0, 0, 0, 0));
        reservation.setRoom(room);
        // When
        ReservationDTO result = ReservationMapper.convertToDTO(reservation);
        // Then
        assertEquals(1, result.getId());
        assertEquals("ABC123", result.getIdentifier());
        assertEquals(LocalDate.of(2024, 8, 20), result.getDate());
        assertEquals(LocalTime.of(10, 0), result.getStartTime());
        assertEquals(LocalTime.of(12, 0), result.getEndTime());
        assertEquals("Test Room", result.getRoomName());
    }

    @Test
    public void listReservationsShouldReturnListOfReservationDTOs() {
        // Given
        Room room = new Room();
        room.setRoomId(1);
        room.setName("Test Room");
        Reservation reservation1 = new Reservation();
        reservation1.setId(1);
        reservation1.setDate(LocalDateTime.of(2024, 8, 20, 0, 0, 0, 0));
        reservation1.setStartTime(LocalDateTime.of(2024, 8, 20, 0, 0, 0, 0));
        reservation1.setEndTime(LocalDateTime.of(2024, 8, 20, 0, 0, 0, 0));
        reservation1.setRoom(room);

        Reservation reservation2 = new Reservation();
        reservation2.setId(2);
        reservation2.setDate(LocalDateTime.of(2024, 8, 20, 0, 0, 0, 0));
        reservation2.setStartTime(LocalDateTime.of(2024, 8, 20, 0, 0, 0, 0));
        reservation2.setEndTime(LocalDateTime.of(2024, 8, 20, 0, 0, 0, 0));
        reservation2.setRoom(room);

        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation1, reservation2));
        // When
        List<ReservationDTO> result = reservationService.listReservations();

        // Then
        assertEquals(2, result.size());

        ReservationDTO dto1 = result.get(0);
        assertEquals(1, dto1.getId());
        assertEquals("ABC123", dto1.getIdentifier());
        assertEquals(LocalDate.of(2023, 6, 4), dto1.getDate());
        assertEquals(LocalTime.of(10, 0), dto1.getStartTime());
        assertEquals(LocalTime.of(12, 0), dto1.getEndTime());
        assertEquals("Test Room", dto1.getRoomName());

        ReservationDTO dto2 = result.get(1);
        assertEquals(2, dto2.getId());
        assertEquals("DEF456", dto2.getIdentifier());
        assertEquals(LocalDate.of(2023, 6, 5), dto2.getDate());
        assertEquals(LocalTime.of(14, 0), dto2.getStartTime());
        assertEquals(LocalTime.of(16, 0), dto2.getEndTime());
        assertEquals("Test Room", dto2.getRoomName());
    }
    
    @Test
    public void getReservationByIdShouldReturnEmptyOptionalWhenNotFound() {
        // Given
        long id = 1;
        when(reservationRepository.findById(id)).thenReturn(Optional.empty());
        // When
        Optional<Reservation> result = reservationService.getReservationById(id);
        // Then
        verify(reservationRepository).findById(id);
        assertFalse(result.isPresent());
    }

    @Test
    public void getReservationByIdShouldReturnReservationOptionalWhenFound() {
        // Given
        long id = 1;
        Reservation reservation = new Reservation();
        reservation.setId(id);
        when(reservationRepository.findById(id)).thenReturn(Optional.of(reservation));
        // When
        Optional<Reservation> result = reservationService.getReservationById(id);
        // Then
        verify(reservationRepository).findById(id);
        assertTrue(result.isPresent());
        assertEquals(reservation, result.get());
    }

    @Test
    void deleteReservationShouldDeleteReservationWhenFound() {
        // Given
        long id = 1;
        Reservation reservation = new Reservation();
        reservation.setId(id);
        when(reservationRepository.existsById(id)).thenReturn(true);
        // When
        reservationService.deleteReservation(id);
        // Then
        verify(reservationRepository).deleteById(id);
    }

    @Test
    void deleteReservationShouldThrowExceptionWhenNotFound() {
        // Given
        long id = 1;
        when(reservationRepository.existsById(id)).thenReturn(false);
        // When/Then
        assertThrows(IllegalArgumentException.class, () -> reservationService.deleteReservation(id));
        verify(reservationRepository, never()).deleteById(id);
    }*/
}