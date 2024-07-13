package com.example.tailormanagementsystem;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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


public class TailorMenu  {
    public static void TailorMenuView(Stage stage)
    {
        BorderPane border = new BorderPane();
        Label title = new Label("Tailor Menu");
        title.setFont(Font.font("Gazpacho Bold", FontWeight.BOLD, 30));
        border.setPadding(new Insets(30, 30, 30, 30));
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(25,25,25,25));
        Button AddTailor = new Button("Add Tailor ➕");
        Button DeleteTailor = new Button("Delete Tailor");
        Button EditTailor = new Button("Edit Tailor");
        Button DisplayTailor = new Button("Display Tailors");
        Button back = new Button("⬅");
        back.setAlignment(Pos.TOP_RIGHT);
        Button logout=new Button("Logout");
        logout.setStyle("-fx-background-color: #B22222; -fx-text-fill: white; -fx-font-size: 16px; -fx-pref-width: 100px; -fx-pref-height: 40px; -fx-border-radius: 20px;");
        vbox.getChildren().addAll(AddTailor,DeleteTailor,EditTailor,DisplayTailor,logout);
        vbox.setAlignment(Pos.CENTER_LEFT);


        vbox.setSpacing(10);
        String buttonStyle = "-fx-background-color: #43AF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-pref-width: 200px; -fx-pref-height: 40px; -fx-border-radius: 20px;";
        AddTailor.setStyle(buttonStyle);
        DeleteTailor.setStyle(buttonStyle);
        EditTailor.setStyle(buttonStyle);
        DisplayTailor.setStyle(buttonStyle);
//border.getChildren().addAll(vbox);


        HBox hbox = new HBox(back,title);
        hbox.setAlignment(Pos.TOP_LEFT);
        hbox.setSpacing(550);
        border.setTop(hbox);
        border.setLeft(vbox);
        DisplayTailor.setOnAction(event->{
            ArrayList<Tailor> Tailors;
            try {
                Tailors = TailorApi.readTailor();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            TableView<Tailor> TailorTable = new TableView<>();
            TableColumn<Tailor,Integer> TailorID = new TableColumn<>("Tailor ID");
            TailorID.setCellValueFactory(new PropertyValueFactory<>("tailorId"));
            TableColumn<Tailor,String> TailorName = new TableColumn<>("Tailor Name");
            TailorName.setCellValueFactory(new PropertyValueFactory<>("name"));
            TableColumn<Tailor,String> Tailoremail = new TableColumn<>("Tailor Email");
            Tailoremail.setCellValueFactory(new PropertyValueFactory<>("email"));
            TableColumn<Tailor,Double> Salary = new TableColumn<>("Salary");
            Salary.setCellValueFactory(new PropertyValueFactory<>("Salary"));
            TailorTable.getColumns().addAll(TailorID,TailorName,Tailoremail,Salary);
            ObservableList<Tailor> Data = FXCollections.observableArrayList(Tailors);
            TailorTable.setItems(Data);
            border.setCenter(TailorTable);
            TailorTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);});

        AddTailor.setOnAction(actionEvent -> {
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));
            Label name=new Label("Name: ");
            TextField namefield=new TextField();
            Label email=new Label("Email: ");
            TextField emailfield=new TextField();
            Label Salary=new Label("Salary: ");
            name.setFont(Font.font("Gazpacho Bold",FontWeight.SEMI_BOLD,15));
            email.setFont(Font.font("Gazpacho Bold",FontWeight.SEMI_BOLD,15));
            Salary.setFont(Font.font("Gazpacho Bold",FontWeight.SEMI_BOLD,15));
            TextField Salaryfield=new TextField();
            grid.add(name,0,0);
            grid.add(namefield,1,0);
            grid.add(email,0,1);
            grid.add(emailfield,1,1);
            grid.add(Salary,0,2);
            grid.add(Salaryfield,1,2);
            Button enter =new Button("Enter");
            enter.setStyle("-fx-background-color: #43AF50; -fx-text-fill: white; -fx-font-size: 15px;");
            grid.add(enter,1,3);

