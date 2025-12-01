package main.java.model;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Flight {

    private static final AtomicInteger nextId = new AtomicInteger(1); // thread-safe auto-increment

    private String flightID;  // unique, auto-generated
    private String flightCode;
    private String airLine;
    private Date flightDate;
    private String flightDuration;
    private AreaCode departureAreaCode;
    private AreaCode arrivalAreaCode;
    private double price;

    // ========================
    // Constructors
    // ========================
    // Constructor for creating a new Flight (auto-generates flightID)
    public Flight(String flightCode,
                  String airLine,
                  Date flightDate,
                  String flightDuration,
                  AreaCode departureAreaCode,
                  AreaCode arrivalAreaCode,
                  double price) {
        this.flightID = generateFlightID();
        this.flightCode = flightCode;
        this.airLine = airLine;
        this.flightDate = flightDate;
        this.flightDuration = flightDuration;
        this.departureAreaCode = departureAreaCode;
        this.arrivalAreaCode = arrivalAreaCode;
        this.price = price;
    }

    // Constructor for loading existing flights from DB (uses given flightID)
    public Flight(String flightID,
                  String flightCode,
                  String airLine,
                  Date flightDate,
                  String flightDuration,
                  AreaCode departureAreaCode,
                  AreaCode arrivalAreaCode,
                  double price) {
        this.flightID = flightID;
        this.flightCode = flightCode;
        this.airLine = airLine;
        this.flightDate = flightDate;
        this.flightDuration = flightDuration;
        this.departureAreaCode = departureAreaCode;
        this.arrivalAreaCode = arrivalAreaCode;
        this.price = price;
    }

    // ========================
    // Unique ID generator
    // ========================
    private String generateFlightID() {
        return "F" + nextId.getAndIncrement(); // e.g., F1, F2, F3
    }

    // ========================
    // Getters
    // ========================
    public String getFlightID() { return flightID; }
    public String getFlightCode() { return flightCode; }
    public String getAirLine() { return airLine; }
    public Date getFlightDate() { return flightDate; }
    public String getFlightDuration() { return flightDuration; }
    public AreaCode getDepartureAreaCode() { return departureAreaCode; }
    public AreaCode getArrivalAreaCode() { return arrivalAreaCode; }
    public double getPrice() { return price; }

    // ========================
    // Setters
    // ========================
    public void setFlightCode(String flightCode) { this.flightCode = flightCode; }
    public void setAirLine(String airLine) { this.airLine = airLine; }
    public void setFlightDate(Date flightDate) { this.flightDate = flightDate; }
    public void setFlightDuration(String flightDuration) { this.flightDuration = flightDuration; }
    public void setDepartureAreaCode(AreaCode departureAreaCode) { this.departureAreaCode = departureAreaCode; }
    public void setArrivalAreaCode(AreaCode arrivalAreaCode) { this.arrivalAreaCode = arrivalAreaCode; }
    public void setPrice(double price) { this.price = price; }

}
