package main.java.model;

import java.util.Date;

public class Flight {
    private int id;
    private String flightID;
    private String flightCode;
    private String airLine;
    private Date flightDate;
    private String flightDuration;
    private AreaCode departureAreaCode;
    private AreaCode arrivalAreaCode;
    private int capacity;
    private double price;

    // Constructors
    public Flight() {}

    public Flight(String flightID,
                  String flightCode,
                  String airline,
                  Date flightDate,
                  String flightDuration,
                  AreaCode departureAreaCode,
                  AreaCode arrivalAreaCode) {
        this.flightID = flightID;
        this.flightCode = flightCode;
        this.airLine = airline;
        this.flightDate = flightDate;
        this.flightDuration = flightDuration;
        this.departureAreaCode = departureAreaCode;
        this.arrivalAreaCode = arrivalAreaCode;
    }

    public int getId() { return id; }
    public String getFlightID(){
        return flightID;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public String getAirLine() {
        return airLine;
    }

    public Date getFlightDate() {
        return flightDate;
    }

    public String getFlightDuration() {
        return flightDuration;
    }

    public AreaCode getDepartureAreaCode() {
        return departureAreaCode;
    }

    public AreaCode getArrivalAreaCode() {
        return arrivalAreaCode;
    }

    public int getCapacity() { return capacity; }
    public double getPrice() { return price; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public void setAirLine(String airLine) {
        this.airLine= airLine;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public void setFlightDuration(String flightDuration) {
        this.flightDuration = flightDuration;
    }

    public void setDepartureAreaCode(AreaCode departureAreaCode) {
        this.departureAreaCode = departureAreaCode;
    }

    public void setArrivalAreaCode(AreaCode arrivalAreaCode) {
        this.arrivalAreaCode = arrivalAreaCode;
    }

    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setPrice(double price) { this.price = price; }
}
