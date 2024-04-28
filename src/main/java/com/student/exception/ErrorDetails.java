package com.student.exception;
import java.util.Date;

public class ErrorDetails {
	private String timestamp;
    private String message;
    private String details;

    public ErrorDetails(String string, String message, String details) {
        super();
        this.timestamp = string;
        this.message = message;
        this.details = details;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
   }

    public String getDetails() {
       return details;
   }
}