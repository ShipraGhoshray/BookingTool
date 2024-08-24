package com.example.bookingTool.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingTool.entity.MaintainenceEntity;

@Repository
public interface MaintainenceRepository extends JpaRepository<MaintainenceEntity, Long> {
    List<MaintainenceEntity> findBySlotId(long slotId);
}