module com.example.sweeper {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.sweeper to javafx.fxml;
    exports com.example.sweeper;
}