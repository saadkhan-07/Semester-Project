package com.example.tailormanagementsystem;

import java.io.IOException;
import java.io.Serializable;

public class Customer extends Person implements Serializable{
    private final static long serialVersionUID=10;
    private int id;
    private static int count =0;
    private String size;
    public Customer(String name, String email,String size) throws IOException {
        super(name, email);
        this.id = CusrtomerApi.countId();
        this.size = size;
    }
    public Customer()
    {

    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", size='" + size + '\'' +
                '}';
    }
}

