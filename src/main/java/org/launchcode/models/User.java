package org.launchcode.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;

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

    @ManyToMany
    private List<Menu> menus;

    public static final String titleList = "Users";

    public static final String titleAdd = "Add User";

    public static final String titleRemove = "Remove User";

    public static final String titleEdit = "Edit User";

    public User(String username, String email, String password, String verify) {
        this.username = username;
        this.email =    email;
        this.password = password;
    }

    public User() {
        ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void addItem(Menu item) {
        menus.add(item);
    }

    /**public void removeItem(Menu item) {
     menus.remove(item);
     }**/

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
