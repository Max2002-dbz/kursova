package com.kursova;

import com.kursova.db.DatabaseManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainController {

    @FXML
    public TableView<Ticket> table;

    @FXML
    public TableColumn<Ticket, Integer> sequance;

    @FXML
    public TableColumn<Ticket, String> destination;

    @FXML
    public TableColumn<Ticket, String> departure;

    @FXML
    public TableColumn<Ticket, String> flight_Number;

    @FXML
    public TableColumn<Ticket, String> passengerName;

    @FXML
    public TableColumn<Ticket, LocalDate> departureDate;

    @FXML
    public TableColumn<Ticket, String> flightDuration;

    @FXML
    public TextField search_by_number;

    @FXML
    public DatePicker search_by_date;

    @FXML
    public Button button_add;

    @FXML
    public Button button_clear;

    public static Stage stage;



    public void initialize() {
        table.setItems(Application.ticketList);

        destination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        departure.setCellValueFactory(new PropertyValueFactory<>("departure"));
        flight_Number.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        passengerName.setCellValueFactory(new PropertyValueFactory<>("passengerName"));
        departureDate.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        flightDuration.setCellValueFactory(new PropertyValueFactory<>("flightDuration"));

        sequance.setCellFactory(column -> {
            TableCell<Ticket, Integer> cell = new TableCell<>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        int rowIndex = getIndex() + 1;
                        setText(String.valueOf(rowIndex));
                    }
                }
            };
            return cell;
        });
        sequance.setSortable(false);

        search_by_number.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTicketsByNumber(table, newValue);
        });

        search_by_date.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                filterTicketsByDate(table, newValue.toString());
            }
        });

        button_clear.setOnAction(event -> {
            // Код для очищення поля DatePicker
            clear(table);
        });
    }

    public void filterTicketsByDate(TableView<Ticket> tableView, String localDate) {
        if (localDate.isEmpty()) {
            tableView.setItems(Application.ticketList);
        } else {
            ObservableList<Ticket> filteredList = Application.ticketList.filtered(order -> order.departureDate.toString().contains(localDate));
            tableView.setItems(filteredList);
        }
    }

    public void filterTicketsByNumber(TableView<Ticket> tableView, String flightNumber) {
        if (flightNumber.isEmpty()) {
            tableView.setItems(Application.ticketList);
            sequance.setCellFactory(column -> {
                TableCell<Ticket, Integer> cell = new TableCell<>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            int rowIndex = getIndex() + 1;
                            setText(String.valueOf(rowIndex));
                        }
                    }
                };
                return cell;
            });
        } else {
            ObservableList<Ticket> filteredList = Application.ticketList.filtered(order -> order.flightNumber.contains(flightNumber));
            tableView.setItems(filteredList);
        }
    }

    public void openAddTicket() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Add-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void delete(){
        int index = table.getSelectionModel().getSelectedIndex();
        if (Application.useDB){
            DatabaseManager.deleteTicket(Application.ticketList.get(index).id);
        }

        Application.ticketList.remove(index);

        if (!Application.useDB) {
            TicketFileManager.saveTickets();
        }
    }

    public void clear(TableView tableView){
        search_by_date.setValue(null);
        tableView.setItems(Application.ticketList);
        sequance.setCellFactory(column -> {
            TableCell<Ticket, Integer> cell = new TableCell<>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        int rowIndex = getIndex() + 1;
                        setText(String.valueOf(rowIndex));
                    }
                }
            };
            return cell;
        });
    }
}