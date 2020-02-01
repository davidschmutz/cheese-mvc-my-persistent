package org.launchcode.controllers;

import org.launchcode.models.User;
import org.launchcode.models.UserType;
import org.launchcode.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Don't forget the JavaDocs
 */

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserDao userDao;

    private User loggedInUser;

    @RequestMapping(value = "")
    public String index(Model model){
        if(loggedInUser == null){
            return "redirect:/user/add";
        }
        model.addAttribute("user", loggedInUser);
        model.addAttribute("users", userDao.findAll());
        return "user/index";
    }

    @RequestMapping("{userID}")
    public String index(Model model, @PathVariable int userID){
        model.addAttribute("user", userDao.findOne(userID));
        return "user/detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddUserForm(Model model){
        model.addAttribute("title", "Add User");
        model.addAttribute(new User());
        model.addAttribute("userTypes", UserType.values());
        return "user/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddUserForm(@ModelAttribute @Valid User newUser,
                                     Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("title", "Add User");
            model.addAttribute("userTypes", UserType.values());
            return "user/add";
        }
        userDao.save(newUser);
        loggedInUser = newUser;
        return "redirect:/user";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveUserForm(Model model) {
        model.addAttribute("users", userDao.findAll());
        model.addAttribute("title", "Remove User");
        return "user/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveUserForm(@RequestParam int [] userIds) {
        for (int userId : userIds) {
            userDao.delete(userId);
        }
        return "redirect:";
    }

    @RequestMapping(value="edit/{userId}", method=RequestMethod.GET)
    public String displayEditForm(Model model, @PathVariable int userId){
        User userToEdit = userDao.findOne(userId);
        model.addAttribute("user", userToEdit);
        model.addAttribute("userTypes", UserType.values());
        return "user/edit";
    }

    @RequestMapping(value="edit",method=RequestMethod.POST)
    public String processEditForm(@ModelAttribute @Valid User user,
                                  Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("userTypes", UserType.values());
            return "user/edit";
        }
        User userToEdit = userDao.findOne(user.getId());
        userToEdit.setUsername(user.getUsername());
        userToEdit.setEmail(user.getEmail());
        userToEdit.setType(user.getType());
        userToEdit.setPassword(user.getPassword());
        userToEdit.setVerifyPassword(user.getVerifyPassword());
        return "redirect:";
    }

}
