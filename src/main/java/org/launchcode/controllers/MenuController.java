package org.launchcode.controllers;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.User;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.data.UserDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "menu")
public class MenuController {
    @Autowired
    private UserDao userDao;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private CheeseDao cheeseDao;

    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("title", Menu.titleList);
        model.addAttribute("menus", menuDao.findAll());

        return "menu/index";
    }

    @RequestMapping(value = "view/{menuId}", method = RequestMethod.GET)
    public String viewMenu(Model model, @PathVariable int menuId) {

        Menu menu = menuDao.findOne(menuId);
        model.addAttribute("title", menu.getName());
        model.addAttribute("cheeses", menu.getCheeses());
        model.addAttribute("menuId", menu.getId());
        return "menu/view";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddMenuForm(Model model) {
        model.addAttribute("title", Menu.titleAdd);
        model.addAttribute(new Menu());
        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model,
                      @ModelAttribute @Valid Menu menu,
                      Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", Menu.titleAdd);
            return "menu/add";
        }

        menuDao.save(menu);

        return "redirect:view/" + menu.getId();
    }

    @RequestMapping(value = "add-item/{menuId}", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int menuId) {
        Menu menu = menuDao.findOne(menuId);
        AddMenuItemForm form = new AddMenuItemForm(
                cheeseDao.findAll(),
                menu);

        model.addAttribute("title", "Add item to menu: " + menu.getName());
        model.addAttribute("form", form);
        return "menu/add-item";
    }

    @RequestMapping(value = "add-item", method = RequestMethod.POST)
    public String addItem(Model model,
    @ModelAttribute @Valid AddMenuItemForm form, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("form", form);
            return "menu/add-item";
        }
        Cheese theCheese = cheeseDao.findOne(form.getCheeseId());
        Menu theMenu = menuDao.findOne(form.getMenuId());
        theMenu.addItem(theCheese);
        menuDao.save(theMenu);
        return "redirect:view/" + theMenu.getId();
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveMenuForm(Model model) {
        model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("title", Menu.titleRemove);
        return "menu/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveMenuForm(Model model, @RequestParam int[] menuIds) {
        /*for (int menuId : menuIds) {
            final Menu menu = menuDao.findOne(menuId);
            menuDao.delete(menu);
        }
        model.addAttribute("categories", menuDao.findAll());
        model.addAttribute("title", Menu.titleRemove);
*/
        int i = 0;
        String all="";
        final Iterable<User> users = userDao.findAll();
        for (int menuId : menuIds) {
            final Menu menu = menuDao.findOne(menuId);
            for (User user : users) {
                if (user.getMenus().contains(menu)) {
                    i++;
                    all += " " + user.getUsername();
                    user.getMenus().remove(menu);
                    userDao.save(user);
                    //menuDao.delete(menu);
                }
                menuDao.delete(menu);
            }
        }
        model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("title", Menu.titleRemove);
        model.addAttribute("total", "Remove from " + i + " user(s):" + all);
        return "redirect:";//"menu/confirmRemove";
    }

    @RequestMapping(value = "edit/{menuId}", method = RequestMethod.GET)
    public String displayEditForm(Model model, @PathVariable int menuId) {
        model.addAttribute("title", Menu.titleEdit);
        Menu menu = menuDao.findOne(menuId);
        model.addAttribute(menu);

        return "menu/edit";
    }

    @RequestMapping(value = "edit/{menuId}", method = RequestMethod.POST)
    public String processEditForm(Model model,
                                  @ModelAttribute  @Valid Menu menu,
                                  Errors errors,
                                  @PathVariable int menuId) {
        if (errors.hasErrors()) {
            model.addAttribute("title", Menu.titleEdit);
            model.addAttribute(menu);
            return "menu/edit";
        }

        Menu newMenu = menuDao.findOne(menuId);

        newMenu.setName(menu.getName());

        menuDao.save(newMenu);
        model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("title", Menu.titleList);
        return "menu/index";//return "redirect:";//return "menu/edit";
    }

}
