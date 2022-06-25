package org.example.Project2Boot.services;


import org.example.Project2Boot.models.Book;
import org.example.Project2Boot.models.Person;
import org.example.Project2Boot.repositories.BookRepository;
import org.example.Project2Boot.repositories.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * created by dmitrii.fateev
 */

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;
    private final BookRepository bookRepository;


    public PersonService(PersonRepository personRepository, BookRepository bookRepositoryl) {
        this.personRepository = personRepository;
        this.bookRepository = bookRepositoryl;
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    public Person findOne(int id){
        return personRepository.findById(id).orElse(null);
    }

    public Person findByName(String name){
        return personRepository.findByName(name);
    }

    @Transactional
    public void save(Person person){
        personRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person){
        person.setUser_id(id);
        personRepository.save(person);
    }

    @Transactional
    public void delete(int id){
        personRepository.deleteById(id);
    }

    public List<Book> showBooks(int id){
        Person person = personRepository.findById(id).orElse(null);
        if (person != null){
            Hibernate.initialize(person.getBooks());
            person.getBooks().forEach(book -> {
                if (book.getTakenAt() != null) {
                    long diffTime = Math.abs(book.getTakenAt().getTime() - new Date().getTime());
                    if (diffTime > 864000000)
                        book.setTimeOut(true);
                }
            });
            return person.getBooks();
        }
        else return Collections.emptyList();
    }
}
