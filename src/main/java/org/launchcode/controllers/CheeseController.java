package org.launchcode.controllers;

import org.launchcode.models.Cheese;
import org.launchcode.models.CheeseData;
import org.launchcode.models.CheeseType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Don't forget the JavaDocs
 */
@Controller
@RequestMapping("cheese")
public class CheeseController {

    static boolean shown = false;

    // Request path: /cheese
    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("cheeses", CheeseData.getAll());
        model.addAttribute("title", "Cheese Listing");
        if(!shown){
            CheeseData.add(new Cheese("Cheddar", "Nice sharp bite!", CheeseType.HARD, 5));
            CheeseData.add(new Cheese("City Cow Mozzarella", "Only store in town ... so we like it", CheeseType.SOFT, 3));
            CheeseData.add(new Cheese("Velveeta", "Not really cheese", CheeseType.FAKE, 2));
            CheeseData.add(new Cheese("Kitchen Cheese", "The price is right", CheeseType.SOFT, 2));
            shown = true;
        }
        return "cheese/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCheeseForm(Model model){
        model.addAttribute("title", "Add Cheese");
        model.addAttribute("cheeseTypes", CheeseType.values());
        model.addAttribute(new Cheese()); // same as ("cheese", new Cheese);
        return "cheese/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCheeseForm(@ModelAttribute @Valid Cheese newCheese,
                                       Errors errors, Model model) {
        if (errors.hasErrors()){
            model.addAttribute("title", "Add Cheese");
            model.addAttribute("cheeseTypes", CheeseType.values());
            return "cheese/add";
        }
        CheeseData.add(newCheese);
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCheeseForm(Model model) {
        model.addAttribute("cheeses", CheeseData.getAll());
        model.addAttribute("title", "Remove Cheese");
        return "cheese/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCheeseForm(@RequestParam int [] cheeseIds) {
        for (int cheeseId : cheeseIds) {
            CheeseData.remove(cheeseId);
        }
        return "redirect:";
    }

    @RequestMapping(value="edit/{cheeseId}", method=RequestMethod.GET)
    public String displayEditForm(Model model, @PathVariable int cheeseId){
        Cheese cheeseToEdit = CheeseData.getById(cheeseId);
        model.addAttribute("cheese", cheeseToEdit);
        model.addAttribute("cheeseTypes", CheeseType.values());
        return "cheese/edit";
    }

    @RequestMapping(value="edit",method=RequestMethod.POST)
    public String processEditForm(@ModelAttribute @Valid Cheese cheese,
                                  Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("cheeseTypes", CheeseType.values());
            return "cheese/edit";
        }
        Cheese cheeseToEdit = CheeseData.getById(cheese.getCheeseId());
        cheeseToEdit.setName(cheese.getName());
        cheeseToEdit.setDescription(cheese.getDescription());
        cheeseToEdit.setType(cheese.getType());
        cheeseToEdit.setRating(cheese.getRating());
        return "redirect:";
    }

}
