package org.launchcode.controllers;

import org.launchcode.models.User;
import org.launchcode.models.UserData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("user")
public class UserController {
    private User loggedInUser;

    @RequestMapping("")
    public String index(Model model){
        if(loggedInUser == null) return "redirect:/user/add";
        model.addAttribute("user", loggedInUser);
        model.addAttribute("users", UserData.getAll());
        return "user/index";
    }

    @RequestMapping("{userID}")
    public String index(Model model, @PathVariable int userID){
        model.addAttribute("user", UserData.getById(userID));
        return "user/detail";
    }


    @RequestMapping(value="add", method = RequestMethod.GET)
    public String add(Model model, @ModelAttribute User user){
        model.addAttribute(new User());
        return "user/add";
    }

    @RequestMapping(value="add", method = RequestMethod.POST)
    public String add(Model model, @Valid @ModelAttribute User user, Errors errors){
        model.addAttribute("user", user);
        if(errors.hasErrors()) return "user/add";

        UserData.add(user);
        loggedInUser = user;
        //model.addAttribute("users", UserData.getAll());
        return "redirect:/user";
    }

}
