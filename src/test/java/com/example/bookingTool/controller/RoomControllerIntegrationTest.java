package com.example.bookingTool.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.bookingTool.dto.RoomDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
public class RoomControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private RoomDTO roomDto;

    @BeforeEach
    public void setUp() {
        roomDto = RoomDTO.builder()
                .roomName("Room1")
                .roomCapacity(3)
                .availability(true)
                .build();
    }

    @Order(1)
    @Test
    public void whenValidRoomDtoGiven_thenRoomShouldBeCreated() throws Exception {
        mockMvc.perform(post("/api/v1/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roomName").value("Room1"))
                .andExpect(jsonPath("$.roomCapacity").value(3))
                .andDo(MockMvcResultHandlers.print());
    }

    @Order(2)
    @Test
    public void whenRoomNameIsEmpty_thenThrowCustomException() throws Exception {
    	roomDto.setRoomName("");
        mockMvc.perform(post("/api/v1/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomDto)))
                .andExpect(status().isBadRequest())
               // .andExpect(jsonPath("$[0].code").value("ROOM NAME EMPTY"))
               // .andExpect(jsonPath("$[0].message").value("Room name must not be blank"))
                .andDo(MockMvcResultHandlers.print());
    }
    
    @Order(3)
    @Test
    public void whenRoomCapacityIsInvalid_thenThrowCustomException() throws Exception {
    	roomDto.setRoomCapacity(1);
        mockMvc.perform(post("/api/v1/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomDto)))
                .andExpect(status().isBadRequest())
               // .andExpect(jsonPath("$[0].code").value("ROOM NAME EMPTY"))
               // .andExpect(jsonPath("$[0].message").value("Room name must not be blank"))
                .andDo(MockMvcResultHandlers.print());
    }
    

    @Order(4)
    @Test
    public void whenGettingRoomByName_thenRoomShouldBeReturned() throws Exception {
    	roomDto = RoomDTO.builder().roomName("TestRoom").roomCapacity(20).availability(true).build();
        String responseContent = mockMvc.perform(post("/api/v1/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        RoomDTO createRoomDto = objectMapper.readValue(responseContent, RoomDTO.class);

        mockMvc.perform(get("/api/v1/rooms/{name}", createRoomDto.getRoomName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.roomName").value("TestRoom"))
               // .andExpect(jsonPath("$.roomCapacity").value(20))
                .andDo(MockMvcResultHandlers.print());
    }

    @Order(5)
    @Test
    public void whenDeleteRoomById_thenRoomShouldBeDeleted() throws Exception {
    	roomDto = RoomDTO.builder().roomName("Room2").roomCapacity(12).availability(true).build();
        String responseContent = mockMvc.perform(post("/api/v1/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        RoomDTO createRoomtDto = objectMapper.readValue(responseContent, RoomDTO.class);

        mockMvc.perform(delete("/api/v1/rooms/{id}", createRoomtDto.getRoomId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
