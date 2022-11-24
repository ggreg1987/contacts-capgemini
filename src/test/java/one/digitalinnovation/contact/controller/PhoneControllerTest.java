package one.digitalinnovation.contact.controller;

import one.digitalinnovation.contact.domain.rest.controller.PersonController;
import one.digitalinnovation.contact.domain.rest.services.PhoneService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WebMvcTest(controllers = PersonController.class)
public class PhoneControllerTest {

    static String PHONE_API = "/api/phones";

    @MockBean
    PhoneService service;

    @Autowired
    MockMvc mockMvc;

}
