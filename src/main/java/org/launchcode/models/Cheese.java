package org.launchcode.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Cheese {

    @NotNull
    @Size(min=3, max=20, message="Name must have from 3 to 20 characters.")
    private String name;

    @NotNull
    @Size(min=1, message="Description must not be empty.")
    private String description;

    private CheeseType type;

    @Min(value=1, message="Rating must be from 1 to 5.")
    @Max(value=5, message="Rating must be from 1 to 5.")
    private int rating;

    private int cheeseId;
    private static int nextId = 1;

    public Cheese(){
        cheeseId = nextId;
        nextId ++;
    }

    public Cheese(String name, String description, CheeseType type, int rating) {
        this();
        this.name = name;
        this.description = description;
        this.type = type;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public int getCheeseId() {
        return cheeseId;
    }

    public void setCheeseId(int cheeseId) {
        this.cheeseId = cheeseId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CheeseType getType() {
        return type;
    }

    public void setType(CheeseType type) {
        this.type = type;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
