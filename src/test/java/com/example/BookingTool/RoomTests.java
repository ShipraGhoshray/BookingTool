
package com.example.BookingTool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.bookingTool.dto.RoomDTO;
import com.example.bookingTool.entity.Room;
import com.example.bookingTool.mapper.RoomMapper;
import com.example.bookingTool.repository.RoomRepository;
import com.example.bookingTool.service.RoomService;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class RoomTests {

    /*@Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @InjectMocks
    private RoomMapper roomMapper;
    
    private Validator validator;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @JsonIgnore
    @Test
    public void testRoomAnnotations() {
        Room room = new Room();
        room.setName("A");
        room.setAvailability(true);
        room.setRoomCapacity(10);
        Set<ConstraintViolation<Room>> violations = validator.validate(room);
        assertEquals(3, violations.size());

        List<String> expectedMessages = Arrays.asList(
                "wielkość musi należeć do zakresu od 2 do 20",
                "Identifier format is invalid",
                "Level must be between 0 and 10"
        );
        for (ConstraintViolation<Room> violation : violations) {
            String actualMessage = violation.getMessage();
            System.out.println("Actual violation message: " + actualMessage);
            assertTrue(expectedMessages.contains(actualMessage));
        }
    }

    @Test
    void convertToDTOShouldConvertRoomToDTO() {
        // Given
        Room room = new Room();
        room.setRoomId(1);
        room.setName("Room 1");
        room.setAvailability(true);
        room.setRoomCapacity(10);
        // When
        RoomDTO result = RoomMapper.convertToDTO(room);
        // Then
        assertEquals("Room 1", result.getRoomName());
        assertEquals(true, result.isAvailability());
        assertEquals(10, result.getRoomCapacity());
    }

    @Test
    void listRoomsShouldReturnListOfRoomDTOs() {
        // Given
        Room room1 = new Room();
        room1.setRoomId(1);
        room1.setName("Room 1");
        room1.setAvailability(true);
        room1.setRoomCapacity(10);

        Room room2 = new Room();
        room2.setRoomId(2);
        room2.setName("Room 2");
        room2.setAvailability(false);
        room2.setRoomCapacity(5);

        when(roomRepository.findAll()).thenReturn(Arrays.asList(room1, room2));

        // When
        List<RoomDTO> result = roomService.listRooms();

        // Then
        assertEquals(2, result.size());

        RoomDTO dto1 = result.get(0);
        assertEquals(1, dto1.getRoomId());
        assertEquals("Room 1", dto1.getRoomName());
        assertEquals(true, dto1.isAvailability());
        assertEquals(10, dto1.getRoomCapacity());

        RoomDTO dto2 = result.get(1);
        assertEquals(2, dto2.getRoomId());
        assertEquals("Room 2", dto2.getRoomName());
        assertEquals(false, dto2.isAvailability());
        assertEquals(5, dto2.getRoomCapacity());
    }


    @Test
    void getRoomByIdShouldReturnRoom() {
        // Given
        long id = 1;
        Room room = new Room(id, "Amaze", true, 7, null);

        when(roomRepository.findById(id)).thenReturn(Optional.of(room));
        // When
        Optional<RoomDTO> result = Optional.ofNullable(roomService.getRoomById(id));
        // Then
        assertEquals(Optional.of(room), result);
    }

    @Test
    void getRoomByIdShouldThrowExceptionWhenIdDoesNotExist() {
        // Given
        long id = 1;
        when(roomRepository.findById(id)).thenReturn(Optional.empty());
        // When/Then
        assertThrows(IllegalArgumentException.class, () -> roomService.getRoomById(id));
    }

    @Test
    void deleteRoomShouldDeleteRoomWhenIdExists() {
        // Given
        long id = 1;
        when(roomRepository.existsById(id)).thenReturn(true);
        // When
        roomService.deleteRoom(id);
        // Then
        verify(roomRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteRoomShouldThrowExceptionWhenIdDoesNotExist() {
        // Given
        long id = 1;
        when(roomRepository.existsById(id)).thenReturn(false);
        // When/Then
        assertThrows(IllegalArgumentException.class, () -> roomService.deleteRoom(id));
        verify(roomRepository, never()).deleteById(id);
    }

    @Test
    public void addRoomShouldSaveRoomWhenAddingNewRoom() {
        // Given
        long id = 1;
        Room room = new Room(id, "Room1",true, 10, null);
        when(roomRepository.findByName("Room1")).thenReturn(Collections.emptyList());
        // When
        roomService.addRoom(room);
        // Then
        verify(roomRepository, never()).findById(id);
        verify(roomRepository, times(1)).save(room);
    }

    @Test
    void addRoomShouldThrowExceptionWhenAddingExistingRoom() {
        // Given
        Room room = new Room();
        room.setName("Existing Room");

        List<Room> existingRooms = new ArrayList<>();
        existingRooms.add(room);

        when(roomRepository.findByName("existing room")).thenReturn(existingRooms);
        // When/Then
        assertThrows(IllegalArgumentException.class, () -> {
            roomService.addRoom(room);
        });
        verify(roomRepository, never()).save(any(Room.class));
    }

    @Test
    public void addRoomShouldThrowExceptionWhenAddingRoomWithExistingIdentifier() {
        // Given
        Room existingRoom = new Room();
        existingRoom.setName("Room1");

        when(roomRepository.findByName("Room1")).thenReturn(Collections.emptyList());

        Room newRoom = new Room();
        newRoom.setName("New Room");

        // When/Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            roomService.addRoom(newRoom);
        });

        assertEquals("Room with the identifier 'R001' already exists", exception.getMessage());
        verify(roomRepository, never()).save(newRoom);
    }*/
}
