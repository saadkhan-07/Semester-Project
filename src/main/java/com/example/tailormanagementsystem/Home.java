package com.example.tailormanagementsystem;

import java.io.IOException;

class Home{
    public static void main(String[] args) throws IOException {
        Appointment appointment=new Appointment(new Customer("Ali","alskfja","small"),new Tailor("saad","salfjdfs",9320421),"23:00");
        System.out.println(appointment);
    }
}
