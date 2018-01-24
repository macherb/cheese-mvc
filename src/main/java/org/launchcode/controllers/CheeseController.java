package org.launchcode.controllers;

import org.launchcode.models.Category;
import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CategoryDao;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("cheese")
public class CheeseController {
    @Autowired
    private CheeseDao cheeseDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private MenuDao menuDao;

    // Request path: /cheese
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", Cheese.getTitleList());

        return "cheese/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCheeseForm(Model model) {
        model.addAttribute("title", Cheese.titleAdd);
        model.addAttribute(new Cheese());
        model.addAttribute("categories", categoryDao.findAll());
        return "cheese/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCheeseForm(
            @ModelAttribute  @Valid Cheese newCheese,
            Errors errors,
            @RequestParam int categoryId,
            Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", Cheese.titleAdd);
            model.addAttribute("categories", categoryDao.findAll());
            return "cheese/add";
        }

        Category cat = categoryDao.findOne(categoryId);
        newCheese.setCategory(cat);
        cheeseDao.save(newCheese);
        return "redirect:";//will it have cheeses and title?
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCheeseForm(Model model) {
        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", Cheese.titleRemove);
        return "cheese/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCheeseForm(Model model, @RequestParam int[] cheeseIds) {
        int i = 0;
        String all="";
        final Iterable<Menu> menus = menuDao.findAll();
        for (int cheeseId : cheeseIds) {
            final Cheese cheese = cheeseDao.findOne(cheeseId);
            for (Menu menu : menus) {
                if (menu.getCheeses().contains(cheese)) {
                    i++;
                    all += " " + menu.getName();
                    menu.getCheeses().remove(cheese);
                    menuDao.save(menu);
                    cheeseDao.delete(cheese);
                }
            }
        }
        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", Cheese.titleRemove);
        model.addAttribute("total", "Remove from " + i + " menu(s):" + all);
        return "redirect:";//return "cheese/confirmRemove";
    }

    @RequestMapping(value = "confirmRemove", method = RequestMethod.POST)
    public String processRemoveCheeseForm(@RequestParam int[] cheeseIds) {

        final Iterable<Menu> menus=menuDao.findAll();
        for (int cheeseId: cheeseIds) {
            final Cheese cheese = cheeseDao.findOne(cheeseId);
            for (Menu menu : menus) {
                if (menu.getCheeses().contains(cheese)) {
                    menu.getCheeses().remove(cheese);
                    menuDao.save(menu);
                    cheeseDao.delete(cheese);
                }
            }
        }
        return "redirect:";
    }

    @RequestMapping(value = "category/{id}", method = RequestMethod.GET)
    public String category(Model model, @PathVariable int id) {
        Category cat = categoryDao.findOne(id);
        List<Cheese> cheeses = cat.getCheeseList();
        model.addAttribute("cheeses", cheeses);
        model.addAttribute("title", "Cheeses in Category: " + cat.getName());
        return "cheese/category";
    }

    @RequestMapping(value = "edit/{cheeseId}", method = RequestMethod.GET)
    public String displayEditForm(Model model, @PathVariable int cheeseId) {
        model.addAttribute("title", "Edit Cheese");
        Cheese cheese = cheeseDao.findOne(cheeseId);
        model.addAttribute(cheese);
        model.addAttribute("category", cheese.getCategory());
        model.addAttribute("categories", categoryDao.findAll());
        return "cheese/edit";
    }

    @RequestMapping(value = "edit/{cheeseId}", method = RequestMethod.POST)
    public String processEditForm(Model model,
                                  @ModelAttribute  @Valid Cheese cheese,
                                  Errors errors,
                                  @PathVariable int cheeseId,
                                  int categoryId) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit Cheese");
            model.addAttribute(cheese);
            model.addAttribute("category", categoryId);
            model.addAttribute("categories", categoryDao.findAll());
            return "cheese/edit";
        }

        Cheese newCheese = cheeseDao.findOne(cheeseId);

        newCheese.setName(cheese.getName());

        newCheese.setDescription(cheese.getDescription());

        Category cat = categoryDao.findOne(categoryId);
        newCheese.setCategory(cat);

        cheeseDao.save(newCheese);
        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", Cheese.getTitleList());
        return "cheese/index";//return "redirect:";//return "cheese/edit";
    }
}
