package com.example.tailormanagementsystem;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Menu {

public static void Menuview(Stage stage) {
    BorderPane border= new BorderPane();
    VBox vbox = new VBox();
    border.setPadding(new Insets(25, 25, 25, 25));
    Button ManageCustomer=new Button("Customer Details");
    Button ManageTailors=new Button("Tailor Management");
    Button setAppointment=new Button("Set Appointment");
    Button setOrder=new Button("Set Order");

    Button logout=new Button("Logout");
    Label title=new Label("Main Menu");
    title.setFont(Font.font("Gazpacho Bold",FontWeight.BOLD,30));
    title.setAlignment(Pos.TOP_CENTER);
    border.setTop(title);
    logout.setStyle("-fx-background-color: #B22222; -fx-text-fill: white; -fx-font-size: 16px; -fx-pref-width: 100px; -fx-pref-height: 40px; -fx-border-radius: 20px;");
    VBox vbox1 = new VBox();
    vbox1.setAlignment(Pos.BOTTOM_LEFT);
    vbox1.getChildren().add(logout);

    String buttonStyle = "-fx-background-color: Navy; -fx-text-fill: white; -fx-font-size: 16px; -fx-pref-width: 200px; -fx-pref-height: 40px; -fx-border-radius: 20px;";

    ManageCustomer.setStyle(buttonStyle);
    ManageTailors.setStyle(buttonStyle);
    setAppointment.setStyle(buttonStyle);
    setOrder.setStyle(buttonStyle);

    vbox.getChildren().addAll(ManageCustomer,ManageTailors,setAppointment,setOrder);
    vbox.setSpacing(10);
    vbox.setAlignment(Pos.CENTER);

//        border.getChildren().add(vbox);

    border.setRight(vbox1);

    border.setCenter(vbox);
    ManageTailors.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            TailorMenu.TailorMenuView(stage);
        }
    });
    ManageCustomer.setOnAction(actionEvent -> {
CustomerMenu.customerMenu(stage);
    });
    setAppointment.setOnAction(actionEvent -> {
AppointmentMenu.menu(stage);
    });
    logout.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Login.Loginview(stage);
        }
    });
setOrder.setOnAction(actionEvent -> {
    OrderMenu.menu(stage);
});

    Screen screen=Screen.getPrimary();
    Rectangle2D bounds=screen.getBounds();
    double defaultwidth= bounds.getWidth();
    double defaultHeight=bounds.getHeight();
    Scene menu = new Scene(border,defaultwidth,defaultHeight);
    stage.setScene(menu);
    stage.setFullScreen(true);
    stage.setTitle("Menu");
    stage.setFullScreenExitHint("");
    stage.show();
}
    public static void main(String[] args) throws IOException {


    }
}
