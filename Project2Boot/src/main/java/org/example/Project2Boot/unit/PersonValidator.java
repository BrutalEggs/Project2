package org.example.Project2Boot.unit;

import org.example.Project2Boot.models.Person;
import org.example.Project2Boot.services.PersonService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * created by dmitrii.fateev
 */
@Component
public class PersonValidator implements Validator {

    private final PersonService personService;

    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (personService.findByName(person.getName()) != null) {
            errors.rejectValue("name", "", "This name is already taken");
        }

    }

}
