package one.digitalinnovation.contact.service;

import one.digitalinnovation.contact.domain.repository.PhoneRepository;
import one.digitalinnovation.contact.domain.rest.services.PhoneService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PhoneServiceTest {

    PhoneService phoneService;

    @MockBean
    PhoneRepository phoneRepository;
}
