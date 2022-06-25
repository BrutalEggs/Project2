package org.example.Project2Boot.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * created by dmitrii.fateev
 */

@Entity
@Table(name = "Person")
public class Person {


    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    @Column(name = "name")
    @NotEmpty(message = "Name should be not empty")
    @Size(min = 2, max = 100, message = "Name should be 2 - 100 chars")
    private String name;

    @Column(name = "year")
    @Min(value = 1900, message = "Year of birth should be over 1900")
    private int year;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Person() {
    }

    public Person(int user_id, String name, int year) {
        this.name = name;
        this.year = year;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
