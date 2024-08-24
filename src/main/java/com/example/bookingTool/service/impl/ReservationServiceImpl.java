package com.example.bookingTool.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.bookingTool.dto.ReservationDTO;
import com.example.bookingTool.entity.MaintainenceEntity;
import com.example.bookingTool.entity.ReservationEntity;
import com.example.bookingTool.entity.RoomEntity;
import com.example.bookingTool.handler.CustomGlobalException;
import com.example.bookingTool.mapper.ReservationMapper;
import com.example.bookingTool.repository.MaintainenceRepository;
import com.example.bookingTool.repository.ReservationRepository;
import com.example.bookingTool.repository.RoomRepository;
import com.example.bookingTool.service.ReservationService;
import com.example.bookingTool.util.Constants;

@Service
public class ReservationServiceImpl implements ReservationService{

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    RoomRepository roomRepository;
    
    @Autowired
    MaintainenceRepository maintenanceRepository;

    public List<ReservationDTO> listReservations() {
    	List<ReservationDTO> processedReservations = new ArrayList<>();
    	List<ReservationEntity> allReservations = reservationRepository.findAll();
        for (ReservationEntity reservation : allReservations) {
            processedReservations.add(ReservationMapper.convertToDTO(reservation));
        }
        return processedReservations;
    }

    public ReservationDTO getReservationById(long id) {
    	Optional<ReservationEntity> reservation = reservationRepository.findById(id);
        return ReservationMapper.convertToDTO(reservation.get());
    }

    public void deleteReservation(long id) {
        if (!reservationRepository.existsById(id)) {
            throw new IllegalArgumentException("Reservation not found for ID: " + id);
        }
        reservationRepository.deleteById(id);
    }
    
    public ReservationDTO addReservation(ReservationDTO reservationDto) {

    	ReservationEntity reservation = ReservationMapper.toEntity(reservationDto);
    	if(isValidReservation(reservation)) {
        	List<RoomEntity> roomList =  roomRepository.findAll();
        	List<RoomEntity> availableRoomList = roomList.stream().filter(room -> 
        	room.isAvailability() && reservation.getNoOfPeople() <= room.getRoomCapacity()).collect(Collectors.toList());
        	AtomicReference<RoomEntity> choosenRoom = new AtomicReference<RoomEntity>();
        	Integer lastCapacity = reservation.getNoOfPeople();
        	availableRoomList.forEach(avRoom -> {
        		if((choosenRoom.get()==null && lastCapacity <= avRoom.getRoomCapacity())
        				|| (null != choosenRoom.get() && choosenRoom.get().getRoomCapacity() < lastCapacity)) {
        			choosenRoom.set(avRoom);
        		}
        	});
        	if(null != choosenRoom.get()) {
            	reservation.setRoom(choosenRoom.get());
            	reservation.setDate(LocalDateTime.now());
                return ReservationMapper.convertToDTO(reservationRepository.save(reservation));
        	}	
    	}
		return null;
    }

