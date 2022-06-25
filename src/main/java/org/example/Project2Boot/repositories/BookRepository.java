package org.example.Project2Boot.repositories;

import org.example.Project2Boot.models.Book;
import org.example.Project2Boot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * created by dmitrii.fateev
 */

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByOwner(Person person);

    List<Book> findByTitleStartingWith(String title);
}
