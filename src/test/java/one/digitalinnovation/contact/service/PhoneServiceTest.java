package one.digitalinnovation.contact.service;

import one.digitalinnovation.contact.domain.entities.Person;
import one.digitalinnovation.contact.domain.entities.Phone;
import one.digitalinnovation.contact.domain.enums.PhoneType;
import one.digitalinnovation.contact.domain.repository.PhoneRepository;
import one.digitalinnovation.contact.domain.rest.services.PhoneService;
import one.digitalinnovation.contact.domain.rest.services.impl.PhoneServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PhoneServiceTest {

    PhoneService phoneService;

    @MockBean
    PhoneRepository phoneRepository;

    @BeforeEach
    public void setUp() {
        this.phoneService = new PhoneServiceImpl(phoneRepository);
    }

    public static Phone createPhone() {
        Person person = Person
                .builder()
                .cpf("68029446004")
                .build();

        return Phone
                .builder()
                .id(1L)
                .number("12345")
                .type(PhoneType.MOBILE)
                .person(person)
                .build();

    }
    @Test
    @DisplayName("Should save a phone")
    public void savePhone() {

        Phone phone = createPhone();
        Person person = createPhone().getPerson();
        Phone phoneReturned = Phone
                    .builder()
                    .id(1L)
                    .number("12345")
                    .type(PhoneType.MOBILE)
                    .person(person)
                    .build();

        when(phoneRepository.save(phone)).thenReturn(phoneReturned);

        Phone phoneTest = phoneService.save(phone);

        assertThat(phoneTest.getId()).isEqualTo(phoneReturned.getId());
        assertThat(phoneTest.getNumber()).isEqualTo(phoneReturned.getNumber());
        assertThat(phoneTest.getPerson().getCpf()).isEqualTo(phoneReturned.getPerson().getCpf());
        assertThat(phoneTest.getType().name()).isEqualTo(phoneReturned.getType().name());

    }

    @Test
    @DisplayName("Cant save a null phone")
    public void cantSavePhone() {
        Phone phone = createPhone();
        when(phoneRepository.save(Mockito.isNull())).thenReturn(null);
        Mockito.verify(phoneRepository,never()).save(phone);
    }

    @Test
    @DisplayName("Should find phone by if")
    public void findByIdTest() {
        Phone phone = createPhone();

        when(phoneRepository.findById(phone.getId())).thenReturn(Optional.of(phone));

        Optional<Phone> service = phoneService.findById(phone.getId());

        assertThat(service.isPresent()).isTrue();
        assertThat(service.get().getId()).isEqualTo(phone.getId());
        assertThat(service.get().getNumber()).isEqualTo(phone.getNumber());
        assertThat(service.get().getType()).isEqualTo(phone.getType());
        assertThat(service.get().getPerson()).isEqualTo(phone.getPerson());

        verify(phoneRepository, times(1)).findById(phone.getId());

    }
}
