package com.babacarmane.studentmanagerbackend.exception;

import java.time.LocalDateTime;

public class ErrorResponse {

    private int status;
    private String message;
    private LocalDateTime timestamp;

    // Constructeur
    public ErrorResponse(int status, String message) {
        this.status    = status;
        this.message   = message;
        this.timestamp = LocalDateTime.now();
    }

    // Getters — nécessaires pour que Jackson sérialise en JSON
    public int getStatus()              { return status; }
    public String getMessage()          { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
