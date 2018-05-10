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
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("title", User.titleList);
        model.addAttribute("users", userDao.findAll());
        return "user/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("title", User.titleAdd);
        model.addAttribute(new User());
        return "user/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            model.addAttribute("title", User.titleAdd);
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

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveUserForm(Model model) {
        model.addAttribute("users", userDao.findAll());
        model.addAttribute("title", User.titleRemove);
        return "user/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveUserForm(Model model, @RequestParam int[] userIds) {
        for (int userId : userIds) {
            final User user = userDao.findOne(userId);
            userDao.delete(user);
        }
        model.addAttribute("categories", userDao.findAll());
        model.addAttribute("title", User.titleRemove);
        return "redirect:";//"user/confirmRemove";
    }

    @RequestMapping(value = "edit/{userId}", method = RequestMethod.GET)
    public String displayEditForm(Model model, @PathVariable int userId) {
        model.addAttribute("title", User.titleEdit);
        User user = userDao.findOne(userId);
        model.addAttribute(user);

        return "user/edit";
    }

    @RequestMapping(value = "edit/{userId}", method = RequestMethod.POST)
    public String processEditForm(Model model,
                                  @ModelAttribute  @Valid User user,
                                  Errors errors,
                                  @PathVariable int userId) {
        if (errors.hasErrors()) {
            model.addAttribute("title", User.titleEdit);
            model.addAttribute(user);
            return "user/edit";
        }

        User newUser = userDao.findOne(userId);

        newUser.setUsername(user.getUsername());

        newUser.setEmail(user.getEmail());

        newUser.setPassword(user.getPassword());

        newUser.setVerify(user.getVerify());

        userDao.save(newUser);
        model.addAttribute("users", userDao.findAll());
        model.addAttribute("title", User.titleList);
        return "user/index";//return "redirect:";//return "user/edit";
    }
}
