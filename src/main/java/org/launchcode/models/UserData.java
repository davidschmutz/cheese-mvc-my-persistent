package org.launchcode.models;

import java.util.ArrayList;

public class UserData {

    private static ArrayList<User> users = new ArrayList<>();

    //getAll
    public static ArrayList<User> getAll(){
        return users;
    }

    //add
    public static void add(User newUser){
        users.add(newUser);
    }

    //remove
    public static void remove(int id){
        User user = getById(id);
        users.remove(user);
    }

    //getById
    public static User getById(int id){
        for(User user: users){
            if(user.getUserID()==id) return user;
        }
        return null;
    }
}
