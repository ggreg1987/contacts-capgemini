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
import org.springframework.data.domain.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

    @Test
    @DisplayName("Cant save duplicated cpf")
    public void cantSavePersonTest() {
        Person person = new Person();
        person.setCpf("68029446004");

        when(personRepository.existsByCpf("68029446004")).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> personService.save(person));

        verify(personRepository,never()).save(person);
    }

    @Test
    @DisplayName("Should show a Person by cpf")
    public void findByIdTest() {
        Person person = createPerson();

        when(personService.findById(Mockito.anyString())).thenReturn(Optional.of(person));

        Optional<Person> personTest = personService.findById(person.getCpf());
        assertThat(personTest.isPresent()).isTrue();
        assertThat(personTest.get().getCpf()).isEqualTo(person.getCpf());
    }

    @Test
    @DisplayName("Cant find wrong cpf")
    public void cantFindByIdTest() {
        Person person = new Person();
        person.setCpf("68029446004");

        when(personRepository.existsByCpf(Mockito.anyString())).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> personService.findById(person.getCpf()));

    }

    @Test
    @DisplayName("Should show all Persons")
    public void findPersonTest() {
        Person person = createPerson();
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Person> personList = Arrays.asList(person);
        PageImpl<Person> page = new PageImpl<>(personList, pageRequest, 10);

        when(personRepository.findAll(any(Example.class), any(Pageable.class))).thenReturn(page);

        Page<Person> personPage = personService.find(person, pageRequest);

        assertThat(personPage.getTotalElements()).isEqualTo(10);
        assertThat(personPage.getContent()).isEqualTo(personList);
        assertThat(personPage.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(personPage.getPageable().getPageSize()).isEqualTo(10);
    }
}
