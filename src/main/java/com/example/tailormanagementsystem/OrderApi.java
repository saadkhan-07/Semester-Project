package com.example.tailormanagementsystem;

import java.io.*;
import java.util.ArrayList;

public class OrderApi {
   static ArrayList<Order> orders=new ArrayList<>();

   public static int searchOrder(int id) throws IOException {
       orders=readOrder();
       int index=-1;
       for (int i = 0; i < orders.size(); i++)
           if (id==orders.get(i).getOrderId())
               index=i;
       return index;
   }
   public static int countId() throws IOException {
       orders=readOrder();
       int id=0;
       for (int i=0;i<orders.size();i++)
       {
           id = orders.get(i).getOrderId();
       }
       id++;
       return id;

    }
    public static void removeOrder(int id) throws IOException {
        orders=readOrder();
        for (int i=0;i<orders.size();i++)
            if (id==orders.get(i).getOrderId())
            {
                orders.remove(orders.get(i));

            }
        writeOrder();
    }
    public static void changeCustomer(Customer customer,int index) throws IOException
    {
        orders=readOrder();
        orders.get(index).setCustomer(customer);
        writeOrder();

    }
    public static void changeTailor(Tailor tailor,int index) throws IOException {
        orders=readOrder();
        orders.get(index).setTailor(tailor);
        writeOrder();
    }
    public static void changeQuantity(int quantity, int index) throws IOException {
       orders=readOrder();
       orders.get(index).setQuantity(quantity);
       writeOrder();
    }
    public static void addOrder(Order order) throws IOException {
       orders=readOrder();
       orders.add(order);
       writeOrder();
   }

    public static void writeOrder() throws IOException {

        File file=new File("order.ser");
        file.createNewFile();
        FileOutputStream fos=new FileOutputStream(file);
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(fos);
        objectOutputStream.writeObject(orders);
        objectOutputStream.close();
        fos.close();


    }
    public static ArrayList<Order> readOrder() throws IOException {
        File file=new File("order.ser");
        ArrayList<Order> Rorders=new ArrayList<>();
        try {
            file.createNewFile();

            if (file.length()!=0)
            {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Rorders = (ArrayList<Order>) ois.readObject();
            }

        } catch (ClassNotFoundException | FileNotFoundException | InvalidClassException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
        return Rorders;
    }

}
