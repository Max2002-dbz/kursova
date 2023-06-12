package com.kursova.db;

import com.kursova.Application;
import com.kursova.Ticket;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlserver://FlightTickets.mssql.somee.com:1433;database=FlightTickets;user=makskuk2002_SQLLogin_1;password=4eqekj2zo8;";


    public static void createTicketsTableIfNotExists() {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            if (!isTableExists(statement, "Tickets")) {
                createTicketsTable(statement);
                System.out.println("Таблицю Tickets створено успішно.");
            } else {
                System.out.println("Таблиця Tickets вже існує.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getTickets() {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM Tickets";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("Id");
                String destination = resultSet.getString("Destination").trim();
                String departure = resultSet.getString("Departure").trim();
                String flightNumber = resultSet.getString("FlightNumber").trim();
                String passengerName = resultSet.getString("PassengerName").trim();
                LocalDate departureDate = resultSet.getDate("DepartureDate").toLocalDate();
                String flightDuration = resultSet.getString("FlightDuration").trim();

                Ticket ticket = new Ticket(destination, departure, flightNumber, passengerName, departureDate, flightDuration);
                ticket.setId(id);
                Application.ticketList.add(ticket);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int addTicket(Ticket ticket) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Tickets (Destination, Departure, FlightNumber, PassengerName, DepartureDate, FlightDuration) " +
                     "VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, ticket.destination);
            statement.setString(2, ticket.departure);
            statement.setString(3, ticket.flightNumber);
            statement.setString(4, ticket.passengerName);
            statement.setDate(5, Date.valueOf(ticket.departureDate));
            statement.setString(6, ticket.flightDuration);

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                System.out.println("Новий квиток успішно додано.");
                return generatedKeys.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void deleteTicket(int ticketId) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            String query = "DELETE FROM Tickets WHERE Id = " + ticketId;
            int rowsAffected = statement.executeUpdate(query);
            System.out.println("Квиток успішно видалено. Кількість рядків, які були змінені: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateTicket(Ticket ticket) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            String query = "UPDATE Tickets SET Destination = '" + ticket.destination + "', Departure = '" + ticket.departure +
                    "', FlightNumber = '" + ticket.flightNumber + "', PassengerName = '" + ticket.passengerName +
                    "', DepartureDate = '" + ticket.departureDate + "', FlightDuration = '" + ticket.flightDuration +
                    "' WHERE Id = " + ticket.id;

            int rowsAffected = statement.executeUpdate(query);
            System.out.println("Квиток успішно оновлено. Кількість рядків, які були змінені: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean isTableExists(Statement statement, String tableName) throws SQLException {
        String query = "SELECT COUNT(*) as count FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = '" + tableName + "'";
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        int count = resultSet.getInt("count");
        return count > 0;
    }

    private static void createTicketsTable(Statement statement) throws SQLException {
        String query = "CREATE TABLE Tickets (" +
                "Id INT PRIMARY KEY IDENTITY(1,1), " +
                "Destination NCHAR(100), " +
                "Departure NCHAR(100), " +
                "FlightNumber NCHAR(20), " +
                "PassengerName NCHAR(100), " +
                "DepartureDate DATE, " +
                "FlightDuration NCHAR(50)" +
                ")";
        statement.executeUpdate(query);
    }
}