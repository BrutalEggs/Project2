package org.example.Project2Boot.repositories;

import org.example.Project2Boot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * created by dmitrii.fateev
 */

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person findByName(String name);
}
