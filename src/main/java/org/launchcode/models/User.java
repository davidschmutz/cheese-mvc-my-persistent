package org.launchcode.models;

import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull(message = "Username may not be empty")
    @Size(min=3, max=15, message = "Username must have from 3 to 15 characters.")
    @Pattern(regexp="[a-z]+", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Username must contain only letters.")
    private String username;

    @Email(message="Email address must be properly formed.")
    private String email;

    private UserType type;

    @Size(min=4, message = "Your password must contain at least 4 characters.")
    private String password;

    @NotNull(message = "Passwords do not match.")
    private String verifyPassword;

    private Date creationDate;

    public User(){
        creationDate = new Date();
    }

    public User(String username, String email, UserType type, String password) {
        this();
        this.username = username;
        this.email = email;
        this.type = type;
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

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public String getFormattedDate(){
        return new SimpleDateFormat("MMM d yyyy hh:mm a").format(getCreationDate());
    }
}
