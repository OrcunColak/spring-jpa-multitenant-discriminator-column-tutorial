package com.colak.springtutorial.controller;

import com.colak.springtutorial.jpa.Person;
import com.colak.springtutorial.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("persons")

@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public List<Person> getPersons() {
        return personService.getAllPersons();
    }

}
