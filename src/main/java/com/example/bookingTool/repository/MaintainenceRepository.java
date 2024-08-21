package com.example.bookingTool.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookingTool.entity.Maintainence;

@Repository
public interface MaintainenceRepository extends JpaRepository<Maintainence, Long> {
    List<Maintainence> findBySlotId(long slotId);
}