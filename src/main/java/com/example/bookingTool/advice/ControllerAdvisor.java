package com.example.bookingTool.advice;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.StreamSupport;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.example.bookingTool.dto.StatusResponseDTO;
import com.example.bookingTool.handler.CustomGlobalException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ControllerAdvisor {
	
	@ExceptionHandler({CustomGlobalException.class})
	public ResponseEntity<Object> handleCustomGlobalException(CustomGlobalException e, WebRequest request) {
		log.error("Error {}/{}", e.getCode(), e.getMessage());
		StatusResponseDTO statusResponse = StatusResponseDTO.builder().code(e.getCode()).message(e.getMessage()).build();
		//StatusResponseDTO statusResponse = new StatusResponseDTO(e.getCode(), e.getMessage());
		return new ResponseEntity<>(statusResponse, e.getHttpStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGenericException(Exception e, WebRequest request) {
		log.error("Error occurred due to : ()", e);
		StatusResponseDTO statusResponse = StatusResponseDTO.builder()
                .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase() + " from " + request)
                .build();
		//StatusResponseDTO statusResponse = new StatusResponseDTO(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
			//	HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase() + " from " + request);
		return new ResponseEntity<>(statusResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<Object> handleMException(MethodArgumentNotValidException e, WebRequest request) {
		StatusResponseDTO statusResponse = StatusResponseDTO.builder().code("APP_400")
				.message(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage()).build();
		//StatusResponseDTO statusResponse = new StatusResponseDTO("APP_400",
			//	Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
		return new ResponseEntity<>(statusResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ConstraintViolationException.class})
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e, WebRequest request) {
		List<StatusResponseDTO> statusResponseList = buildValidationErrors(e.getConstraintViolations());
		return new ResponseEntity<>(statusResponseList, HttpStatus.BAD_REQUEST);
	}

	private List<StatusResponseDTO> buildValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
		List<StatusResponseDTO> statusResponseDtoList = new ArrayList<StatusResponseDTO>();
		constraintViolations.forEach(violation -> {
			statusResponseDtoList.add(new StatusResponseDTO("APP_400", constructMessage(violation)));
		});
        return statusResponseDtoList;
    }

	private String constructMessage(ConstraintViolation<?> violation) {
		if (violation.getMessageTemplate().startsWith("{") && violation.getMessageTemplate().endsWith("}")) {
			return StringUtils.capitalize(
					Objects.requireNonNull(StreamSupport.stream(violation.getPropertyPath().spliterator(), false)
							.reduce((first, second) -> second).orElse(null)).toString()) + " " + violation.getMessage();
		}
		return violation.getMessage();
	}
}
