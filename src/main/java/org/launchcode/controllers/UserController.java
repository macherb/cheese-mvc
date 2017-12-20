package org.launchcode.controllers;

import org.launchcode.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

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
        String  verify = "password";
        model.addAttribute("title", "Add User");
        model.addAttribute(new User());
        model.addAttribute("verify", "verify");
        model.addAttribute(verify);
        return "user/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute User user, @ModelAttribute @Valid String verify) {
        model.addAttribute(user);
        if (verify == null) {
            model.addAttribute("title", "verify is null");
            model.addAttribute("verify", "verify");
            return "user/add";
        }
        model.addAttribute(verify);
        if (user == null) {
            model.addAttribute("title", "user is null");
            model.addAttribute("verify", "verify");
            return "user/add";
        }
        else if (user.getPassword() == null) {
            model.addAttribute("title", "password is null");
            model.addAttribute("verify", "verify");
            return "user/add";
        }
        else if (user.getPassword().equals("")) {
            model.addAttribute("title", "password is empty");
            model.addAttribute("verify", "verify");
            return "user/add";
        }
        else if (!user.getPassword().equals(user.getVerify())) {
            model.addAttribute("title", "password \"" + user.getPassword() + "\" not equal to verify \"" + user.getVerify() + '"');
            model.addAttribute("verify", "verify");
            return "user/add";
        }
        model.addAttribute("title", "Hello " + user.getUsername());
        return "user/index";//"redirect:";
    }
}
