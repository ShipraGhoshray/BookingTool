package com.example.bookingTool.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

import com.example.bookingTool.dto.ReservationDTO;
import com.example.bookingTool.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
public class ReservationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String reservationApi = "/api/v1/reservation";
    private ReservationDTO reservationDto;
    private String currentDate;
    
    @BeforeEach
    public void setUp() {
    	currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern(Constants.DATE_PATTERN));
    	reservationDto = ReservationDTO.builder()
                .startTime(currentDate + " 09:30:00")
                .endTime(currentDate + " 10:00:00")
                .noOfPeople(5)
                .build();
    }

    @Order(1)
    @Test
    public void whenValidReservationDtoGiven_thenReservationShouldBeCreated() throws Exception {
        mockMvc.perform(post(reservationApi)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationDto)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Order(2)
    @Test
    public void whenNoOfPplInReservationIsEmpty_thenThrowCustomException() throws Exception {
    	reservationDto.setNoOfPeople(0);
        mockMvc.perform(post(reservationApi)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationDto)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
    
    @Order(3)
    @Test
    public void whenReservationTimeIsInPast_thenThrowCustomException() throws Exception {
    	currentDate = LocalDate.now().minusDays(2).format(DateTimeFormatter.ofPattern(Constants.DATE_PATTERN));
       	reservationDto.setStartTime(currentDate + " 10:30:00");
    	reservationDto.setEndTime(currentDate + " 11:00:00");
    	reservationDto.setNoOfPeople(6);
        mockMvc.perform(post(reservationApi)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationDto)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
    
    @Order(4)
    @Test
    public void whenReservationTimeIsInFuture_thenThrowCustomException() throws Exception {
    	currentDate = LocalDate.now().plusDays(6).format(DateTimeFormatter.ofPattern(Constants.DATE_PATTERN));
       	reservationDto.setStartTime(currentDate + " 11:30:00");
    	reservationDto.setEndTime(currentDate + " 12:00:00");
    	reservationDto.setNoOfPeople(6);
        mockMvc.perform(post(reservationApi)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationDto)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
    
    @Order(5)
    @Test
    public void whenReservationTimeIsInvalid_thenThrowCustomException() throws Exception {
    	currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern(Constants.DATE_PATTERN));
       	reservationDto.setStartTime(currentDate + " 13:00:00");
    	reservationDto.setEndTime(currentDate + " 12:00:00");
    	reservationDto.setNoOfPeople(6);
        mockMvc.perform(post(reservationApi)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationDto)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Order(6)
    @Test
    public void whenGettingReservationById_thenReservationShouldBeReturned() throws Exception {
    	currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern(Constants.DATE_PATTERN));
    	reservationDto = ReservationDTO.builder()
                .startTime(currentDate + " 14:00:00")
                .endTime(currentDate + " 14:30:00")
                .noOfPeople(5)
                .build();
        String responseContent = mockMvc.perform(post(reservationApi)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservationDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        ReservationDTO createReservationDto = objectMapper.readValue(responseContent, ReservationDTO.class);
        mockMvc.perform(get(reservationApi + "/{id}", createReservationDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Order(5)
    @Test
    public void whenDeleteReservationById_thenReservationShouldBeDeleted() throws Exception {
    	currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern(Constants.DATE_PATTERN));
    	reservationDto = ReservationDTO.builder()
                .startTime(currentDate + " 16:00:00")
                .endTime(currentDate + " 16:30:00")
                .noOfPeople(5)
                .build();
        String responseContent = mockMvc.perform(post(reservationApi)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        ReservationDTO createReservationtDto = objectMapper.readValue(responseContent, ReservationDTO.class);

        mockMvc.perform(delete(reservationApi + "/{id}", createReservationtDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}