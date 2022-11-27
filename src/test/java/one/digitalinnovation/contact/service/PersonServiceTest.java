package one.digitalinnovation.contact.service;

import one.digitalinnovation.contact.domain.entities.Person;
import one.digitalinnovation.contact.domain.repository.PersonRepository;
import one.digitalinnovation.contact.domain.rest.services.PersonService;
import one.digitalinnovation.contact.domain.rest.services.impl.PersonServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class PersonServiceTest {

    PersonService service;

    @MockBean
    PersonRepository repository;

    @BeforeEach
    public void setUp() {
        service = new PersonServiceImpl(repository);
    }

    Person createPerson = new PhoneServiceTest().createNewPerson();

    @Test
    @DisplayName("Should save a Person")
    public void createPersonTest() {
        Person person = createPerson;

        when(repository.save(person)).thenReturn(Person.builder()
                .cpf("35617385089")
                .email("g@gmail.com")
                .birthDate(LocalDate.now())
                .name("gabriel")
                .build());
        Person save = service.save(person);
        assertThat(save.getCpf()).isEqualTo(person.getCpf());
    }

    @Test
    @DisplayName("Should find a person by the cpf")
    public void findPersonByCpfTest() {

        String cpf = createPerson.getCpf();

        when(repository.existsByCpf(cpf)).thenReturn(true);
        when(repository.findByCpf(cpf)).thenReturn(Optional.of(createPerson));

        Person person = service.findById(cpf);

        assertThat(person.getCpf()).isEqualTo(createPerson.getCpf());
    }

    @Test
    @DisplayName("Cant find person by wrong cpf")
    public void cantFindPersonByCpf() {

        when(repository.existsByCpf(Mockito.anyString())).thenReturn(false);

        assertThrows(ResponseStatusException.class,
                () -> service.findById(Mockito.anyString()));

    }

    @Test
    @DisplayName("Delete a person by cpf")
    public void deletePersonByCpf() {
        String cpf = createPerson.getCpf();

        when(repository.existsByCpf(cpf)).thenReturn(true);
        when(repository.findByCpf(cpf)).thenReturn(Optional.of(createPerson));
        assertDoesNotThrow(() -> service.delete(cpf));

        verify(repository, times(1)).delete(createPerson);
    }

    @Test
    @DisplayName("Cant delete a person with wrong cpf")
    public void cantDeletePersonByCpfTest() {

        String cpf = createPerson.getCpf();

        when(repository.existsByCpf(cpf)).thenReturn(false);
        assertThrows(ResponseStatusException.class,
                () -> service.delete(cpf));

        verify(repository,never()).delete(createPerson);
    }

    @Test
    @DisplayName("Should update a person ")
    public void updatePersonTest() {

        Person person = Person.builder().cpf("49988490003").build();
        Person update = createPerson;
        update.setCpf(person.getCpf());

        when(repository.existsByCpf(person.getCpf())).thenReturn(true);
        when(repository.findByCpf(person.getCpf())).thenReturn(Optional.of(person));
        when(repository.save(person)).thenReturn(update);

        Person personSave = service.update(person.getCpf(), person);

        assertThat(personSave.getCpf()).isEqualTo(update.getCpf());

        verify(repository,times(1)).save(person);

    }

    @Test
    @DisplayName("Cant update a person with wrong cpf")
    public void cantUpdatePersonTest() {

        when(repository.existsByCpf(Mockito.anyString())).thenReturn(false);
        assertThrows(ResponseStatusException.class,
                () -> service.update(createPerson.getCpf(),createPerson));

        verify(repository,never()).save(createPerson);
    }
}
