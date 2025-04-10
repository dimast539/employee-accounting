package com.vst.demo.controllerTests;

import com.vst.demo.controllers.PersonController;
import com.vst.demo.enitity.Person;
import com.vst.demo.service.PersonService;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                2,"Олег", "Тиньков", "oleg-tinkov@exmple.com", null);
        Mockito.when(personService.getPersonById(2)).thenReturn(person);

        mockMvc.perform(get("/person/2")).
                andDo(print())
                .andExpect(status().isOk())      // ожидаем 200
                .andExpect(jsonPath("$.name").value("Олег"))
                .andExpect(jsonPath("$.surname").value("Тиньков"));
    }
}
