package com.example.bookingTool.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "room")
public class Room implements Serializable{
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial")
    private long roomId;

    @NotBlank(message = "Conference room name is required")
    @Size(min = 2, max = 20)
    private String name;

    @NotNull(message = "Availability is required")
    private boolean availability;

    @NotNull(message = "Number of sitting places is required")
    @Min(value = 0, message = "Number of sitting places must be greater than or equal to zero")
    private Integer roomCapacity;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;

	/*public Room(long roomId,
			@NotBlank(message = "Conference room name is required") @Size(min = 2, max = 20) String name,
			@NotNull(message = "Availability is required") boolean availability,
			@NotNull(message = "Number of sitting places is required") @Min(value = 0, message = "Number of sitting places must be greater than or equal to zero") Integer roomCapacity,
			List<Reservation> reservations) {
		super();
		this.roomId = roomId;
		this.name = name;
		this.availability = availability;
		this.roomCapacity = roomCapacity;
		this.reservations = reservations;
	}*/

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public Integer getRoomCapacity() {
		return roomCapacity;
	}
	
	public void setRoomCapacity(Integer roomCapacity) {
		this.roomCapacity = roomCapacity;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
}
