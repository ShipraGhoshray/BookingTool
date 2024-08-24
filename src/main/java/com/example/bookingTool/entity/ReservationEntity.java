package com.example.bookingTool.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "roomReservation")
public class ReservationEntity implements Serializable{

	@Serial
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reservationId")
    private long id;

    @NotNull(message = "Number of People is required")
    @Min(value = 1, message = "Number of sitting places must be greater than or equal to one")
    @Max(value = 20, message = "No room available for more than 20 people")
    @Column(name = "noOfPeople")
    private int noOfPeople;
    
    //@FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

	@NotNull(message = "Start time of the meeting is required")
    @Column(name = "startTime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime  startTime;

    @NotNull(message = "End time of the meeting is required")
    @Column(name = "endTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime  endTime;

    @ManyToOne()
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

    public ReservationEntity() {
    }
    
    public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public int getNoOfPeople() {
		return noOfPeople;
	}
	public void setNoOfPeople(int noOfPeople) {
		this.noOfPeople = noOfPeople;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
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

	public RoomEntity getRoom() {
		return room;
	}
    
    public void setRoom(RoomEntity room) {
        this.room = room;
    }
    
    @AssertTrue(message = "End time of the meeting must be after it's start time")
    private boolean isEndTimeAfterStartTime() {
        return endTime.isAfter(startTime);
    }

}
