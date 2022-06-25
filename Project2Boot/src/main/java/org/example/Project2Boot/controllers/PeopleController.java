package org.example.Project2Boot.controllers;


import org.example.Project2Boot.models.Person;
import org.example.Project2Boot.services.PersonService;
import org.example.Project2Boot.unit.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * created by dmitrii.fateev
 */
@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonService personService;

    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonService personService, PersonValidator personValidator) {
        this.personService = personService;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String indexPeople(Model model){
        model.addAttribute("people", personService.findAll());
        return "people/index";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("person") Person person){
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person,bindingResult);

        if (bindingResult.hasErrors())
            return "people/new";

        personService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personService.findOne(id));
        model.addAttribute("books", personService.showBooks(id));
        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){
      //  personValidator.validate(person,bindingResult);

        if (bindingResult.hasErrors())
            return "people/edit";
        personService.update(id,person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        personService.delete(id);
        return "redirect:/people";
    }

}
