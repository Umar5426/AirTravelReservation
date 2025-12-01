package main.java.model;

import java.util.Date;

public class Flight {
    public String flightID;
    public String flightCode;
    public String airLine;
    public Date flightDate;
    public String flightDuration;
    public AreaCode departureAreaCode;
    public AreaCode arrivalAreaCode;

    // Constructors
    public Flight() {}

    public Flight(String flightID, String flightCode, String airline, Date flightDate, String flightDuration, AreaCode departureAreaCode, AreaCode arrivalAreaCode) {
        this.flightID = flightID;
        this.flightCode = flightCode;
        this.airLine = airline;
        this.flightDate = flightDate;
        this.flightDuration = flightDuration;
        this.departureAreaCode = departureAreaCode;
        this.arrivalAreaCode = arrivalAreaCode;
    }

    // Getters
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

    // Setters
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

    


}
