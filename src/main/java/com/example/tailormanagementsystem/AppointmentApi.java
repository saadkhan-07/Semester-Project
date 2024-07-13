package com.example.tailormanagementsystem;

import java.io.*;
import java.util.ArrayList;

public class AppointmentApi {


  private static   ArrayList<Appointment> appointments=new ArrayList<>();

    public static int searchAppointment(int id) throws IOException {
        appointments=getAppointments();
        int index=-1;
        if (appointments.isEmpty())
            return -1;
        for (int i = 0; i < appointments.size(); i++)
            if (id==appointments.get(i).getId())
                index=i;
        return index;
    }
    public static Appointment getAppointment(int id) throws IOException {
        appointments=getAppointments();

     Appointment appointment= new Appointment();
        for (int i = 0; i < appointments.size(); i++)
            if (id==appointments.get(i).getId())
                appointment= appointments.get(i);
        return appointment;
    }

public static void changeCustomer(Customer customer,int index) throws IOException
{
    appointments=getAppointments();
    appointments.get(index).setCustomer(customer);
    writeAppointment();

}
public static void changeTailor(Tailor tailor,int index) throws IOException {
    appointments=getAppointments();
    appointments.get(index).setTailor(tailor);
    writeAppointment();
}
public static boolean changeTime(String time,int index) throws IOException {
    appointments=getAppointments();
    Tailor tailor=appointments.get(index).getTailor();
    if (TailorApi.isAvailable(tailor,time)) {
        appointments.get(index).setTime(time);
        writeAppointment();
        return true;
    }
    return false;
}
    public static int countId()
    {
        appointments=getAppointments();
        int id=0;
        for (int i=0;i<appointments.size();i++)
        {
            id = appointments.get(i).getId();
        }
        id++;
        return id;
    }

    public static void removeAppointment(int id) throws IOException {
        appointments=getAppointments();
        for (int i=0;i<appointments.size();i++)
            if (id==appointments.get(i).getId())
            {
                appointments.remove(appointments.get(i));

            }
        writeAppointment();
    }

    public static boolean addAppointment(Appointment appointment) throws IOException {

     if (checkAppointment(appointment))
     {
         if ( TailorApi.isAvailable(appointment.getTailor(),appointment.getTime()) )
         {
             appointments.add(appointment);
             writeAppointment();
             return true;
         }
         else {

             return false;
         }
     }
     else
         return false;

    }
    public static boolean checkAppointment(Appointment app)
    {
        appointments=getAppointments();
       if (!appointments.isEmpty())
       {
           for (Appointment appointment: appointments)
               if (app.getTailor().equals(appointment.getTailor()))
                   if (app.getTime().equals(appointment.getTime()))
                       return false;
           return true;
       }
       else
           return true;
    }
    public static boolean analyzeAppointment(Appointment app,String time)
    {
        appointments=getAppointments();
        if (!appointments.isEmpty())
        {
            for (Appointment appointment: appointments)
                if (app.getTailor().equals(appointment.getTailor()))
                    if (time.equals(appointment.getTime()))
                        return false;
            return true;
        }
        else
            return true;
    }
    public static void writeAppointment() throws IOException {
        File file = new File("Appointments.ser");
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(appointments);
        oos.close();
        fos.close();
    }

    public static ArrayList<Appointment> getAppointments() {
        File file=new File("Appointments.ser");
      ArrayList<Appointment> Rappointments=new ArrayList<>();

        try {
            file.createNewFile();
            if (file.length()!=0)
            {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Rappointments = (ArrayList<Appointment>)ois.readObject();
            }
        } catch (ClassNotFoundException | FileNotFoundException | InvalidClassException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
        return Rappointments;

    }
}
