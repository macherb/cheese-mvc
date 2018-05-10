package org.launchcode.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by LaunchCode
 */
@Entity
public class Cheese {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=15, message = "Name must be between three and fifteen characters")
    private String name;

    @NotNull
    @Size(min=1, message = "Description must not be empty")
    private String description;

    @ManyToOne
    private Category category;

    ///@ManyToMany(mappedBy = "cheeses")
    ///private List<Menu> menus;

    ///private List<Cheese> cheeses;

    public static String titleList = "Cheeses";

    public static String titleAdd = "Add Cheese";

    public static String titleRemove = "Remove Cheese";

    public static String titleEdit = "Edit Cheese";

    public Cheese(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Cheese() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    /*public static String getTitleList() {
        return titleList;
    }*/

    /*public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }*/
}
