package org.launchcode.models.forms;

import org.launchcode.models.Menu;
import org.launchcode.models.User;

public class AddUserItemForm {
    private User user;

    private Iterable<Menu> menus;

    private int userId;

    private int menuId;

    public AddUserItemForm() {
        ;
    }

    public AddUserItemForm(Iterable<Menu> menus, User user) {
        this.user = user;
        this.menus =  menus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Iterable<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Iterable<Menu> menus) {
        this.menus = menus;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
}
