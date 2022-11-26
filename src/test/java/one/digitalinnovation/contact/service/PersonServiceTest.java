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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

        assertThrows(ResponseStatusException.class,
                () -> personService.save(person));

        verify(personRepository,never()).save(person);
    }

    @Test
    @DisplayName("Should show a Person by cpf")
    public void findByIdTest() {
        String cpf = "06422689403";
        Person person = Person
                .builder().cpf(cpf).build();

        when(personRepository.existsByCpf(cpf)).thenReturn(true);
        when(personService.findById(cpf)).thenReturn(Optional.ofNullable(person));

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

        assertThrows(ResponseStatusException.class,
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

    @Test
    @DisplayName("Should delete a Person")
    public void deletePersonTest() {
        Person person = new Person();
        person.setCpf("68029446004");

        when(personRepository.existsByCpf(Mockito.anyString())).thenReturn(true);
        assertDoesNotThrow(() -> personService.delete(person));

        verify(personRepository,times(1)).delete(person);
    }

    @Test
    @DisplayName("Cant delete a person with invalid cpf")
    public void cantDeletePersonTest() {
        Person person = new Person();
        person.setCpf("68029446004");

        when(personRepository.existsByCpf(Mockito.anyString())).thenReturn(false);
        assertThrows(IllegalArgumentException.class,() -> personService.delete(person));

        verify(personRepository,never()).delete(person);
    }

    @Test
    @DisplayName("Should update a Person")
    public void updatePersonTest() {
        String cpf = "12263694056";

        Person oldPerson = Person.builder().cpf(cpf).build();
        Person update = createPerson();

        update.setCpf(cpf);
        when(personRepository.existsByCpf(cpf)).thenReturn(true);
        when(personRepository.save(oldPerson)).thenReturn(update);
        Person person = personService.update(oldPerson);

        assertThat(person.getCpf()).isEqualTo(update.getCpf());
    }

    @Test
    @DisplayName("Cant update a person with wrong cpf")
    public void cantUpdatePersonTest() {
        Person person = new Person();
        person.setCpf("68029446004");

        when(personRepository.existsByCpf(person.getCpf())).thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> personService.update(person));
        verify(personRepository,never()).save(person);
    }
}
