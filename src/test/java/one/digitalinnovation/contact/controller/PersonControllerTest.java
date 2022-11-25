package one.digitalinnovation.contact.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import one.digitalinnovation.contact.domain.entities.Person;
import one.digitalinnovation.contact.domain.entities.Phone;
import one.digitalinnovation.contact.domain.rest.controller.PersonController;
import one.digitalinnovation.contact.domain.rest.dto.personDTO.PersonDTO;
import one.digitalinnovation.contact.domain.rest.dto.phoneDTO.PhoneDTO;
import one.digitalinnovation.contact.domain.rest.services.PersonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    static String PERSON_API = "/api/persons";


    @MockBean
    PersonService personService;

    @Autowired
    MockMvc mockMvc;

    private Person createPerson() {

        return Person
                .builder()
                .cpf("73788507055")
                .birthDate(LocalDate.now())
                .email("gabriel@gmail.com")
                .name("gabriel gregorio")
                .build();
    }

    private PersonDTO createPersonDTO() {
        return PersonDTO
                .builder()
                .cpf("73788507055")
                .birthDate("25-11-2022")
                .email("gabriel@gmail.com")
                .name("gabriel gregorio")
                .build();
    }

    @Test
    @DisplayName("Should save a person")
    public void createPersonTest() throws Exception {
        Person person = createPerson();
        BDDMockito.given(personService.save(Mockito.any(Person.class))).willReturn(person);

        PersonDTO dto = PersonDTO
                .builder().cpf("73788507055")
                .name("gabriel gregorio")
                .email("gabriel@gmail.com")
                .birthDate("25-11-2022").build();
        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(PERSON_API)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(json);

        mockMvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("cpf").isNotEmpty())
                .andExpect(jsonPath("name").value(dto.getName()))
                .andExpect(jsonPath("birthDate").value(dto.getBirthDate()))
                .andExpect(jsonPath("email").value(dto.getEmail()));
    }
}
