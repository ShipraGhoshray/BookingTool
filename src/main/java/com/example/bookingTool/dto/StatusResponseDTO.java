package com.example.bookingTool.dto;

import java.io.Serial;
import java.io.Serializable;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusResponseDTO implements Serializable{
	
	 @Serial
	 private static final long serialVersionUID = 1L;
	 
    private String code;
    private String message;
}