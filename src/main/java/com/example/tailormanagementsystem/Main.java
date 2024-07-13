package com.example.tailormanagementsystem;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        Login.Loginview(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}
