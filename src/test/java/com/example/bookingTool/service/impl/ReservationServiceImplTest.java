package com.example.bookingTool.service.impl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.bookingTool.dto.ReservationDTO;
import com.example.bookingTool.entity.ReservationEntity;
import com.example.bookingTool.mapper.ReservationMapper;
import com.example.bookingTool.repository.MaintainenceRepository;
import com.example.bookingTool.repository.ReservationRepository;
import com.example.bookingTool.repository.RoomRepository;
import com.example.bookingTool.util.Constants;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RoomRepository roomRepository;
    
    @Mock
    private MaintainenceRepository maintenanceRepository;
    
    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Captor
    private ArgumentCaptor<ReservationEntity> reservationEntityArgumentCaptor;

    private ReservationDTO reservationDto;
    private ReservationEntity reservationEntity;
    private String currentDate;

    @BeforeEach
    public void setUp() {
    	currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern(Constants.DATE_PATTERN));
    	System.out.println(currentDate);
    	reservationDto = ReservationDTO.builder()
    			.date(currentDate)
                .startTime(currentDate + " 09:30:00")
                .endTime(currentDate + " 10:00:00")
                .noOfPeople(5)
                .build();
    	reservationEntity = ReservationMapper.toEntity(reservationDto);
    }

    @Test
    public void whenRequiredAttributesGiven_thenCreateAReservation() {
    	currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern(Constants.DATE_PATTERN));
    	reservationDto.setStartTime(currentDate + " 18:15:00");
    	reservationDto.setEndTime(currentDate + " 18:45:00");
    	
    	ReservationEntity reservationEntity = ReservationMapper.toEntity(reservationDto);
        when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(reservationEntity);

        ReservationDTO createdReservation= reservationService.addReservation(reservationDto);
        verify(reservationRepository).save(reservationEntityArgumentCaptor.capture());
        ReservationEntity capturedReservationEntity = reservationEntityArgumentCaptor.getValue();
        
        assertEquals(reservationDto.getNoOfPeople(), capturedReservationEntity.getNoOfPeople());
        assertEquals(reservationDto.getStartTime(), capturedReservationEntity.getStartTime());
        assertEquals(reservationDto.getEndTime(), capturedReservationEntity.getEndTime());
        
        assertEquals(reservationDto.getNoOfPeople(), createdReservation.getNoOfPeople());
        assertEquals(reservationDto.getStartTime(), createdReservation.getStartTime());
        assertEquals(reservationDto.getEndTime(), createdReservation.getEndTime());
    }

    @Test
    public void whenDeleteReservationById_thenReservationIsDeleted() {
        when(reservationRepository.findById(reservationEntity.getId())).thenReturn(Optional.of(reservationEntity));
        doNothing().when(reservationRepository).delete(reservationEntity);
        reservationService.deleteReservation(6L);
        verify(reservationRepository).delete(reservationEntity);
    }
}