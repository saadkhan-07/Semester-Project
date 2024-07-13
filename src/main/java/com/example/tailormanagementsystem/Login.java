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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.EventHandler;

public class Login  {
public static void Loginview(Stage stage)
{

    // Create a GridPane with padding and alignment
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25, 25, 25, 25));

    // Title
    Text scenetitle = new Text("Saad Design's");

    scenetitle.setFont(Font.font("Gazpacho Bold", FontWeight.SEMI_BOLD, 30));

    grid.add(scenetitle, 1, 0, 2, 1);

    // Username Label and TextField
    Label userName = new Label("Username:");
    userName.setFont(Font.font("Gazpacho Bold", FontWeight.NORMAL, 14));
    grid.add(userName, 0, 1);
    TextField userTextField = new TextField();
    grid.add(userTextField, 1, 1);

    // Password Label and PasswordField
    Label pw = new Label("Password:");
    pw.setFont(Font.font("Gazpacho Bold", FontWeight.NORMAL, 14));
    grid.add(pw, 0, 2);
    PasswordField pwBox = new PasswordField();
    grid.add(pwBox, 1, 2);

    // Login Button
    Button login = new Button("Login ✅");
    Button forgotpassword = new Button("Forgot Password");


    HBox hbBtn = new HBox(10);
    hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
    hbBtn.getChildren().add(forgotpassword);
    hbBtn.getChildren().add(login);

    grid.add(hbBtn, 1, 3);
    Button close = new Button("Close❌");
    grid.add(close, 1, 4);
    close.setAlignment(Pos.BOTTOM_CENTER);

    close.setStyle("-fx-background-color: #B22222; -fx-text-fill: white; -fx-font-size: 15px;");
    login.setStyle("-fx-background-color: #43AF50; -fx-text-fill: white; -fx-font-size: 15px;");
    forgotpassword.setStyle("-fx-background-color: navy; -fx-text-fill: white; -fx-font-size: 15px;");
    // Invalid Credentials Text
    Text invalidtext = new Text();
    grid.add(invalidtext, 1, 5);

    // Login Action
    login.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            String userName = userTextField.getText();
            String password = pwBox.getText();
            // Here, you can implement your user validation logic

            // For demonstration purposes, always display invalid credentials message
            invalidtext.setFill(Color.FIREBRICK);
            invalidtext.setText("Fill the fields!!");

            User user = new User(userName, password);
            try {
                if (  UserAPi.checkUser(user)) {

                    Menu.Menuview(stage);

                }
                else{
                    invalidtext.setFill(Color.FIREBRICK);
                    invalidtext.setText("Invalid username or password");

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    });
    forgotpassword.setOnAction(actionEvent -> ResetPassword.resetPassword(stage));
    close.setOnAction(actionEvent -> stage.close());

    // Create the scene and set it to the stage

    Screen screen=Screen.getPrimary();
    Rectangle2D bounds=screen.getBounds();
    double defaultwidth= bounds.getWidth();
    double defaultHeight=bounds.getHeight();
    Scene Loginscene = new Scene(grid,defaultwidth,defaultHeight);
//    stage.setFullScreen(true);
    stage.setScene(Loginscene);
    stage.setTitle("Login Page");

    stage.setFullScreenExitHint("");
    stage.show();
}


}


