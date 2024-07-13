package com.example.tailormanagementsystem;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Tailor extends Person implements Serializable {
    private final static long serialVersionUID=11;
    private  int tailorId;
    private static int count = 0;
    private double Salary;
    private  String startingTime="12:00";
    private String endingTime="18:00";



    public Tailor(String name, String email, double salary) throws IOException {
        super(name, email);
        Salary = salary;
       tailorId=TailorApi.countId();
    }
    Tailor() throws IOException {
    }


    public void setTailorId() {
        this.tailorId = ++count;
    }

    public int getTailorId() {
        return tailorId;
    }

    public double getSalary() {
        return Salary;
    }

    public void setSalary(double salary) {
        Salary = salary;
    }

    public static void setCount(int count) {
        Tailor.count = count;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    public String getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(String endingTime) {
        this.endingTime = endingTime;
    }

    @Override
    public boolean equals(Object obj) {
        Tailor t=(Tailor) obj;
        return this.getTailorId()==t.getTailorId() ;
    }

    @Override
    public String toString() {
        return "Tailor{" +
                "tailorId=" + tailorId +
                ", Salary=" + Salary +
                ", startingTime='" + startingTime + '\'' +
                ", endingTime='" + endingTime + '\'' +
                '}';
    }

    public static void main(String[] args) throws IOException {

//    int index=AppointmentApi.searchAppointment(2);
//    Customer customer=CusrtomerApi.getCustomer(2);
//        System.out.println(AppointmentApi.getAppointment(2).getCustomer().getName());
//    AppointmentApi.changeCustomer(customer,index);
//        System.out.println(AppointmentApi.getAppointment(2).getCustomer().getName());
//
//Order order=new Order(new Customer("ALi","ans@gmail.com","Small"),new Tailor("Saad","saad@gmail.com",90000),2,"Simple");
////       OrderApi.addOrder(order);
//       OrderApi.changeQuantity(3,0);

       ArrayList<Order> orders=OrderApi.readOrder();
       for (Order order1:orders)
           System.out.println(order1);


    }
}
