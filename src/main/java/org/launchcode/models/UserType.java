package org.launchcode.models;

public enum UserType {

    DEFAULT ("..."),
    ADMINISTRATOR ("Administrator"),
    FULL_STACK ("Full Stack Developer"),
    FRONT_END ("Front End Developer"),
    BACK_END ("Back End Developer");

    private final String name;

    UserType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
