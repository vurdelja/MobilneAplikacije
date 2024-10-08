package com.example.mobilneaplikacije.model;

import com.google.firebase.Timestamp;

public class Notification {
    private String workerId;
    private String message;
    private boolean read;
    private Timestamp timestamp;

    public Notification() {
    }

    public Notification(String workerId, String message, boolean read, Timestamp timestamp) {
        this.workerId = workerId;
        this.message = message;
        this.read = read;
        this.timestamp = timestamp;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}