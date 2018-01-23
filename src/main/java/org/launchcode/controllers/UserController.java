package org.launchcode.controllers;

import org.launchcode.models.Menu;
import org.launchcode.models.User;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.data.UserDao;
import org.launchcode.models.forms.AddUserItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserDao userDao;

    @Autowired
    private MenuDao menuDao;

    // Request path: /user
    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("title", "Hello username");
        model.addAttribute("users", userDao.findAll());
        return "user/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("title", "Add User");
        model.addAttribute(new User());
        return "user/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            return "user/add";
        }

        if (user == null) {
            model.addAttribute("title", "user is null");
            return "user/add";
        }
        else if (user.getPassword() == null) {
            model.addAttribute("title", "password is null");
            return "user/add";
        }
        else if (user.getPassword().equals("")) {
            model.addAttribute("title", "password is empty");
            return "user/add";
        }
        else if (!user.getPassword().equals(user.getVerify())) {
            model.addAttribute("title", "password \"" + user.getPassword() + "\" not equal to verify \"" + user.getVerify() + '"');
            return "user/add";
        }
        model.addAttribute("title", "Hello " + user.getUsername());
        userDao.save(user);
        return "redirect:";//"user/index";//
    }

    @RequestMapping(value = "view/{userId}", method = RequestMethod.GET)
    public String viewUser(Model model, @PathVariable int userId) {

        User user = userDao.findOne(userId);
        model.addAttribute("title", user.getUsername());
        model.addAttribute("menus", user.getMenus());
        model.addAttribute("userId", user.getId());
        return "user/view";
    }

    @RequestMapping(value = "add-item/{userId}", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int userId) {
        User user = userDao.findOne(userId);
        AddUserItemForm form = new AddUserItemForm(
                menuDao.findAll(),
                user);

        model.addAttribute("title", "Add item to user: " + user.getUsername());
        model.addAttribute("form", form);
        return "user/add-item";
    }

    @RequestMapping(value = "add-item", method = RequestMethod.POST)
    public String addItem(Model model,
                          @ModelAttribute @Valid AddUserItemForm form, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("form", form);
            return "user/add-item";
        }
        Menu theMenu = menuDao.findOne(form.getMenuId());
        User theUser = userDao.findOne(form.getUserId());
        theUser.addItem(theMenu);
        userDao.save(theUser);
        return "redirect:view/" + theUser.getId();
    }
}
