package com.vst.demo.controllers;

import com.vst.demo.enitity.Person;
import com.vst.demo.exceptions.PersonNotFoundException;
import com.vst.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")

public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable int id) {
        return personService.getPersonById(id);
    }


    @PostMapping("/save")
    public ResponseEntity<?> createUser(@RequestBody Person person) {
        try {
            Person savedUser = personService.save(person);
            return ResponseEntity.ok(savedUser);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }


    @GetMapping("/find-all")
    public List<Person> findAll() {
        return personService.findAll();
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        personService.deleteById(id);
    }

    @PutMapping("/{personID}/assign-dep/{departmentID}")
    public ResponseEntity<?> assignDepartmentToPerson(
            @PathVariable(name = "personID") int personID,
            @PathVariable(name = "departmentID") int departmentID) {
        try {
            var updatePerson = personService.assignDepartmentToPerson(personID, departmentID);
            return ResponseEntity.ok(updatePerson);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updatePerson(@PathVariable int id,
                                          @RequestBody Person person) {
        try {
            var updatePerson = personService.updatePerson(id, person);
            return ResponseEntity.ok(updatePerson);
        } catch (PersonNotFoundException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
