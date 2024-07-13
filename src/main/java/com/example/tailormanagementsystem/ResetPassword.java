package com.example.tailormanagementsystem;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import javax.swing.*;
import java.io.IOException;


public class ResetPassword  {

   public static void resetPassword(Stage stage) {
       GridPane grid = new GridPane();
       grid.setAlignment(Pos.CENTER);
       grid.setHgap(10);
       grid.setVgap(15);
       grid.setPadding(new Insets(25, 25, 25, 25));
       Button back = new Button("⬅");
       Text resetText = new Text("Reset Password");
       resetText.setFont(Font.font("Gazpacho Bold", FontWeight.BOLD, 20));
       resetText.setFill(Color.FIREBRICK);
       grid.add(back, 0, 0);
       grid.add(resetText, 1, 0,2,1);
       Label resetLabel = new Label("Enter Resetcode: ");
       PasswordField resetField = new PasswordField();
       Button enterButton = new Button("Enter");
       Text invalidcodeText = new Text();
       invalidcodeText.setFill(Color.FIREBRICK);
       enterButton.setAlignment(Pos.BOTTOM_LEFT);
       enterButton.setStyle("-fx-background-color: #43AF50; -fx-text-fill: white; -fx-font-size: 15px;");
       resetLabel.setFont(Font.font("Gazpacho Bold", FontWeight.BOLD, 14));
       grid.add(resetLabel, 0, 1);
       grid.add(resetField, 1, 1);
       grid.add(enterButton, 1, 2);
       grid.add(invalidcodeText, 2, 2);
       Label password=new Label("New Password");
       PasswordField passwordField=new PasswordField();
       Button OkButton = new Button("OK✅");
       OkButton.setStyle("-fx-background-color: #43AF50; -fx-text-fill: white; -fx-font-size: 15px;");

       enterButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               try {
                   if (UserAPi.checkCode((Integer.valueOf(resetField.getText())))) {

                        invalidcodeText.setVisible(false);
                       password.setFont(Font.font("Gazpacho Bold", FontWeight.NORMAL, 14));
                       grid.add(password,0,3);

                       grid.add(passwordField,1,3);
                       grid.add(OkButton,1,4);
                   }
                   else
                   {
                       invalidcodeText.setText("Invalid reset code!!");
                   }
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
           }
       });
       OkButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {

               try {
                   UserAPi.setPassword(passwordField.getText());
                   Login.Loginview(stage);
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
           }
       });
       back.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent actionEvent) {
               Login.Loginview(stage);
           }
       });


       Screen screen=Screen.getPrimary();
       Rectangle2D bounds=screen.getBounds();
       double defaultwidth= bounds.getWidth();
       double defaultHeight=bounds.getHeight();
       Scene Resetscene=new Scene(grid,defaultwidth,defaultHeight);
       stage.setScene(Resetscene);
       stage.setTitle("Reset Password");
       stage.setFullScreen(true);
       stage.setFullScreenExitHint("");
       stage.show();


   }


}
