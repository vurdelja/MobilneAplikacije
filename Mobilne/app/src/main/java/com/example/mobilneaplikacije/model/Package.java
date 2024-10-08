package com.example.mobilneaplikacije.model;

import java.util.List;

public class Package {
    private String name;
    private String description;
    private double discount;
    private boolean visibility;
    private boolean availability;
    private String category;
    private List<Product> products;
    private List<Service> services;
    private List<String> eventTypes;
    private double price;
    private List<String> images;
    private int reservationLeadTime;
    private int cancellationLeadTime;
    private String confirmationMethod;
    private String status;

    public Package() {
    }

    public Package(String name, String description, double discount, boolean visibility, boolean availability, String category, List<Product> products, List<Service> services, List<String> eventTypes, double price, List<String> images, int reservationLeadTime, int cancellationLeadTime, String confirmationMethod, String status) {
        this.name = name;
        this.description = description;
        this.discount = discount;
        this.visibility = visibility;
        this.availability = availability;
        this.category = category;
        this.products = products;
        this.services = services;
        this.eventTypes = eventTypes;
        this.price = price;
        this.images = images;
        this.reservationLeadTime = reservationLeadTime;
        this.cancellationLeadTime = cancellationLeadTime;
        this.confirmationMethod = confirmationMethod;
        this.status = status;
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<String> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(List<String> eventTypes) {
        this.eventTypes = eventTypes;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getReservationLeadTime() {
        return reservationLeadTime;
    }

    public void setReservationLeadTime(int reservationLeadTime) {
        this.reservationLeadTime = reservationLeadTime;
    }

    public int getCancellationLeadTime() {
        return cancellationLeadTime;
    }

    public void setCancellationLeadTime(int cancellationLeadTime) {
        this.cancellationLeadTime = cancellationLeadTime;
    }

    public String getConfirmationMethod() {
        return confirmationMethod;
    }

    public void setConfirmationMethod(String confirmationMethod) {
        this.confirmationMethod = confirmationMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getters and Setters
    // ...
}