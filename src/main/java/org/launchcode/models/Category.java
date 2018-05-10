package org.launchcode.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=15, message = "Name must be between three and fifteen characters")
    private String name;

    @OneToMany
    @JoinColumn(name = "category_id")
    private List<Cheese> cheeseList = new ArrayList<>();

    public static String titleList = "Categories";

    public static String titleAdd = "Add Category";

    public static String titleRemove = "Remove Category";

    public static String titleEdit = "Edit Category";

    public Category() {
    }

    public Category(String name) {
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

    public List<Cheese> getCheeseList() {
        return cheeseList;
    }

    public void setCheeseList(List<Cheese> cheeses) {
        this.cheeseList = cheeses;
    }
}
