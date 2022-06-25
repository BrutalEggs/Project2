package org.example.Project2Boot.controllers;


import org.example.Project2Boot.models.Book;
import org.example.Project2Boot.models.Person;
import org.example.Project2Boot.services.BookService;
import org.example.Project2Boot.services.PersonService;
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
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PersonService personService;



    @Autowired
    public BookController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;

    }

    @GetMapping
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean sortByYear){
        if (page == null || booksPerPage == null)
            model.addAttribute("books", bookService.findAll(sortByYear));
        else
            model.addAttribute("books", bookService.findWithPagination(page,booksPerPage,sortByYear));
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "people/new";
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", bookService.findOne(id));
      //  Person owner = bookDAO.getOwner(id);
        if (bookService.getOwner(id) != null)
            model.addAttribute("owner", bookService.getOwner(id));
        else
            model.addAttribute("people", personService.findAll());
        return "books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id, @ModelAttribute("person") @Valid Book book,
                         BindingResult bindingResult){

        if (bindingResult.hasErrors())
            return "people/edit";
        bookService.update(id,book);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookService.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookService.assign(id, person);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String search(){
        return "books/search";
    }

    @PostMapping("/search")
    public String search(@RequestParam("fragment") String s, Model model){
        System.out.println(s);
        model.addAttribute("books",bookService.findByTitleStartWith(s));
        return "books/search";
    }

}
