
package com.example.tailormanagementsystem;

import java.io.*;

public class UserAPi implements Serializable {


public static void writeUser(User user) throws IOException {
    FileOutputStream fos = new FileOutputStream(new File("Users.ser"));
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    oos.writeObject(user);
    oos.close();
    fos.close();
}
public static void setPassword(String password) throws IOException {
    User user= readUser();
    user.setPassword(password);
    UserAPi.writeUser(user);
}
public static boolean checkUser(User user) throws IOException {
    User checkUser = readUser();
    if(user.equals(checkUser)) {
        return true;
    }
    else
        return false;
}
public static boolean checkCode(int code) throws IOException {
    User checkUser = readUser();
    if(code==checkUser.getResetcode())
        return true;
    else
        return false;
}
public static User readUser() throws IOException {
    User user=new User();
    File file=new File("Users.ser");
    if(file.length() != 0)
{


    try {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        user =(User)ois.readObject();
        ois.close();
        fis.close();
    }
    catch (ClassNotFoundException | FileNotFoundException |InvalidClassException e) {
        System.out.println(e.getMessage());
    }
}
else
{
    user=new User();
}
    return user;
}

}
