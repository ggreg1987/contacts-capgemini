package one.digitalinnovation.contact.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import one.digitalinnovation.contact.domain.entities.Person;
import one.digitalinnovation.contact.domain.rest.controller.PersonController;
import one.digitalinnovation.contact.domain.rest.dto.personDTO.PersonDTO;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

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

    @Test
    @DisplayName("Should find person by cpf")
   public void findPersonByIdTest() throws Exception {
          Person person =  Person
                   .builder()
                   .cpf("73788507055")
                   .birthDate(LocalDate.now())
                   .email("gabriel@gmail.com")
                   .name("gabriel gregorio")
                   .build();
          BDDMockito.given(personService.findById(person.getCpf())).willReturn(Optional.of(person));

       MockHttpServletRequestBuilder request = MockMvcRequestBuilders
               .get(PERSON_API.concat("/" + person.getCpf()))
               .accept(APPLICATION_JSON)
               .contentType(APPLICATION_JSON);

       mockMvc
               .perform(request)
               .andExpect(status().isOk())
               .andExpect(jsonPath("cpf").isNotEmpty())
               .andExpect(jsonPath("cpf").value(createPersonDTO().getCpf()))
               .andExpect(jsonPath("name").value(createPersonDTO().getName()))
               .andExpect(jsonPath("birthDate").value(createPersonDTO().getBirthDate()))
               .andExpect(jsonPath("email").value(createPersonDTO().getEmail()));
   }

   @Test
   @DisplayName("Should show all persons")
   public void findPersonTest() throws Exception {
           Person person =  Person
                   .builder()
                   .cpf("73788507055")
                   .birthDate(LocalDate.now())
                   .email("gabriel@gmail.com")
                   .name("gabriel gregorio")
                   .build();

       BDDMockito.given(personService.find(Mockito.any(Person.class),
                       Mockito.any(Pageable.class)))
               .willReturn(new PageImpl<Person>(Arrays.asList(person),
                       PageRequest.of(0,20),1));

       String query = String.format("?cpf=%s&name=%s&page=0&size=20",
               person.getCpf(),
               person.getName());

       MockHttpServletRequestBuilder request = MockMvcRequestBuilders
               .get(PERSON_API.concat(query))
               .accept(APPLICATION_JSON);

       mockMvc
               .perform(request)
               .andExpect(status().isOk())
               .andExpect(jsonPath("totalElements").value(1))
               .andExpect(jsonPath("pageable.pageSize").value(20))
               .andExpect(jsonPath("pageable.pageNumber").value(0));
   }

   @Test
   @DisplayName("Should delete a person")
   public void deletePersonTest() throws Exception {

        Person person = Person.builder().cpf("73788507055").build();

       BDDMockito.given(personService.findById(Mockito.anyString()))
               .willReturn(Optional.of(person));

       MockHttpServletRequestBuilder request = MockMvcRequestBuilders
               .delete(PERSON_API.concat("/" + 1))
               .accept(APPLICATION_JSON);

       mockMvc
               .perform(request)
               .andExpect(status().isNoContent());
   }

   @Test
   @DisplayName("Cant delete a person wth wrong cpf")
   public void cantDeletePersonTest() throws Exception {

       BDDMockito.given(personService.findById(Mockito.anyString()))
               .willReturn(Optional.empty());

       MockHttpServletRequestBuilder request = MockMvcRequestBuilders
               .delete(PERSON_API.concat("/" + 1))
               .accept(APPLICATION_JSON);

       mockMvc
               .perform(request)
               .andExpect(status().isNotFound());
   }

   @Test
   @DisplayName("Should update a person")
   public void updatePersonTest() throws Exception {
       String cpf = "73788507055";

       String json = new ObjectMapper().writeValueAsString(createPersonDTO());

       Person person = createPerson();
       person.setCpf(cpf);

       BDDMockito.given(personService.findById(cpf)).willReturn(Optional.of(person));

       Person update = createPerson();
       update.setCpf(cpf);
       update.setName("Iron Man");
       update.setEmail("ironman@gmail.com");

       BDDMockito.given(personService.update(person)).willReturn(update);

       MockHttpServletRequestBuilder request = MockMvcRequestBuilders
               .put(PERSON_API.concat("/" + cpf))
               .accept(APPLICATION_JSON)
               .contentType(APPLICATION_JSON)
               .content(json);

       mockMvc
               .perform(request)
               .andExpect(status().isOk())
               .andExpect(jsonPath("cpf").value(createPersonDTO().getCpf()))
               .andExpect(jsonPath("name").value(createPersonDTO().getName()))
               .andExpect(jsonPath("email").value(createPersonDTO().getEmail()));

   }
}
