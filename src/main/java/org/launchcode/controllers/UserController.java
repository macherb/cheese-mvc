package com.example.cheesemvc.controllers;

import com.example.cheesemvc.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("user")
public class UserController {
    // Request path: /user
    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("title", "Hello username");
        return "user/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("title", "Add User");
        model.addAttribute(new User());
        model.addAttribute("verify", "");
        return "user/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute User user, String verify) {
        model.addAttribute(user);
        model.addAttribute(verify);
        /*if (user == null) {
            model.addAttribute("title", "Add User");
            model.addAttribute("verify", "");
            return "user/add";
        }
        else */if (user.getPassword() == null) {
            model.addAttribute("title", "Add User");
            model.addAttribute("verify", "");
            return "user/add";
        }
        else if (user.getPassword().equals("")) {
            model.addAttribute("title", "Add User");
            model.addAttribute("verify", "");
            return "user/add";
        }
        else if (!user.getPassword().equals(verify)) {
            model.addAttribute("title", "Add User");
            model.addAttribute("verify", "");
            return "user/add";
        }
        model.addAttribute("title", "Hello username");
        return "user/index";//"redirect:";
    }
}
