package com.kursova;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TicketFileManager {
    private static final String FILE_PATH = "tickets.txt";

    public static void saveTickets() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Ticket ticket : Application.ticketList) {
                writer.println(ticketToLine(ticket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadTickets() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Ticket ticket = lineToTicket(line);
                if (ticket != null) {
                    Application.ticketList.add(ticket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String ticketToLine(Ticket ticket) {
        return ticket.destination + ";" +
                ticket.departure + ";" +
                ticket.flightNumber + ";" +
                ticket.passengerName + ";" +
                ticket.departureDate + ";" +
                ticket.flightDuration;
    }

    private static Ticket lineToTicket(String line) {
        String[] parts = line.split(";");
        if (parts.length == 6) {
            String destination = parts[0];
            String departure = parts[1];
            String flightNumber = parts[2];
            String passengerName = parts[3];
            LocalDate departureDate = LocalDate.parse(parts[4]);
            String flightDuration = parts[5];
            return new Ticket(destination, departure, flightNumber, passengerName, departureDate, flightDuration);
        }
        return null;
    }
}