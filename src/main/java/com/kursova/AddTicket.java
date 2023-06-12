package com.kursova;

import com.kursova.db.DatabaseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class AddTicket {
    @FXML
    private Button done;

    @FXML
    private TextField enter_destination;

    @FXML
    private TextField enter_departure;

    @FXML
    private TextField enter_flight_number;

    @FXML
    private DatePicker enter_datePicker;

    @FXML
    private TextField enter_passengerName;

    @FXML
    private TextField enter_flight_duration;

    public void onDoneClick(){
        MainController.stage.close();
        createTicket();
    }
    public void createTicket(){
        String destination = enter_destination.getText();
        String departure = enter_departure.getText();
        String flightNumber = enter_flight_number.getText();
        String passengerName = enter_passengerName.getText();
        LocalDate departureDate = enter_datePicker.getValue();
        String flightDuration = enter_flight_duration.getText();

        Ticket ticket = new Ticket(destination, departure, flightNumber, passengerName, departureDate, flightDuration);
        Application.ticketList.add(ticket);

        if (Application.useDB){
            Application.ticketList.get(Application.ticketList.size() - 1).id = DatabaseManager.addTicket(ticket);
        }
        else {
            TicketFileManager.saveTickets();
        }
    }
}
