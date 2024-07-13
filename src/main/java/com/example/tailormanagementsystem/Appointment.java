package com.example.tailormanagementsystem;

import java.io.Serializable;
import java.time.LocalDate;

import java.util.Date;

public class Appointment implements Serializable {
    private static final long serialVersionUID=1;

    private Customer customer;
    private Tailor tailor;
    private String time;
    private String type;
        private int id;
    private static int count=0;
    private  LocalDate date;
public Appointment()
{

}
    public Appointment(Customer customer, Tailor tailor,String time) {
        this.customer = customer;
        this.tailor = tailor;
        this.time = time;
        this.id=AppointmentApi.countId();
        this.date=LocalDate.now();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Tailor getTailor() {
        return tailor;
    }

    public void setTailor(Tailor tailor) {
        this.tailor = tailor;
    }
    @Override
    public String toString() {
        return "Appointment{" +
                "customer=" + customer +
                ", tailor=" + tailor +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                ", id=" + id +
                ", date=" + date +
                '}';
    }
}
