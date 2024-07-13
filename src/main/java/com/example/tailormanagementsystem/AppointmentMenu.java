package com.example.tailormanagementsystem;

import javafx.beans.property.SimpleStringProperty;
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

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class AppointmentMenu {
    

    public static void menu(Stage stage)
    {
        BorderPane border = new BorderPane();
        Label title = new Label("Appointment Menu");
        title.setFont(Font.font("Gazpacho Bold", FontWeight.BOLD, 30));
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(25,25,25,25));
        Button AddAppointment = new Button("Add Appointment ➕");
        Button DeleteAppointment = new Button("Delete Appointment");
        Button EditAppointment = new Button("Edit Appointment");
        Button DisplayAppointments=new Button("Display Appointments");
        Button back = new Button("⬅");
        back.setAlignment(Pos.TOP_RIGHT);
        Button logout=new Button("Logout");
        logout.setStyle("-fx-background-color: #B22222; -fx-text-fill: white; -fx-font-size: 16px; -fx-pref-width: 100px; -fx-pref-height: 40px; -fx-border-radius: 20px;");
        vbox.getChildren().addAll(AddAppointment,DeleteAppointment,EditAppointment,DisplayAppointments,logout);
        vbox.setAlignment(Pos.CENTER_LEFT);

        HBox hBox = new HBox(back,title);
        hBox.setAlignment(Pos.TOP_LEFT);
        hBox.setSpacing(550);
        border.setTop(hBox);

        vbox.setSpacing(10);
        String buttonStyle = "-fx-background-color: #43AF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-pref-width: 200px; -fx-pref-height: 40px; -fx-border-radius: 20px;";
        AddAppointment.setStyle(buttonStyle);
        DeleteAppointment.setStyle(buttonStyle);
        EditAppointment.setStyle(buttonStyle);
        DisplayAppointments.setStyle(buttonStyle);


        border.setLeft(vbox);

        AddAppointment.setOnAction(actionEvent -> {
            GridPane grid = new GridPane();
            Label CustomerId = new Label("Customer id: ");
            TextField customeridfield = new TextField();
            grid.add(CustomerId,0,0);
            grid.add(customeridfield,1,0);
            grid.setPadding(new Insets(25,25,25,25));
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setAlignment(Pos.CENTER);
            Label TailorId = new Label("Tailor id: ");
            TextField tailorfield = new TextField();
            grid.add(TailorId,0,1);
            grid.add(tailorfield,1,1);
            Label Time = new Label("Time: ");
            CustomerId.setFont(Font.font("Gazpacho Bold", FontWeight.NORMAL, 15));
            TailorId.setFont(Font.font("Gazpacho Bold", FontWeight.NORMAL, 15));
            Time.setFont(Font.font("Gazpacho Bold", FontWeight.NORMAL, 15));
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.BOTTOM_RIGHT);
            ComboBox<String> hourComboBox = new ComboBox<>();
            hourComboBox.getItems().addAll(
                    "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
                    "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                    "20", "21", "22", "23"
            );
            hourComboBox.setValue("00");
            ComboBox<String> minuteComboBox = new ComboBox<>();
            minuteComboBox.getItems().addAll(
                    "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"
            );
            minuteComboBox.setValue("00");
            hbox.getChildren().addAll(hourComboBox,minuteComboBox);
            grid.add(Time,0,2);
            grid.add(hbox,1,2);
            Button enter =new Button("Enter");
            enter.setStyle("-fx-background-color: #43AF50; -fx-text-fill: white; -fx-font-size: 15px;");
            grid.add(enter,1,3);
            enter.setAlignment(Pos.CENTER_LEFT);
            border.setCenter(grid);
            enter.setOnAction(actionEvent1 -> {
             if(customeridfield.getText().isEmpty()|| tailorfield.getText().isEmpty()){
                 Text invalid=new Text("Please fill all the fields!!");
                 invalid.setFill(Color.FIREBRICK);
                 grid.add(invalid,1,4,2,1);
             }
             else {
                 Customer customer;
                 Tailor tailor;
                 try {

                     int cid = Integer.parseInt(customeridfield.getText());
                     int tailorid = Integer.parseInt(tailorfield.getText());
                      customer =CusrtomerApi.getCustomer(cid);
                     tailor=TailorApi.getTailor(tailorid);

                 } catch (IOException e) {
                     throw new RuntimeException(e);
                 }
                 if (customer.getId()==0)
                 {
                     Text invalid=new Text("This customer is not registered!");
                     invalid.setFill(Color.FIREBRICK);
                     grid.add(invalid,2,0,2,1);

                 }
                 else if (tailor.getTailorId()==0)
                 {
                     Text invalid=new Text("This tailor is not registered!");
                     invalid.setFill(Color.FIREBRICK);
                     grid.add(invalid,2,1,2,1);
                 }
                 else
                 {
                     try {
                         String time=hourComboBox.getValue();
                         time+=":"+minuteComboBox.getValue();
                         if (AppointmentApi.addAppointment(new Appointment(customer,tailor,time)))
                         {
                             Text success=new Text("Appointment added!");
                             success.setFont(Font.font("Gazpacho Bold", FontWeight.NORMAL, 15));
                             grid.add(success,1,4);
                         }
                         else {
                             Text invalid=new Text("Tailor is not free at this time slot");
                             invalid.setFill(Color.FIREBRICK);
                             grid.add(invalid,2,4,2,1);
                         }
                     } catch (IOException e) {
                         throw new RuntimeException(e);
                     }
                 }

             }
            });
        });

        DisplayAppointments.setOnAction(actionEvent -> {
            ArrayList<Appointment> appointments=new ArrayList<>();

                appointments=AppointmentApi.getAppointments();

            TableView<Appointment> AppointmentTable = new TableView<>();
            TableColumn<Appointment,Integer> AppointmentId = new TableColumn<>("Appointment ID");
            AppointmentId.setCellValueFactory(new PropertyValueFactory<>("id"));
            TableColumn<Appointment,String> customer = new TableColumn<>("Customer");
            customer.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getCustomer().getName()));
            TableColumn<Appointment,String> tailor = new TableColumn<>("Tailor");
            tailor.setCellValueFactory(cellData-> new SimpleStringProperty(String.valueOf(cellData.getValue().getTailor().getName())));
            TableColumn<Appointment,String> Time = new TableColumn<>("Time");
            Time.setCellValueFactory(new PropertyValueFactory<>("time"));
            TableColumn<Appointment, String> date=new TableColumn<>("Date");
            date.setCellValueFactory(data -> {
                LocalDate currentDate = LocalDate.now();
                return new SimpleStringProperty(currentDate.toString());});
            AppointmentTable.getColumns().addAll(AppointmentId,customer,tailor,Time,date);
            ObservableList<Appointment> Data = FXCollections.observableArrayList(appointments);
            AppointmentTable.setItems(Data);
            border.setCenter(AppointmentTable);
            AppointmentTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        });

        DeleteAppointment.setOnAction(actionEvent -> {
            Text delete = new Text("Delete Appointment");
            delete.setFont(Font.font("Gazpacho Bold", FontWeight.SEMI_BOLD, 30));

            GridPane gridPane = new GridPane();
            gridPane.setHgap(15);
            gridPane.setVgap(15);
            gridPane.setPadding(new Insets(210, 250, 150, 250));
// gridPane.setAlignment(Pos.CENTER);
            Label Id = new Label("Enter Id: ");
            Id.setFont(Font.font("Gazpacho Bold",FontWeight.SEMI_BOLD,15));
            TextField idfield = new TextField();
            gridPane.add(delete, 0, 0, 2, 1);
            gridPane.add(Id, 0, 1);
            gridPane.add(idfield, 1, 1);
            border.setCenter(gridPane);
            Button enter = new Button("Enter");
            enter.setStyle("-fx-background-color: #43AF50; -fx-text-fill: white; -fx-font-size: 15px;");
            gridPane.add(enter, 1, 2);
            enter.setOnAction(Event -> {
                if (idfield.getText().isEmpty()) {
                    Text invalid = new Text("Please enter the id field!!");
                    invalid.setFill(Color.FIREBRICK);
                    gridPane.add(invalid, 2, 2);
                } else {
                    try {
                        int tid;

                        tid=AppointmentApi.searchAppointment(Integer.parseInt(idfield.getText()));

                        if (tid==-1) {
                            Text invalid=new Text(" ID Not Found!");
                            invalid.setFill(Color.FIREBRICK);
                            gridPane.add(invalid,0,3,2,1);
                        }
                        else {

                            AppointmentApi.removeAppointment(Integer.parseInt(idfield.getText()));
                            Text remove = new Text("Removed Successfully");
                            remove.setFont(Font.font("Gazpacho Bold", FontWeight.BOLD, 20));
                            gridPane.add(remove, 1, 3, 2, 1);

                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            });


        });

        EditAppointment.setOnAction(actionEvent -> {
            GridPane grid2=new GridPane();
            grid2.setAlignment(Pos.CENTER);
            grid2.setHgap(15);
            grid2.setVgap(15);
            grid2.setPadding(new Insets(210,350,250,250));
            Label EditID=new Label("Appointment ID: ");
            EditID.setFont(Font.font("Gazpacho Bold", FontWeight.BOLD, 20));
            TextField IDfield=new TextField();
            Button enter =new Button("Enter");
            enter.setStyle("-fx-background-color: #43AF50; -fx-text-fill: white; -fx-font-size: 15px;");

            border.setCenter(grid2);
            grid2.add(EditID,0,0);
            grid2.add(IDfield,1,0);
            grid2.add(enter,1,1);
            enter.setOnAction(actionEvent1 -> {
                if (IDfield.getText().isEmpty()) {
                    Text invalid=new Text("Please Enter the id!!");
                    invalid.setFill(Color.FIREBRICK);
                    grid2.add(invalid,0,2,2,1);
                }
                else {
                    int apid;
                    try {
                        int appointmentid= Integer.parseInt(IDfield.getText());
                        apid = AppointmentApi.searchAppointment(appointmentid);

                        if (apid == -1) {
                            Text invalid = new Text("Appointment Id Not Found!");
                            invalid.setFill(Color.FIREBRICK);
                            grid2.add(invalid, 0, 3, 2, 1);
                        } else {
                            Text text = new Text("Select from the following");
                            text.setFont(Font.font("Gazpacho Bold", FontWeight.SEMI_BOLD, 30));
                            GridPane gridPane = new GridPane();
                            gridPane.setAlignment(Pos.CENTER_LEFT);
                            gridPane.setHgap(10);
                            gridPane.setVgap(10);
                            gridPane.setPadding(new Insets(120, 250, 170, 250));
                            Button Customerr = new Button("Customer");
                            Button tailor = new Button("Tailor");
                            Button Time = new Button("Time");
                            Customerr.setMaxWidth(130);
                            tailor.setMaxWidth(130);
                            Time.setMaxWidth(130);

                            Customerr.setStyle("-fx-background-color: Navy; -fx-text-fill: white; -fx-font-size: 15px;");
                            tailor.setStyle("-fx-background-color: Navy; -fx-text-fill: white; -fx-font-size: 15px;");
                            Time.setStyle("-fx-background-color: Navy; -fx-text-fill: white; -fx-font-size: 15px;");
                            gridPane.add(text, 0, 0, 2, 1);
                            gridPane.add(Customerr, 1, 1);
                            gridPane.add(tailor, 1, 2);
                            gridPane.add(Time, 1, 3);
                            border.setCenter(gridPane);
                            Customerr.setOnAction(actionEvent2 -> {
                                GridPane gridPane1=new GridPane();
                                gridPane1.setAlignment(Pos.CENTER);
                                gridPane1.setHgap(10);
                                gridPane1.setVgap(10);
                                gridPane1.setPadding(new Insets(120, 250, 170, 250));
                                Label Newcustomer=new Label("Customer Id: ");
                                Newcustomer.setFont(Font.font("Gazpacho Bold",FontWeight.SEMI_BOLD,20));
                                TextField customerfield=new TextField();
                                gridPane1.add(Newcustomer,0,0);
                                gridPane1.add(customerfield,1,0);
                                Button enterbutton =new Button("Enter");
                                enterbutton.setStyle("-fx-background-color: #43AF50; -fx-text-fill: white; -fx-font-size: 15px;");
                                gridPane1.add(enterbutton,1,1);
                                border.setCenter(gridPane1);
                                enterbutton.setOnAction(actionEvent3 -> {
                                    if (!customerfield.getText().isEmpty()) {
                                        try {
                                            Customer cus=CusrtomerApi.getCustomer(Integer.parseInt(customerfield.getText()));
                                            if (cus.getId()==0)
                                            {
                                                Text invalid=new Text("Customer Id Not Found!");
                                                invalid.setFill(Color.FIREBRICK);
                                                gridPane1.add(invalid, 0, 3, 2, 1);
                                            }
                                          else {
                                                AppointmentApi.changeCustomer(cus,apid);
                                                Text edited = new Text("Changed Successfully");
                                                edited.setFont(Font.font("Gazpacho Bold", FontWeight.SEMI_BOLD, 25));
                                                gridPane1.add(edited, 1, 2, 2, 1);
                                            }
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                    else {
                                        Text invalid=new Text("Please fill Required field!!");
                                        invalid.setFill(Color.FIREBRICK);
                                        gridPane1.add(invalid,0,4,2,1);
                                    }
                                });
                            });
                            tailor.setOnAction(actionEvent2 -> {
                                GridPane gridPane1=new GridPane();
                                gridPane1.setAlignment(Pos.CENTER);
                                gridPane1.setHgap(10);
                                gridPane1.setVgap(10);
                                gridPane1.setPadding(new Insets(120, 250, 170, 250));
                                Label Newtailor=new Label("Tailor Id: ");
                                Newtailor.setFont(Font.font("Gazpacho Bold",FontWeight.SEMI_BOLD, 20));

                                TextField tailorfield=new TextField();
                                gridPane1.add(Newtailor,0,0);
                                gridPane1.add(tailorfield,1,0);
                                Button enterbutton =new Button("Enter");
                                gridPane1.add(enterbutton,1,1);
                                enterbutton.setStyle("-fx-background-color: #43AF50; -fx-text-fill: white; -fx-font-size: 15px;");
                                border.setCenter(gridPane1);
                                enterbutton.setOnAction(actionEvent3 -> {
                                    if (!tailorfield.getText().isEmpty()) {
                                        try {
                                           Tailor tailor1=TailorApi.getTailor(Integer.parseInt(tailorfield.getText()));
                                           if (tailor1.getName() == null) {
                                               Text invalid = new Text("Tailor Id Not Found!");
                                               invalid.setFill(Color.FIREBRICK);
                                               gridPane1.add(invalid, 0, 3, 2, 1);
                                           }
                                         else
                                           {
                                               AppointmentApi.changeTailor(tailor1,apid);
                                               Text edited = new Text("Changed Successfully");
                                               edited.setFont(Font.font("Gazpacho Bold", FontWeight.SEMI_BOLD, 30));
                                               gridPane1.add(edited, 1, 2, 2, 1);
                                           }
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                    else {
                                        Text invalid=new Text("Please fill Required field!!");
                                        invalid.setFill(Color.FIREBRICK);
                                        gridPane1.add(invalid,0,4,2,1);
                                    }
                                });
                            });

                            Time.setOnAction(event->{
                                GridPane gridPane1=new GridPane();
                                gridPane1.setAlignment(Pos.CENTER);
                                gridPane1.setHgap(10);
                                gridPane1.setVgap(10);
                                gridPane1.setPadding(new Insets(120, 250, 170, 250));
                                ComboBox<String> hourComboBox = new ComboBox<>();
                                hourComboBox.getItems().addAll(
                                        "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
                                        "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                                        "20", "21", "22", "23"
                                );
                                hourComboBox.setValue("00");
                                ComboBox<String> minuteComboBox = new ComboBox<>();
                                minuteComboBox.getItems().addAll(
                                        "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"
                                );
                                HBox hbox = new HBox();
                                hbox.setAlignment(Pos.BOTTOM_RIGHT);
                                Label time = new Label("Time: ");
                                time.setFont(Font.font("Gazpacho Bold",FontWeight.SEMI_BOLD,15));
                                minuteComboBox.setValue("00");
                                minuteComboBox.setValue("00");
                                hbox.getChildren().addAll(hourComboBox,minuteComboBox);
                                gridPane1.add(time,0,0);
                                gridPane1.add(hbox,1,0);
                                Button enterButton =new Button("Enter");
                                enterButton.setStyle("-fx-background-color: #43AF50; -fx-text-fill: white; -fx-font-size: 15px;");
                                gridPane1.add(enterButton,1,3);
                                enterButton.setAlignment(Pos.CENTER_LEFT);
                                border.setCenter(gridPane1);
                                enterButton.setOnAction(ActionEvent->{
                                    String Newtime=hourComboBox.getValue();
                                    Newtime+=":"+minuteComboBox.getValue();
                                    try {
                                        Text invalid=new Text();
                                        Text invalid1=new Text();
                                        invalid1.setVisible(false);
                                        invalid.setVisible(false);
                                       Appointment appointment= AppointmentApi.getAppointment(appointmentid);
//                                        System.out.println(appointment);
                                        System.out.println(AppointmentApi.checkAppointment(appointment));
                                       if (AppointmentApi.analyzeAppointment(appointment,Newtime)) {

                                           if (AppointmentApi.changeTime(Newtime, apid)) {

                                               Text edited = new Text("Changed Successfully");
                                               edited.setFont(Font.font("Gazpacho Bold", FontWeight.SEMI_BOLD, 30));
                                               gridPane1.add(edited, 1, 4, 2, 1);
                                           } else {

                                               invalid.setVisible(true);

                                                invalid.setText("Tailor is not free at this time!");
                                               invalid.setFill(Color.FIREBRICK);
                                               gridPane1.add(invalid, 0, 5, 2, 1);

                                           }
                                       }
                                       else
                                       {

                                          invalid1.setVisible(true);
                                           invalid1.setText("Tailor has an appointment at this time!");
                                           invalid1.setFill(Color.FIREBRICK);
                                           gridPane1.add(invalid1, 0, 6, 2, 1);

                                       }
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                });


                            });
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }      });
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
        Scene menu = new Scene(border,defaultwidth,defaultHeight);
        stage.setScene(menu);
        stage.setFullScreen(true);
        stage.setTitle("Menu");
        stage.setFullScreenExitHint("");
        stage.show();

    }


}
