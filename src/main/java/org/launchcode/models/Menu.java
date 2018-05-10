package org.launchcode.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Menu {
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=15, message = "Name must be between three and fifteen characters")
    private String name;

    @ManyToMany
    private List<Cheese> cheeses;

    public static final String titleList = "Menus";

    public static final String titleAdd = "Add Menu";

    public static final String titleRemove = "Remove Menu";

    public static final String titleEdit = "Edit Menu";

    public Menu() {

    }

    public Menu(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addItem(Cheese item) {
        cheeses.add(item);
    }

    /**public void removeItem(Cheese item) {
        cheeses.remove(item);
    }**/

    public List<Cheese> getCheeses() {
        return cheeses;
    }

    public void setCheeses(List<Cheese> cheeses) {
        this.cheeses = cheeses;
    }
}
