package com.kursova;

import java.time.LocalDate;

public class Ticket {
    public int id;
    public String destination;
    public String departure;
    public String flightNumber;
    public String passengerName;
    public LocalDate departureDate;
    public String flightDuration;

    public Ticket(String destination, String departure, String flightNumber,
                  String passengerName, LocalDate departureDate, String flightDuration) {
        this.destination = destination;
        this.departure = departure;
        this.flightNumber = flightNumber;
        this.passengerName = passengerName;
        this.departureDate = departureDate;
        this.flightDuration = flightDuration;
    }

    public int getId() {
        return id;
    }

    public String getDestination() {
        return destination;
    }

    public String getDeparture() {
        return departure;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public String getFlightDuration() {
        return flightDuration;
    }

    public void setId(int id) {
        this.id = id;
    }
}