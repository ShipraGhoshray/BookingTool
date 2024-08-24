package com.example.bookingTool.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "maintenanceTimeSlots")
public class MaintainenceEntity implements Serializable {
    
	@Serial
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "slotId")
    private long slotId;

	@NotNull(message = "Maintainence Start time is required")
    @Column(name = "startTimeSlot")
    private LocalDateTime startTime;

    @NotNull(message = "Maintainence End Time is required")
    @Column(name = "endTimeSlot")
    private LocalDateTime endTime;
}