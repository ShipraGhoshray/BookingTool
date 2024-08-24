package com.example.bookingTool.service.impl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.bookingTool.dto.RoomDTO;
import com.example.bookingTool.entity.RoomEntity;
import com.example.bookingTool.mapper.RoomMapper;
import com.example.bookingTool.repository.RoomRepository;

@ExtendWith(MockitoExtension.class)
public class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImpl roomService;

    @Captor
    private ArgumentCaptor<RoomEntity> roomEntityArgumentCaptor;

    private RoomDTO roomDto;
    private RoomEntity roomEntity;

    @BeforeEach
    public void setUp() {
        roomDto = RoomDTO.builder()
                .roomName("TestConferenceRoom")
                .roomCapacity(5)
                .availability(true)
                .build();
        roomEntity = RoomMapper.toEntity(roomDto);
    }

    @Test
    public void whenRequiredAttributesGiven_thenCreateARoom() {
    	RoomEntity roomEntity = RoomMapper.toEntity(roomDto);
        when(roomRepository.save(any(RoomEntity.class))).thenReturn(roomEntity);

        RoomDTO createdRoom = roomService.addRoom(roomDto);
        verify(roomRepository).save(roomEntityArgumentCaptor.capture());
        RoomEntity capturedRoomEntity = roomEntityArgumentCaptor.getValue();
        
        assertEquals(roomDto.getRoomName(), capturedRoomEntity.getName());
        assertEquals(roomDto.isAvailability(), capturedRoomEntity.isAvailability());
        assertEquals(roomDto.getRoomCapacity(), capturedRoomEntity.getRoomCapacity());
        
        assertEquals(roomDto.getRoomName(), createdRoom.getRoomName());
        assertEquals(roomDto.isAvailability(), createdRoom.isAvailability());
        assertEquals(roomDto.getRoomCapacity(), createdRoom.getRoomCapacity());
    }

    @Test
    public void whenRoomsArePresent_thenReturnRooms() {
        List<RoomEntity> roomEntities = List.of(roomEntity);
        when(roomRepository.findAll()).thenReturn(roomEntities);
        List<RoomDTO> rooms = roomService.listRooms();
        assertFalse(rooms.isEmpty());
        assertEquals(roomEntities.size(), rooms.size());
    }

    @Test
    public void whenRoomByNameIsFound_thenReturnRoom() {
        when(roomRepository.findByName(roomDto.getRoomName())).thenReturn(List.of(roomEntity));
        List<RoomDTO> foundRooms = roomService.getRoomByName(roomDto.getRoomName());
        assertNotNull(foundRooms.get(0));
        assertEquals(roomDto.getRoomName(), foundRooms.get(0).getRoomName());
        assertEquals(roomDto.getRoomCapacity(), foundRooms.get(0).getRoomCapacity());
        assertEquals(roomDto.isAvailability(), foundRooms.get(0).isAvailability());
    }

    //@Test
    public void whenDeleteRoomById_thenRoomIsDeleted() {
    	roomDto.setRoomName("TestConferenceRoom");
    	roomDto.setRoomId(6L);
        when(roomRepository.findById(roomDto.getRoomId())).thenReturn(Optional.of(roomEntity));
        doNothing().when(roomRepository).delete(roomEntity);
        roomService.deleteRoom(6L);
        verify(roomRepository).delete(roomEntity);
    }
}
