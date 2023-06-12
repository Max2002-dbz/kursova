module com.example.kursova {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.kursova to javafx.fxml;
    exports com.kursova;
    exports com.kursova.db;
    opens com.kursova.db to javafx.fxml;
}