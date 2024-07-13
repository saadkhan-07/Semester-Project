package com.example.tailormanagementsystem;

import javax.imageio.IIOException;
import java.io.*;
import java.util.ArrayList;

public class TailorApi {

    public static ArrayList<Tailor> tailors = new ArrayList<Tailor>();
   public static int searchTailor(int id) throws IOException {
       tailors=readTailor();
       int index=-1;
       for (int i = 0; i < tailors.size(); i++)
           if (id==tailors.get(i).getTailorId())
               index=i;
       return index;
   }
    public static int countId()throws IOException
    {
        File file=new File("tailors.ser");
        file.createNewFile();
        if (file.length()!=0)
            tailors=TailorApi.readTailor();
        int id=0;
       if (!tailors.isEmpty())
       {
           for(int i=0;i<tailors.size();i++)
               id=tailors.get(i).getTailorId();

       }
        id++;
        return id;
    }
    public static void addTailor(Tailor tailor) throws IOException {
      tailors=readTailor();
        tailors.add(tailor);
        writeTailor();
    }
    public static void changeName(String newName,int index) throws IOException {
        tailors.get(index).setName(newName);
        writeTailor();
    }
    public static void changeSalary(Double newSalary,int index) throws IOException {
        tailors.get(index).setSalary(newSalary);
        writeTailor();
    }
    public static void changeEmail(String newEmail,int index) throws IOException {
        tailors.get(index).setEmail(newEmail);
        writeTailor();
    }

    public static void writeTailor() throws IOException {
        File file = new File("tailors.ser");
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(tailors);
        oos.close();
        fos.close();
    }


    public static Tailor getTailor(int id) throws IOException {
        tailors=readTailor();
       Tailor tailor=new Tailor();
        for (int i = 0; i < tailors.size(); i++)
            if (id==tailors.get(i).getTailorId())
               tailor=tailors.get(i);
    return tailor;
    }

    public static boolean isAvailable(Tailor tailor,String time)
    {
        int hour=0;
       try{

            hour=Integer.parseInt(time.split(":")[0]);
       }
       catch(NumberFormatException e) {
           System.out.println(e.getMessage());
       }
        int minute=Integer.parseInt(time.split(":")[1]);
        if((hour>=Integer.parseInt(tailor.getStartingTime().split(":")[0]))  && (hour<=Integer.parseInt(tailor.getEndingTime().split(":")[0])))
        {
            return true;
        }
        else
            return false;
    }

    public static void removeTailor(int id) throws IOException {
       tailors=readTailor();
        for (int i=0;i<tailors.size();i++)
            if (id==tailors.get(i).getTailorId())
            {
                tailors.remove(tailors.get(i));

            }
        writeTailor();
    }

    public static ArrayList<Tailor> readTailor() throws IOException {
        ArrayList<Tailor> Rtailors = new ArrayList<>();
        File file = new File("tailors.ser");
        file.createNewFile();
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Rtailors = (ArrayList<Tailor>) ois.readObject();
        } catch (ClassNotFoundException | FileNotFoundException | InvalidClassException | EOFException e) {
//            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return Rtailors;
    }
}