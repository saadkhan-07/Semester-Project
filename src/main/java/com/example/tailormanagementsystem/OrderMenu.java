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
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderMenu {
    public static void menu(Stage stage)
    {

        BorderPane border = new BorderPane();
        Label title=new Label("Order Menu");
        title.setFont(Font.font("Gazpacho Bold", FontWeight.BOLD,30));
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(25,25,25,25));
        Button AddOrder = new Button("Add Order ➕");
        Button DeleteOrder = new Button("Delete Order");
        Button EditOrder = new Button("Edit Order");
        Button DisplayOrders=new Button("Display Orders");
        Button back = new Button("⬅");
        back.setAlignment(Pos.TOP_RIGHT);
        Button logout=new Button("Logout");
        logout.setStyle("-fx-background-color: #B22222; -fx-text-fill: white; -fx-font-size: 16px; -fx-pref-width: 100px; -fx-pref-height: 40px; -fx-border-radius: 20px;");
        vbox.getChildren().addAll(AddOrder,DeleteOrder,EditOrder,DisplayOrders,logout);
        vbox.setAlignment(Pos.CENTER_LEFT);
        vbox.setSpacing(10);
        String buttonStyle = "-fx-background-color: #43AF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-pref-width: 200px; -fx-pref-height: 40px; -fx-border-radius: 20px;";
        AddOrder.setStyle(buttonStyle);
        DisplayOrders.setStyle(buttonStyle);
        DeleteOrder.setStyle(buttonStyle);
        EditOrder.setStyle(buttonStyle);
        HBox hBox=new HBox(back,title);
        hBox.setAlignment(Pos.TOP_LEFT);
        hBox.setSpacing(550);
        border.setTop(hBox);
        border.setLeft(vbox);

        AddOrder.setOnAction(actionEvent -> {
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
            Label Quantity=new Label("Quantity");
            TextField quantityfield=new TextField();
            grid.add(Quantity,0,2);
            grid.add(quantityfield,1,2);
            Label Type=new Label("Type");
            TailorId.setFont(Font.font("Gazpacho Bold",FontWeight.SEMI_BOLD,15));
            CustomerId.setFont(Font.font("Gazpacho Bold",FontWeight.SEMI_BOLD,15));
            Type.setFont(Font.font("Gazpacho Bold",FontWeight.SEMI_BOLD,15));
            Quantity.setFont(Font.font("Gazpacho Bold",FontWeight.SEMI_BOLD,15));
            ToggleGroup toggleGroup=new ToggleGroup();
            RadioButton Urgent=new RadioButton("Urgent");
            RadioButton Simple=new RadioButton("Regular");
            Urgent.setToggleGroup(toggleGroup);
            Simple.setToggleGroup(toggleGroup);
            Simple.setSelected(true);
            HBox hBox1=new HBox(Urgent,Simple);
            hBox1.setAlignment(Pos.BOTTOM_RIGHT);
            grid.add(Type,0,3);
            grid.add(hBox1,1,3);
            border.setCenter(grid);
            Button enter =new Button("Enter");
            enter.setStyle("-fx-background-color: #43AF50; -fx-text-fill: white; -fx-font-size: 15px;");
            grid.add(enter,1,4);
            enter.setAlignment(Pos.CENTER_LEFT);
            Text invalid1=new Text();
            Text invalid2=new Text();
            Text invalid3=new Text();
            enter.setOnAction(actionEvent1 -> {
                if(customeridfield.getText().isEmpty()|| tailorfield.getText().isEmpty()|| quantityfield.getText().isEmpty()){
                     invalid1.setText("Please fill all the fields!!");
                    invalid1.setFill(Color.FIREBRICK);
                    grid.add(invalid1,1,5,2,1);
                }
                else {
                    invalid1.setVisible(false);
                    Customer customer;
                    Tailor tailor;
                    try {

                        int cid = Integer.parseInt(customeridfield.getText());
                        int tailorid = Integer.parseInt(tailorfield.getText());
                        customer = CusrtomerApi.getCustomer(cid);
                        tailor = TailorApi.getTailor(tailorid);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (customer.getId() == 0) {
                         invalid2.setText("This customer is not registered!");
                        invalid2.setFill(Color.FIREBRICK);
                        grid.add(invalid2, 2, 0, 2, 1);

                    } else if (tailor.getTailorId() == 0) {
                        invalid2.setVisible(false);
                         invalid3.setText("This tailor is not registered!");
                        invalid3.setFill(Color.FIREBRICK);
                        grid.add(invalid3, 2, 1, 2, 1);
                    }
                    else {
                        invalid1.setVisible(false);
                        invalid2.setVisible(false);
                        invalid3.setVisible(false);
                        int quant= Integer.parseInt(quantityfield.getText());
                        String type;
                        if (Urgent.isSelected())
                            type="Urgent";
                        else
                            type="Simple";
                        try {
                            OrderApi.addOrder(new Order(customer,tailor,quant,type));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        Text added=new Text("Added Successfully");
                        added.setFont(Font.font("Gazpacho Bold", FontWeight.BOLD, 20));
                        grid.add(added,0,6,2,1);


                    }
                }
            });


        });
        DisplayOrders.setOnAction(actionEvent -> {
            ArrayList<Order> orders=new ArrayList<>();

            try {
                orders=OrderApi.readOrder();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            TableView<Order> OrderTable = new TableView<>();
            TableColumn<Order,Integer> OrderId = new TableColumn<>("Order ID");
            OrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
            TableColumn<Order,String> customer = new TableColumn<>("Customer");
            customer.setCellValueFactory(cellData->new SimpleStringProperty(String.valueOf(cellData.getValue().getCustomer().getName())));
            TableColumn<Order,String> tailor = new TableColumn<>("Tailor");
            tailor.setCellValueFactory(cellData-> new SimpleStringProperty(String.valueOf(cellData.getValue().getTailor().getName())));
            TableColumn<Order, String> Type=new TableColumn<>("Type");
            Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
            TableColumn<Order,Double> TotalCost=new TableColumn<>("Total Cost");
            TotalCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
            TableColumn<Order,Integer> Quantity = new TableColumn<>("Quantity");
            Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            TableColumn<Order, String> placingDate = new TableColumn<>("Placement Date");
            placingDate.setCellValueFactory(new PropertyValueFactory<>("placingDate"));
            TableColumn<Order, String> Deliverydate=new TableColumn<>("Delivery Date");
            Deliverydate.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
            OrderTable.getColumns().addAll(OrderId,customer,tailor,Type,TotalCost,Quantity,placingDate,Deliverydate);
            ObservableList<Order> Data = FXCollections.observableArrayList(orders);
            OrderTable.setItems(Data);
            border.setCenter(OrderTable);
            OrderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        });
        DeleteOrder.setOnAction(actionEvent -> {
            Text delete = new Text("Delete Order");
            delete.setFont(Font.font("Gazpacho Bold", FontWeight.SEMI_BOLD, 30));

            GridPane gridPane = new GridPane();
            gridPane.setHgap(15);
            gridPane.setVgap(15);
            gridPane.setPadding(new Insets(210, 250, 150, 250));
// gridPane.setAlignment(Pos.CENTER);
            Label Id = new Label("Enter Id: ");
            TextField idfield = new TextField();
            gridPane.add(delete, 0, 0, 2, 1);
            gridPane.add(Id, 0, 1);
            gridPane.add(idfield, 1, 1);
            border.setCenter(gridPane);
            Button enter = new Button("Enter");
            enter.setStyle("-fx-background-color: #43AF50; -fx-text-fill: white; -fx-font-size: 15px;");
            gridPane.add(enter, 1, 2);
            Text invalid1=new Text();
            enter.setOnAction(actionEvent1 -> {
                if (idfield.getText().isEmpty()) {
                     invalid1.setText("Please enter the id field!!");
                    invalid1.setFill(Color.FIREBRICK);
                    gridPane.add(invalid1, 2, 2);
                }
                else
                {invalid1.setVisible(false);
                    int oid= 0;
                    try {
                        oid = OrderApi.searchOrder(Integer.parseInt(idfield.getText()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Text invalid=new Text();
                    if (oid==-1) {
                         invalid.setText("Order ID Not Found!");
                        invalid.setFill(Color.FIREBRICK);
                        gridPane.add(invalid,0,3,2,1);
                    }
                    else {


                        try {
                            OrderApi.removeOrder(Integer.parseInt(idfield.getText()));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        invalid.setVisible(false);
                        Text remove = new Text("Removed Successfully");
                        remove.setFont(Font.font("Gazpacho Bold", FontWeight.BOLD, 20));
                        gridPane.add(remove, 1, 4, 2, 1);
                    }

                }
            });
        });

        EditOrder.setOnAction(actionEvent -> {
            GridPane grid2=new GridPane();
            grid2.setAlignment(Pos.CENTER);
            grid2.setHgap(15);
            grid2.setVgap(15);
            grid2.setPadding(new Insets(210,350,250,250));
            Label EditID=new Label("Order ID: ");
            EditID.setFont(Font.font("Gazpacho Bold", FontWeight.BOLD, 20));
            TextField IDfield=new TextField();
            Button enter =new Button("Enter");
            enter.setStyle("-fx-background-color: #43AF50; -fx-text-fill: white; -fx-font-size: 15px;");

            border.setCenter(grid2);
            grid2.add(EditID,0,0);
            grid2.add(IDfield,1,0);
            grid2.add(enter,1,1);
            enter.setOnAction(actionEvent1 -> {
                Text invalid=new Text();
                if (IDfield.getText().isEmpty()) {
                    invalid.setText("Please Enter the id!!");
                    invalid.setFill(Color.FIREBRICK);
                    grid2.add(invalid,0,2,2,1);
                }
               else
                {
                    invalid.setVisible(false);
                   int oid;
                    int OrderID=Integer.parseInt(IDfield.getText());
                    try {
                       oid=OrderApi.searchOrder(OrderID);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Text invalid1=new Text();
                    if (oid == -1) {
                        invalid1.setVisible(true);
                         invalid1.setText("Order Id Not Found!");
                        invalid1.setFill(Color.FIREBRICK);
                        grid2.add(invalid1, 0, 3, 2, 1);
                    }
                    else
                    {
                        Text text = new Text("Select from the following");
                        text.setFont(Font.font("Gazpacho Bold", FontWeight.SEMI_BOLD, 30));
                        GridPane gridPane = new GridPane();
                        gridPane.setAlignment(Pos.CENTER_LEFT);
                        gridPane.setHgap(10);
                        gridPane.setVgap(10);
                        gridPane.setPadding(new Insets(120, 250, 170, 250));
                        Button Customerr = new Button("Customer");
                        Button tailor = new Button("Tailor");
                        Button quantity = new Button("Quantity");
                        Customerr.setMaxWidth(130);
                        tailor.setMaxWidth(130);
                        quantity.setMaxWidth(130);
                        Customerr.setStyle("-fx-background-color: Navy; -fx-text-fill: white; -fx-font-size: 15px;");
                        tailor.setStyle("-fx-background-color: Navy; -fx-text-fill: white; -fx-font-size: 15px;");
                        quantity.setStyle("-fx-background-color: Navy; -fx-text-fill: white; -fx-font-size: 15px;");
                        gridPane.add(text, 0, 0, 2, 1);
                        gridPane.add(Customerr, 1, 1);
                        gridPane.add(tailor, 1, 2);
                        gridPane.add(quantity, 1, 3);
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
                                            Text invalid2=new Text("Customer Id Not Found!");
                                            invalid2.setFill(Color.FIREBRICK);
                                            gridPane1.add(invalid2, 0, 3, 2, 1);
                                        }
                                        else {
                                           OrderApi.changeCustomer(cus,oid);
                                            Text edited = new Text("Changed Successfully");
                                            edited.setFont(Font.font("Gazpacho Bold", FontWeight.SEMI_BOLD, 25));
                                            gridPane1.add(edited, 1, 2, 2, 1);
                                        }
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                else {
                                    Text invalid3=new Text("Please fill Required field!!");
                                    invalid3.setFill(Color.FIREBRICK);
                                    gridPane1.add(invalid3,0,4,2,1);
                                }
                            });

                        });
                        tailor.setOnAction(actionEvent2 ->{
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

                                            gridPane1.add(invalid, 0, 3, 2, 1);
                                        }
                                        else
                                        {
                                            OrderApi.changeTailor(tailor1,oid);
                                            Text edited = new Text("Changed Successfully");
                                            edited.setFont(Font.font("Gazpacho Bold", FontWeight.SEMI_BOLD, 30));
                                            gridPane1.add(edited, 1, 2, 2, 1);
                                        }
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                else {
                                    Text invalid4=new Text("Please fill Required field!!");
                                    invalid4.setFill(Color.FIREBRICK);
                                    gridPane1.add(invalid4,0,4,2,1);
                                }
                            });
                        });

                        quantity.setOnAction(actionEvent2 -> {
                            GridPane gridPane1=new GridPane();
                            gridPane1.setAlignment(Pos.CENTER);
                            gridPane1.setHgap(10);
                            gridPane1.setVgap(10);
                            border.setCenter(gridPane1);
                            gridPane1.setPadding(new Insets(120, 250, 170, 250));
                            Label Newquantity=new Label("Quantity: ");
                            Newquantity.setFont(Font.font("Gazpacho Bold",FontWeight.SEMI_BOLD, 15));
                            TextField tqfield=new TextField();
                            gridPane1.add(Newquantity,0,0);
                            gridPane1.add(tqfield,1,0);
                            Button enterbutton =new Button("Enter");
                            gridPane1.add(enterbutton,1,1);
                            enterbutton.setStyle("-fx-background-color: #43AF50; -fx-text-fill: white; -fx-font-size: 15px;");
                            enterbutton.setOnAction(actionEvent3 -> {
                                Text invalid5=new Text();
                                if (tqfield.getText().isEmpty()) {

                                     invalid5.setText("Please fill Required field!!");
                                    invalid5.setFill(Color.FIREBRICK);
                                    gridPane1.add(invalid5,0,3,2,1);

                                }
                                else {
                                    invalid5.setVisible(false);
                                    try {
                                        OrderApi.changeQuantity(Integer.parseInt(tqfield.getText()),oid);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    Text edited = new Text("Changed Successfully");
                                    edited.setFont(Font.font("Gazpacho Bold", FontWeight.SEMI_BOLD, 30));
                                    gridPane1.add(edited, 1, 2, 2, 1);
                                }
                            });
                        });

                    }

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
        Scene menu = new Scene(border,defaultwidth,defaultHeight);
        stage.setScene(menu);
        stage.setFullScreen(true);
        stage.setTitle("Menu");
        stage.setFullScreenExitHint("");
        stage.show();
    }
}