            enter.setAlignment(Pos.CENTER_LEFT);
            enter.setOnAction(Event->{
                if (namefield.getText().isEmpty() || emailfield.getText().isEmpty() || Salaryfield.getText().isEmpty()) {
                    Text invalid=new Text("Please fill all the textfields!!");
                    invalid.setFill(Color.FIREBRICK);
                    grid.add(invalid,1,4);
                }
                else{
                    Text added=new Text("Added Successfully");
                    added.setFont(Font.font("Gazpacho Bold", FontWeight.BOLD, 20));
                    grid.add(added,1,5,2,1);
                    String tname=namefield.getText();
                    Double tsalary= Double.valueOf(Salaryfield.getText());
                    String temail=emailfield.getText();



                    try {
                        TailorApi.addTailor(new Tailor(tname,temail,tsalary));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            });

            border.setCenter(grid);
        });
        EditTailor.setOnAction(actionEvent ->{
            GridPane grid2=new GridPane();
            grid2.setAlignment(Pos.CENTER);
            grid2.setHgap(10);
            grid2.setVgap(10);
            grid2.setPadding(new Insets(210,350,250,250));
            Label EditTailorID=new Label("Tailor ID: ");
            EditTailorID.setFont(Font.font("Gazpacho Bold",FontWeight.SEMI_BOLD,15));
            TextField tailorIDfield=new TextField();
            Button enter =new Button("Enter");
            enter.setStyle("-fx-background-color: #43AF50; -fx-text-fill: white; -fx-font-size: 15px;");


            grid2.add(EditTailorID,0,0);
            grid2.add(tailorIDfield,1,0);
            grid2.add(enter,1,1);
            border.setCenter(grid2);
            enter.setOnAction(actionEvent2 -> {
                if (tailorIDfield.getText().isEmpty()) {
                    Text invalid=new Text("Please Enter the id!!");
                    invalid.setFill(Color.FIREBRICK);
                    grid2.add(invalid,0,2,2,1);
                }

                else {
                    int tid;
                    try {

                        tid=TailorApi.searchTailor(Integer.parseInt(tailorIDfield.getText()));
                        System.out.println(tid);
                        if (tid==-1) {
                            Text invalid=new Text("Tailor ID Not Found!");
                            invalid.setFill(Color.FIREBRICK);
                            grid2.add(invalid,0,3,2,1);

                        }
                        else
                        {
                            Text text =new Text("Select from the following");
                            text.setFont(Font.font("Gazpacho Bold", FontWeight.SEMI_BOLD, 30));
                            GridPane gridPane=new GridPane();
                            gridPane.setAlignment(Pos.CENTER_LEFT);
                            gridPane.setHgap(10);
                            gridPane.setVgap(10);
                            gridPane.setPadding(new Insets(120, 250, 170, 250));
                            Button Name=new Button("Name");
                            Button Email=new Button("Email");
                            Button Salary=new Button("Salary");
                            Name.setMaxWidth(130);
                            Email.setMaxWidth(130);
                            Salary.setMaxWidth(130);

                            Name.setStyle("-fx-background-color: Navy; -fx-text-fill: white; -fx-font-size: 15px;");
                            Email.setStyle("-fx-background-color: Navy; -fx-text-fill: white; -fx-font-size: 15px;");
                            Salary.setStyle("-fx-background-color: Navy; -fx-text-fill: white; -fx-font-size: 15px;");
                            gridPane.add(text,0,0,2,1);
                            gridPane.add(Name,1,1);
                            gridPane.add(Email,1,2);
                            gridPane.add(Salary,1,3);
                            border.setCenter(gridPane);
                            Name.setOnAction(actionEvent1 -> {
                                GridPane gridPane1=new GridPane();
                                gridPane1.setAlignment(Pos.CENTER);
                                gridPane1.setHgap(10);
                                gridPane1.setVgap(10);
                                gridPane1.setPadding(new Insets(120, 250, 170, 250));
                                Label NewName=new Label("Name: ");
                                NewName.setFont(Font.font("Gazpacho Bold",FontWeight.SEMI_BOLD,15));
                                TextField namefield=new TextField();
                                gridPane1.add(NewName,0,0);
                                gridPane1.add(namefield,1,0);
                                Button enterbutton =new Button("Enter");
                                enterbutton.setStyle("-fx-background-color: #43AF50; -fx-text-fill: white; -fx-font-size: 15px;");
                                gridPane1.add(enterbutton,1,1);
                                border.setCenter(gridPane1);
                                enterbutton.setOnAction(actionEvent3 -> {

                                    if (!namefield.getText().isEmpty()) {
                                        try {
                                            String tname=namefield.getText();
                                            TailorApi.changeName(tname, tid);
                                            Text edited = new Text("Changed Successfully");
                                            edited.setFont(Font.font("Gazpacho Bold", FontWeight.SEMI_BOLD, 30));
                                            gridPane1.add(edited, 1, 2, 2, 1);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                    else {
                                        Text invalid=new Text("Please fill Required field!!");
                                        invalid.setFill(Color.FIREBRICK);
                                        gridPane1.add(invalid,0,3,2,1);
                                    }
                                });

                            });
                            Email.setOnAction(actionEvent1 -> {
                                GridPane gridPane1=new GridPane();
                                gridPane1.setAlignment(Pos.CENTER);
                                gridPane1.setHgap(10);
                                gridPane1.setVgap(10);
                                gridPane1.setPadding(new Insets(120, 250, 170, 250));
                                Label NewEmail=new Label("Email: ");
                                NewEmail.setFont(Font.font("Gazpacho Bold",FontWeight.SEMI_BOLD,15));
                                TextField emailField=new TextField();
                                gridPane1.add(NewEmail,0,0);
                                gridPane1.add(emailField,1,0);
                                Button enterbutton =new Button("Enter");
                                gridPane1.add(enterbutton,1,1);
                                enterbutton.setStyle("-fx-background-color: #43AF50; -fx-text-fill: white; -fx-font-size: 15px;");
                                border.setCenter(gridPane1);
                                enterbutton.setOnAction(actionEvent3 -> {
                                    if (!emailField.getText().isEmpty()) {
                                        try {
                                            String temail=emailField.getText();
                                            TailorApi.changeEmail(temail, tid);
                                            Text edited = new Text("Changed Successfully");
                                            edited.setFont(Font.font("Gazpacho Bold", FontWeight.SEMI_BOLD, 30));
                                            gridPane1.add(edited, 1, 2, 2, 1);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                    else {
                                        Text invalid=new Text("Please fill Required field!!");
                                        invalid.setFill(Color.FIREBRICK);
                                        gridPane1.add(invalid,0,3,2,1);
                                    }
                                });
                            });
                            Salary.setOnAction(actionEvent3->{
                                GridPane gridPane1=new GridPane();
                                gridPane1.setAlignment(Pos.CENTER);
                                gridPane1.setHgap(10);
                                gridPane1.setVgap(10);
                                gridPane1.setPadding(new Insets(120, 250, 170, 250));
                                Label NewSalary=new Label("Salary: ");
                                NewSalary.setFont(Font.font("Gazpacho Bold",FontWeight.SEMI_BOLD,15));
                                TextField salaryField=new TextField();
                                gridPane1.add(NewSalary,0,0);
                                gridPane1.add(salaryField,1,0);
                                Button enterbutton =new Button("Enter");
                                gridPane1.add(enterbutton,1,1);
                                enterbutton.setStyle("-fx-background-color: #43AF50; -fx-text-fill: white; -fx-font-size: 15px;");
                                border.setCenter(gridPane1);
                                enterbutton.setOnAction(event->{
                                    if (!salaryField.getText().isEmpty()) {
                                        try {
                                            String tsalary=salaryField.getText();
                                            TailorApi.changeSalary(Double.valueOf(tsalary), tid);
                                            Text edited = new Text("Changed Successfully");
                                            edited.setFont(Font.font("Gazpacho Bold", FontWeight.SEMI_BOLD, 30));
                                            gridPane1.add(edited, 1, 2, 2, 1);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                    else {
                                        Text invalid=new Text("Please fill Required field!!");
                                        invalid.setFill(Color.FIREBRICK);
                                        gridPane1.add(invalid,0,3,2,1);
                                    }

                                });

                            });
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            });



//
        });
        DeleteTailor.setOnAction(actionEvent -> {
            Text delete=new Text("Delete Tailor");
            delete.setFont(Font.font("Gazpacho Bold", FontWeight.SEMI_BOLD, 30));

            GridPane gridPane=new GridPane();
            gridPane.setHgap(15);
            gridPane.setVgap(15);
            gridPane.setPadding(new Insets(210,250,150,250));
// gridPane.setAlignment(Pos.CENTER);
            Label Id=new Label("Enter Id: ");
            Id.setFont(Font.font("Gazpacho Bold",FontWeight.SEMI_BOLD,15));
            TextField idfield=new TextField();
            gridPane.add(delete,0,0,2,1);
            gridPane.add(Id,0,1);
            gridPane.add(idfield,1,1);
            border.setCenter(gridPane);
            Button enter =new Button("Enter");
            enter.setStyle("-fx-background-color: #43AF50; -fx-text-fill: white; -fx-font-size: 15px;");
            gridPane.add(enter,1,2);
            enter.setOnAction(Event->{
                if (idfield.getText().isEmpty())
                {
                    Text invalid=new Text("Please enter the id field!!");
                    invalid.setFill(Color.FIREBRICK);
                    gridPane.add(invalid,2,2);
                }
                else{
                    int tid= 0;
                    try {
                        tid = TailorApi.searchTailor(Integer.parseInt(idfield.getText()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    if (tid==-1) {
                        Text invalid=new Text("Tailor ID Not Found!");
                        invalid.setFill(Color.FIREBRICK);
                        gridPane.add(invalid,0,3,2,1);
                    }
                    else {

                        try {
                            TailorApi.removeTailor(Integer.parseInt(idfield.getText()));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Text remove = new Text("Removed Successfully");
                        remove.setFont(Font.font("Gazpacho Bold", FontWeight.BOLD, 20));
                        gridPane.add(remove, 1, 4, 2, 1);

                    }
//                    try {
//                        TailorApi.removeTailor(Integer.parseInt(idfield.getText()));
//                        Text remove=new Text("Removed Successfully");
//                        remove.setFont(Font.font("Gazpacho Bold", FontWeight.BOLD, 20));
//                        gridPane.add(remove,2,3,2,1);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
                }

            });


        });
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Login.Loginview(stage);
            }
        });
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Menu.Menuview(stage);
            }
        });

        Screen screen=Screen.getPrimary();
        Rectangle2D bounds=screen.getBounds();
        double defaultwidth= bounds.getWidth();
        double defaultHeight=bounds.getHeight();
        Scene Tailormenu = new Scene(border,defaultwidth,defaultHeight);
        stage.setScene(Tailormenu);
        stage.setTitle("TailorManagementSystem");
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);
        stage.show();

    }
}
