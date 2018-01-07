package org.launchcode.controllers;

import org.launchcode.models.User;
import org.launchcode.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserDao userDao;

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
}
