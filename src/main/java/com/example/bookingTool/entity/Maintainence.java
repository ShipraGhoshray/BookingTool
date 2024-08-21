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

@Entity
@Table(name = "maintenanceTimeSlots")
public class Maintainence implements Serializable {
    
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

    public Maintainence() {
    }
	public Maintainence(long slotId, LocalDateTime startTime, LocalDateTime endTime) {
        this.slotId = slotId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

	public long getSlotId() {
		return slotId;
	}

	public void setSlotId(long slotId) {
		this.slotId = slotId;
	}

    public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
}
