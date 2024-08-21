package com.example.bookingTool.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingTool.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
   //List<Reservation>findByRoom(long roomId);
   List<Reservation>findByStartTimeAndEndTime(LocalDateTime startTime, LocalDateTime endTime);
}
