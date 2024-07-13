package com.example.tailormanagementsystem;

import java.io.*;
import java.util.ArrayList;

public class CusrtomerApi {

    public static  ArrayList<Customer> customers=new ArrayList<>();



    public static void addCustomer(Customer customer) throws IOException {
        customers=readCustomer();
        customers.add(customer);
        writeCustomer();
    }

    public static int searchCustomer(int id) throws IOException {
        customers=readCustomer();
        int index=-1;
        for (int i = 0; i < customers.size(); i++)
            if (id==customers.get(i).getId())
                index=i;
        return index;
    }

    public static Customer getCustomer(int id) throws IOException {
        customers=readCustomer();
        Customer customer=new Customer();
        for (int i = 0; i < customers.size(); i++)
            if (id==customers.get(i).getId())
               customer=customers.get(i);
        return customer;
    }



    public static void removeCustomer(int id) throws IOException {
        customers=readCustomer();
        for (int i=0;i<customers.size();i++)
            if (id==customers.get(i).getId())
            {
                customers.remove(customers.get(i));

            }
        writeCustomer();
    }
    public static void changeName(String newName,int index) throws IOException {
       customers.get(index).setName(newName);
        writeCustomer();
    }
    public static void changeEmail(String newEmail,int index) throws IOException {
       customers.get(index).setEmail(newEmail);
        writeCustomer();
    }

    public static void changeSize(String newSize,int index) throws IOException {
        customers.get(index).setSize(newSize);
        writeCustomer();
    }


    public static void writeCustomer() throws IOException {
        File file = new File("customer.ser");
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(customers);
        oos.close();
        fos.close();
    }

    public static int countId()throws IOException
    {
        customers=readCustomer();
        int id=0;
        if (!customers.isEmpty())
        {
            for(int i=0;i<customers.size();i++)
                id=customers.get(i).getId();

        }
        id++;
        return id;
    }

    public static ArrayList<Customer> readCustomer() throws IOException {
        ArrayList<Customer> Rcustomers = new ArrayList<>();
        File file = new File("customer.ser");

        try {
            if (file.length()!=0)
            {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Rcustomers = (ArrayList<Customer>) ois.readObject();
            }
        } catch (ClassNotFoundException | FileNotFoundException | InvalidClassException e) {
            System.out.println(e.getMessage());
        }
        return Rcustomers;
    }
}
