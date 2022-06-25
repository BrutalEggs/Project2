package org.example.Project2Boot.services;


import org.example.Project2Boot.models.Book;
import org.example.Project2Boot.models.Person;
import org.example.Project2Boot.repositories.BookRepository;
import org.example.Project2Boot.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * created by dmitrii.fateev
 */

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    public List<Book> findAll(boolean sortByYear){
        if (sortByYear)
            return bookRepository.findAll(Sort.by("year"));
        return bookRepository.findAll();
    }

    public Book findOne(int id){
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book){
        Book bookToUpdate = bookRepository.findById(id).get();
        book.setBook_id(id);
        book.setOwner(bookToUpdate.getOwner());
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);
    }

    public Person getOwner(int id){
        return bookRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void release(int id){
        Book book = bookRepository.getOne(id);
        book.setOwner(null);
        book.setTakenAt(null);
        bookRepository.save(book);
    }

    @Transactional
    public void assign(int id, Person person){
        Book book = bookRepository.getOne(id);
        book.setOwner(person);
        book.setTakenAt(new Date());
        bookRepository.save(book);
    }

    public List<Book> findByTitleStartWith(String title){
        return bookRepository.findByTitleStartingWith(title);
    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear){
        if (sortByYear)
            return bookRepository.findAll(PageRequest.of(page,booksPerPage, Sort.by("year"))).getContent();
        else return bookRepository.findAll(PageRequest.of(page,booksPerPage)).getContent();
    }

}
