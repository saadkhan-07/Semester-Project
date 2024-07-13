module com.example.tailormanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.tailormanagementsystem to javafx.fxml;
    exports com.example.tailormanagementsystem;
}