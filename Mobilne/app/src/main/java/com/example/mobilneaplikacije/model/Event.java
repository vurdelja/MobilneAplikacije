package com.example.mobilneaplikacije.model;

public class Event {
    private String eventType;
    private String name;
    private String description;
    private int maxParticipants;
    private String location;
    private String date;
    private boolean isPrivate;

    public Event() {
    }

    public Event(String eventType, String name, String description, int maxParticipants, String location, String date, boolean isPrivate) {
        this.eventType = eventType;
        this.name = name;
        this.description = description;
        this.maxParticipants = maxParticipants;
        this.location = location;
        this.date = date;
        this.isPrivate = isPrivate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    @Override
    public String toString() {
        return "Event{" +
                ", eventType='" + eventType + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", maxParticipants=" + maxParticipants +
                ", location='" + location + '\'' +
                ", date=" + date +
                ", isPrivate=" + isPrivate +
                '}';
    }
}
