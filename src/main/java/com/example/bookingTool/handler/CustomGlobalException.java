package com.example.bookingTool.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
@Getter
public class CustomGlobalException extends RuntimeException {

    private final String code;
    private final HttpStatus httpStatus;

    public CustomGlobalException(String code, String message, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

	public String getCode() {
		return code;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
