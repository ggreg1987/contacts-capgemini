package one.digitalinnovation.contact.service;

import one.digitalinnovation.contact.domain.entities.Person;
import one.digitalinnovation.contact.domain.entities.Phone;
import one.digitalinnovation.contact.domain.enums.PhoneType;
import one.digitalinnovation.contact.domain.repository.PhoneRepository;
import one.digitalinnovation.contact.domain.rest.services.PhoneService;
import one.digitalinnovation.contact.domain.rest.services.impl.PhoneServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Assertions.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class PhoneServiceTest {

    PhoneService service;

    @MockBean
    PhoneRepository repository;

    @BeforeEach
    public void setUp() {
        service = new PhoneServiceImpl(repository);
    }

    private Person createNewPerson() {
        return Person
                .builder()
                .cpf("35617385089")
                .email("g@gmail.com")
                .birthDate(LocalDate.now())
                .name("gabriel")
                .build();
    }

    private Phone createNewPhone() {
        return Phone
                .builder()
                .id(1L)
                .type(PhoneType.HOME)
                .number("12345")
                .person(createNewPerson())
                .build();
    }

    @Test
    @DisplayName("Should create a Phone.")
    public void createPhoneTest() {
        Long id = 1L;
        Phone phone = createNewPhone();

        when(repository.save(phone)).thenReturn(
                    Phone
                            .builder()
                            .id(1L)
                            .type(PhoneType.HOME)
                            .number("12345")
                            .person(createNewPerson())
                            .build()
        );

        Phone saved = service.save(phone);

        assertThat(saved.getId()).isEqualTo(phone.getId());
        assertThat(saved.getType().name()).isEqualTo(phone.getType().name());
        assertThat(saved.getNumber()).isEqualTo(phone.getNumber());
        assertThat(saved.getPerson().getCpf()).isEqualTo(phone.getPerson().getCpf());
    }

    @Test
    @DisplayName("Should find phone by id")
    public void findByIdTest() {
        Long id = 1L;
        Phone phone = createNewPhone();
        phone.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(phone));

        Phone serviceById = service.findById(id);

        assertThat(serviceById.getNumber()).isEqualTo(phone.getNumber());
        assertThat(serviceById.getType().name()).isEqualTo(phone.getType().name());
    }

    @Test
    @DisplayName("Cant find phone with wrong id")
    public void cantFindByIdTest() {
        Long id = 2L;

        when(repository.existsById(id)).thenReturn(false);

        assertThrows(ResponseStatusException.class,
                () -> service.delete(id));

    }

    @Test
    @DisplayName("Should delete the phone by id")
    public void deletePhoneTest() {

        Phone phone = Phone
                .builder().id(1L).build();

        when(repository.existsById(phone.getId())).thenReturn(true);
        when(repository.findById(phone.getId())).thenReturn(Optional.of(phone));
        assertDoesNotThrow(() -> service.delete(phone.getId()));

        verify(repository,times(1)).delete(phone);

    }

}
