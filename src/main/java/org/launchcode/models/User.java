package org.launchcode.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {
    @NotNull
    @Size(min=1, message = "User Name must not be empty")
    private String  username;

    @NotNull
    @Size(min=1, message = "Email must not be empty")
    private String  email;

    @NotNull
    @Size(min=1, message = "Password must not be empty")
    private String  password;

    @NotNull
    @Size(min=1, message = "Verify password must not be empty")
    private String  verify;

    public User(String username, String email, String password, String verify) {
        this.username = username;
        this.email =    email;
        this.password = password;
    }

    public User() {
        ;
    }

    public String getUsername() {
        return  username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return  email;
    }

    public void setEmail(String email) {
        this.email =    email;
    }

    public String getPassword() {
        return  password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerify() {
        return  verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }
}
