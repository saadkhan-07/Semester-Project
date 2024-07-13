package com.example.tailormanagementsystem;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Order implements Serializable {
public final static long serialVersionUID = 1L;
    private int orderId;
    private String Type;
    private Customer customer;
    private Tailor tailor;
    private String placingDate;
    private String deliveryDate;
    private final double ratePerSuit=2000.0;
    private final double ratePerUrgentSuit=2500.0;
    private Calendar calendar=Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private Date date;

    private double cost;
    private int quantity;

    public Order() {

    }

    public Order(Customer customer, Tailor tailor,int quantity,String type) throws IOException, ParseException {
        orderId=OrderApi.countId();
        this.customer = customer;
        this.tailor = tailor;
       date=new Date();
       this.placingDate=String.valueOf(sdf.format(date));
        calendar.setTime(date);
        this.quantity = quantity;
        setType(type);

    }

    public double getCost() {
        return cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        setType(this.getType());
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
        if (type.equals("Urgent")) {
            setUrgentDeliveryDate();
            setUrgentCost();
        }
        else {
            setDeliveryDate();
            setCost();
        }
    }

    public Tailor getTailor() {
        return tailor;
    }

    public void setTailor(Tailor tailor) {
        this.tailor = tailor;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setCost() {
         cost=this.quantity*this.ratePerSuit;
    }
    public void setUrgentCost()
    {
         cost=this.quantity*this.ratePerUrgentSuit;
    }
    public  void setUrgentDeliveryDate()
    {

calendar.add(Calendar.DAY_OF_MONTH,quantity*1);
deliveryDate=String.valueOf(sdf.format(calendar.getTime()));


    }
    public  void setDeliveryDate()
    {
//        String deldate;
//        deliveryDate= String.valueOf(LocalDate.now());
//        int date=Integer.parseInt(deliveryDate.split("-")[2]);
//
//            date += this.quantity * 2;
//        if (date>31) {
//            date = date - 31;
//            int year= Integer.parseInt(deliveryDate.split("-")[0]);
//
//            int mon= Integer.parseInt(deliveryDate.split("-")[1] )+1;;
//
//            if(mon>12) {
//                mon = mon - 12;
//                year+=1;
//                deldate= String.valueOf(year);
//                deldate += "-" + mon;
//
//            }
//            else {
//                deldate = String.valueOf(year);
//                deldate += "-" + mon;
//            }
//            deldate += "-" + date;
//        }
//
//        else {
//
//            deldate=deliveryDate.split("-")[0];
//            deldate+="-"+deliveryDate.split("-")[1];
//            deldate+="-"+date;
//        }
//        deliveryDate=deldate;
        calendar.add(Calendar.DAY_OF_MONTH,quantity*3);
        deliveryDate=String.valueOf(sdf.format(calendar.getTime()));


    }

    public String getPlacingDate() {
        return placingDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", Type='" + Type + '\'' +
                ", customer=" + customer +
                ", tailor=" + tailor +
                ", placingDate='" + placingDate + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", ratePerSuit=" + ratePerSuit +
                ", ratePerUrgentSuit=" + ratePerUrgentSuit +
                ", cost=" + cost +
                ", quantity=" + quantity +
                '}';
    }
}
