package one.digitalinnovation.contact.controller;
import one.digitalinnovation.contact.domain.entities.Person;
import one.digitalinnovation.contact.domain.rest.controller.PersonController;
import one.digitalinnovation.contact.domain.rest.dto.personDTO.PersonDTO;
import one.digitalinnovation.contact.domain.rest.services.PersonService;
import one.digitalinnovation.contact.domain.rest.services.PhoneService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    static String PHONE_API = "/api/phones";


    @MockBean
    PersonService personService;

    @Autowired
    MockMvc mockMvc;

    private Person createPerson() {

        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedString = localDate.format(formatter);

        return Person
                .builder()
                .cpf("73788507055")
                .birthDate(LocalDate.parse(formattedString.concat("25/11/2022")))
                .email("gabriel@gmail")
                .name("gabriel gregorio")
                .build();
    }

    private PersonDTO createPersonDTO() {
        return PersonDTO
                .builder()
                .cpf("73788507055")
                .birthDay("25/11/2022")
                .email("gabriel@gmail")
                .name("gabriel gregorio")
                .build();
    }


}
