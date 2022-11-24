package one.digitalinnovation.contact.service;

import one.digitalinnovation.contact.domain.entities.Person;
import one.digitalinnovation.contact.domain.repository.PersonRepository;
import one.digitalinnovation.contact.domain.rest.services.PersonService;
import one.digitalinnovation.contact.domain.rest.services.impl.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PersonServiceTest {

    PersonService personService;

    @MockBean
    PersonRepository personRepository;

    @BeforeEach
    public void setUp() {
        this.personService = new PersonServiceImpl(personRepository);
    }

    private Person createPerson() {
        return Person
                .builder()
                .cpf("68029446004")
                .name("Gabriel Gregorio")
                .email("gabriel@gmail.com")
                .build();
    }

    @Test
    @DisplayName("Should save a Person")
    public void savePersonTest() {
        Person person = createPerson();

        when(personRepository.save(Mockito.any())).thenReturn(person);

        Person personTest = personService.save(person);

        assertThat(personTest.getCpf()).isEqualTo(person.getCpf());
    }

}
