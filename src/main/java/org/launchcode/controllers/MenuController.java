package org.launchcode.controllers;

import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private CheeseDao cheeseDao;

    @RequestMapping("")
    public String index(Model model){
        model.addAttribute("title","Menus");
        model.addAttribute("menus", menuDao.findAll());
        return "menu/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("title", "Add a New Menu");
        model.addAttribute(new Menu());
        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Menu menu, Errors errors){
        if(errors.hasErrors()){
            model.addAttribute("title", "Add Menu");
            return "menu/add";
        }
        menuDao.save(menu);
        return "redirect:view/" + menu.getId();
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveMenuForm(Model model) {
        model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("title", "Remove Menu");
        return "menu/remove-menu";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveMenuForm(@RequestParam int [] menuIds) {
        for (int menuId : menuIds) {
            menuDao.delete(menuId);
        }
        return "redirect:";
    }

    @RequestMapping(value = "view/{menuId}", method = RequestMethod.GET)
    public String viewMenu(Model model, @PathVariable int menuId){
        Menu menu = menuDao.findOne(menuId);
        model.addAttribute("title", menu.getName());
        model.addAttribute("cheeses", menu.getCheeses());
        model.addAttribute(menu);
        return "menu/view";
    }

    @RequestMapping(value = "add-item/{menuId}", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int menuId){
        // Added this code to prevent duplicate cheeses on a single menu
        List<Cheese> availableCheese = new ArrayList<>();
        List<Cheese> cheeseOnMenu = menuDao.findOne(menuId).getCheeses();
        for (Cheese cheese : cheeseDao.findAll()){
            if (!cheeseOnMenu.contains(cheese)){
                availableCheese.add(cheese);
            }
        }

        AddMenuItemForm form = new AddMenuItemForm(menuDao.findOne(menuId), availableCheese);
//        AddMenuItemForm form = new AddMenuItemForm(menuDao.findOne(menuId), cheeseDao.findAll());
        model.addAttribute("title", "Add item to menu: " + form.getMenu().getName().toUpperCase());
        model.addAttribute("form", form);
        return "menu/add-item";
    }

    @RequestMapping(value = "add-item", method = RequestMethod.POST)
    public String addItem(@ModelAttribute @Valid AddMenuItemForm form, Errors errors){
        if(errors.hasErrors()){
            return "menu/add-item";
        }
        Menu theMenu = menuDao.findOne(form.getMenuId());
        Cheese theCheese = cheeseDao.findOne(form.getCheeseId());
        theMenu.addItem(theCheese);
        menuDao.save(theMenu);
        return "redirect:/menu/view/" + theMenu.getId();
    }

    @RequestMapping(value = "remove-item/{menuId}", method = RequestMethod.GET)
    public String displayRemoveItemForm(Model model, @PathVariable int menuId) {
        model.addAttribute("title", "Remove Item(s) From: ");
        model.addAttribute("menu", menuDao.findOne(menuId));
        return "menu/remove-item";
    }

    @RequestMapping(value = "remove-item", method = RequestMethod.POST)
    public String processRemoveItemForm(Model model, @RequestParam int [] cheeseIds, int menuId) {
        Menu menu = menuDao.findOne(menuId);
        for (int cheeseId : cheeseIds) {
            Cheese cheeseToRemove = cheeseDao.findOne(cheeseId);
            menu.removeItem(cheeseToRemove);
            menuDao.save(menu);
        }
        model.addAttribute("title", menu.getName());
        model.addAttribute("cheeses", menu.getCheeses());
        return  "redirect:/menu/view/" + menuId;
    }
}
