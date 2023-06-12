package com.kursova;

import com.kursova.db.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;  
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Application extends javafx.application.Application {

    static boolean useDB = false;

    public static ObservableList<Ticket> ticketList = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();

        MainController mainController = fxmlLoader.getController();
        mainController.initialize();

        if (useDB){
            DatabaseManager.createTicketsTableIfNotExists();
            DatabaseManager.getTickets();
            TicketFileManager.saveTickets();
        }
        else{
            TicketFileManager.loadTickets();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}