    private boolean isValidReservation(ReservationEntity reservation) {

    	LocalDate currentDate = LocalDateTime.now().toLocalDate();
        if (reservation.getStartTime().toLocalDate().isAfter(currentDate) 
        		|| reservation.getEndTime().toLocalDate().isAfter(currentDate)) {
        	throw new CustomGlobalException(Constants.CUSTOM_EXCEPTION_INVALID_DATE, 
        			"Cannot make a reservation for a future date.", HttpStatus.BAD_REQUEST);
        } else if (reservation.getStartTime().toLocalDate().isBefore(currentDate) 
        		|| reservation.getEndTime().toLocalDate().isBefore(currentDate)) {
        	throw new CustomGlobalException(Constants.CUSTOM_EXCEPTION_INVALID_DATE, 
        			"Cannot make a reservation for a past date.", HttpStatus.BAD_REQUEST);
        }else if (reservation.getEndTime().isBefore(reservation.getStartTime())) {
        	throw new CustomGlobalException(Constants.CUSTOM_EXCEPTION_INVALID_DATE, 
        			"Reservation end time must be after start time.", HttpStatus.BAD_REQUEST);
        }
    
    	// Check if start and end time is in multiple of 15 minutes
    	long minutes = ChronoUnit.MINUTES.between(reservation.getStartTime(), reservation.getEndTime());
    	if(minutes % 15 != 0) {
    		throw new CustomGlobalException(Constants.CUSTOM_EXCEPTION_INVALID_TIMESLOT, 
				"Reservation can be done only in intervals of 15 mins, 2:00 - 2:15 or 2:00 - 2:30 or 2:00 - 3:00", 
				HttpStatus.BAD_REQUEST);
    	}
    	
        if (reservation.getNoOfPeople() > 20 || reservation.getNoOfPeople() < 1) {
        	throw new CustomGlobalException(Constants.CUSTOM_EXCEPTION_INVALID_CAPACITY, 
       			"The number of people allowed for a reservation should be greater than 1 and less than or equal to 20", 
       			HttpStatus.BAD_REQUEST);
        } 
        
    	//Validating overlap with Maintenance    	
    	List<MaintainenceEntity> maintenanceList = maintenanceRepository.findAll();
    	maintenanceList.forEach(mSlot -> {
    		if(isOverlappingWithMaintenanceSlot(reservation.getStartTime(), reservation.getEndTime(),
    				mSlot.getStartTime(), mSlot.getEndTime())) {
    			//isReservationAllowed.set(false);
    			throw new CustomGlobalException(Constants.CUSTOM_EXCEPTION_SLOT_OVERLAP, 
    					"Reservation time overlaps with maintenance time, Please choose another slot.", HttpStatus.BAD_REQUEST);
    		}
    	});
    	//Overlap reservation
    	List<ReservationEntity> allReservations = reservationRepository.findAll();
    	for (ReservationEntity existingReservation : allReservations) {
    		if (isExistingReservationsOverlapping(existingReservation.getStartTime(), existingReservation.getEndTime(),
    				reservation.getStartTime(), reservation.getEndTime())) {
    			throw new CustomGlobalException(Constants.CUSTOM_EXCEPTION_NO_SLOT_AVAIABLE, 
    					"Reservation time overlaps with existing reservations, Please choose another slot.", HttpStatus.BAD_REQUEST);
    		}
    	}
    	return true;
    }

    private boolean isExistingReservationsOverlapping(LocalDateTime reservationStartTime, LocalDateTime reservationEndTime, 
    		LocalDateTime newReservationStartTime, LocalDateTime newReservationEndTime) {
        return (reservationStartTime.isBefore(newReservationEndTime) && reservationEndTime.isAfter(newReservationStartTime)) 
        		|| (newReservationStartTime.isBefore(reservationEndTime) && reservationEndTime.isAfter(reservationStartTime)) 
        		|| (reservationStartTime.isEqual(newReservationStartTime) && reservationEndTime.isEqual(newReservationEndTime));
    }
    
    private boolean isOverlappingWithMaintenanceSlot(LocalDateTime reservationStartTime, LocalDateTime reservationEndTime, 
    		LocalDateTime maintenanceStartTime, LocalDateTime maintenanceEndTime) {
        return reservationStartTime.isBefore(maintenanceEndTime) && maintenanceStartTime.isBefore(reservationEndTime);
    	/*if(mSlot.getStartTime().isEqual(reservation.getStartTime()) 
    			|| mSlot.getEndTime().isEqual(reservation.getEndTime())
    			|| (mSlot.getEndTime().isEqual(reservation.getEndTime()) && mSlot.getEndTime().isBefore(reservation.getEndTime()))
    			|| (mSlot.getEndTime().isEqual(reservation.getEndTime()) && mSlot.getEndTime().isBefore(reservation.getEndTime()))) {
    		isReservationAllowed.set(false);
    	}*/
    }
}