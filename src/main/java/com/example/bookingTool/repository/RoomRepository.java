package com.example.bookingTool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingTool.entity.RoomEntity;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    List<RoomEntity> findByName(String roomName);
    List<RoomEntity> findByRoomCapacity(Integer capacity);
}
