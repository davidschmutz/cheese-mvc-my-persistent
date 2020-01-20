package org.launchcode.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

public class User {

    @NotEmpty
    @Size(min=3, max=15, message = "Username must have from 3 to 15 characters.")
    @Pattern(regexp="[a-z]+", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Username must contain only letters.")
    private String username;

    @Email(message="Email address must be properly formed.")
    private String email;

    @Size(min=6,message = "Your password must contain at least 6 characters.")
    private String password;

    @NotNull(message = "Passwords do not match.")
    private String verifyPassword;


    private int userID;
    private Date creationDate;

    private static int nextID = 1;

    public User(){
        userID = nextID;
        nextID++;
        creationDate = new Date();
    }

    public User(String username, String email, String password) {
        this();
        this.username = username;
        this.email = email;
        this.password = password;
    }

    private void checkPassword(){
        if(getPassword()==null ||
                getVerifyPassword() == null ||
                !getPassword().equals(getVerifyPassword()))
            verifyPassword=null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        checkPassword();
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
        checkPassword();
    }

    public int getUserID() {
        return userID;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
