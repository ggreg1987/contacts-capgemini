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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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
        when(repository.findByCpf(cpf)).thenReturn(Optional.ofNullable(createPerson));

        Person person = service.findById(cpf);

        assertThat(person.getCpf()).isEqualTo(createPerson.getCpf());

    }

}
