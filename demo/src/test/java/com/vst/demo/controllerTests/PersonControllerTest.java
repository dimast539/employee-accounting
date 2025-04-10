package com.vst.demo.controllerTests;

import com.vst.demo.controllers.PersonController;
import com.vst.demo.enitity.Department;
import com.vst.demo.enitity.Person;
import com.vst.demo.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    @InjectMocks
    private PersonController personController;      //тестируемый контроллер
    @Mock
    private PersonService personService;


    private MockMvc mockMvc;        //http

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @Test
    void testPersonById() throws Exception{
        Person person = new Person(
                1,"Кузьма", "Елисеев", "kuzma-eliseev@exmple.com", null);
        Mockito.when(personService.getPersonById(1)).thenReturn(person);


        mockMvc.perform(get("/person/1")).
                andDo(print())
                .andExpect(status().isOk())      // ожидаем 200
                .andExpect(jsonPath("$.name").value("Кузьма"))
                .andExpect(jsonPath("$.surname").value("Елисеев"));
    }

    @Test
    void testAssignDepartmentToPerson() throws Exception{

        Person person = new Person(
                1,"Кузьма", "Елисеев", "kuzma-eliseev@exmple.com", null);
        Department department = new Department(1, "IT");
        Mockito.when(personService.assignDepartmentToPerson(1,1 ))
                .thenReturn(person);

        mockMvc.perform(put("/person/1/assign-dep/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Кузьма"));
    }

    @Test
    public void testDeleteById() throws Exception{
        int personId = 1;

        Mockito.doNothing().when(personService).deleteById(personId);

        mockMvc.perform(delete("/person/{id}", personId))
                .andDo(print())
                .andExpect(status().isOk());

        Mockito.verify(personService, Mockito.times(1)).deleteById(personId);

    }
}
