package com.vst.demo.service;

import com.vst.demo.enitity.Person;
import com.vst.demo.exceptions.DepartmentNotFoundException;
import com.vst.demo.exceptions.PersonNotFoundException;
import com.vst.demo.repository.DepartmentRepository;
import com.vst.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, DepartmentRepository departmentRepository) {
        this.personRepository = personRepository;
        this.departmentRepository = departmentRepository;
    }

    public Person getPersonById(int id) {
        return personRepository.findById(id).orElseThrow(
                () -> new PersonNotFoundException("Пользователь с данным id не найден"));
    }

    public Person save(Person person) {


        var existingPerson = personRepository.findByEmail(person.getEmail());
        if (existingPerson.isPresent()) {
            throw new PersonNotFoundException("Пользователь уже существует");
        }
        return personRepository.save(person);
    }

    public void deleteById(int id) {
        var existing = personRepository.findById(id);
        if (existing.isPresent())
            personRepository.deleteById(id);
        else throw new PersonNotFoundException("Невозможно удалить пользователя");
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    public Person assignDepartmentToPerson(int personID, int departmentID){

        var person = personRepository.findById(personID).orElseThrow(()->
                new PersonNotFoundException("Пользователь с таким ID не существует"));

        var department = departmentRepository.findById(departmentID).orElseThrow(()->
                new DepartmentNotFoundException("Данного отдела не существует"));

        person.setDepartment(department);

        return personRepository.save(person);
    }
}
