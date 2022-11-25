package one.digitalinnovation.contact.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import one.digitalinnovation.contact.domain.entities.Person;
import one.digitalinnovation.contact.domain.entities.Phone;
import one.digitalinnovation.contact.domain.enums.PhoneType;
import one.digitalinnovation.contact.domain.rest.controller.PhoneController;
import one.digitalinnovation.contact.domain.rest.dto.phoneDTO.PhoneDTO;
import one.digitalinnovation.contact.domain.rest.services.PersonService;
import one.digitalinnovation.contact.domain.rest.services.PhoneService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WebMvcTest(controllers = PhoneController.class)
public class PhoneControllerTest {

    static String PHONE_API = "/api/phones";

    @MockBean
    PhoneService service;

    @MockBean
    PersonService personService;

    @Autowired
    MockMvc mockMvc;


    private PhoneDTO createPhoneDTO() {
        return PhoneDTO.builder()
                .id(1L)
                .type("HOME")
                .number("323212321")
                .build();
    }

    private Person createPerson() {
        return Person
                .builder()
                .cpf("73788507055")
                .birthDate(LocalDate.now())
                .email("gabriel@gmail")
                .name("gabriel gregorio")
                .build();
    }
        private Phone createPhone() {
            return Phone
                    .builder()
                    .id(1L)
                    .type(PhoneType.HOME)
                    .number("323212321")
                    .person(createPerson())
                    .build();
        }
    @Test
    @DisplayName("Should save a phone")
    public void createPhoneTest() throws Exception {
        Person person = createPerson();

        BDDMockito.given(personService.findById("73788507055")).willReturn(Optional.of(person));

        Phone phone = createPhone();
        BDDMockito.given(service.save(Mockito.any(Phone.class))).willReturn(phone);


        PhoneDTO phoneDTO = PhoneDTO
                .builder().number("323212321")
                .id(1L).type("HOME").build();
        String json = new ObjectMapper().writeValueAsString(phoneDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(PHONE_API)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(json);

        mockMvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("type").value(phoneDTO.getType()))
                .andExpect(jsonPath("number").value(phoneDTO.getNumber()));
    }

    @Test
    public void createInvalidPhoneTest() throws Exception {

        String json = new ObjectMapper().writeValueAsString(new PhoneDTO());
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(PHONE_API)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(json);

        mockMvc
                .perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should show Phone by id")
    public void findByIdTest() throws Exception {
        Long id = 1L;
        Phone phone = Phone
                .builder()
                .id(id)
                .type(createPhone().getType())
                .number(createPhone().getNumber())
                .person(createPerson())
                .build();

        BDDMockito.given(service.findById(id)).willReturn(Optional.of(phone));


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PHONE_API.concat("/" + id))
                .accept(APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("number").value(createPhoneDTO().getNumber()))
                .andExpect(jsonPath("type").value(createPhoneDTO().getType()));
    }

    @Test
    @DisplayName("Test find phone pageable")
    public void findPhoneTest() throws Exception {

        Long id = 1L;

        Phone phone = Phone
                .builder()
                .id(id)
                .type(createPhone().getType())
                .number(createPhone().getNumber())
                .person(createPerson())
                .build();

        BDDMockito.given(service.find(Mockito.any(Phone.class),
                Mockito.any(Pageable.class)))
                .willReturn(new PageImpl<Phone>(Arrays.asList(phone),
                        PageRequest.of(0,20),1));

        String query = String.format("?type=%s&number=%s&page=0&size=20",
                phone.getType().name(),
                phone.getNumber());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PHONE_API.concat(query))
                .accept(APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("totalElements").value(1))
                .andExpect(jsonPath("pageable.pageSize").value(20))
                .andExpect(jsonPath("pageable.pageNumber").value(0));

    }

    @Test
    @DisplayName("Should delete a phone")
    public void deletePhoneTest() throws Exception {

        Phone phone = Phone.builder().id(1L).build();

        BDDMockito.given(service.findById(Mockito.anyLong()))
                .willReturn(Optional.of(phone));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(PHONE_API.concat("/" + 1))
                .accept(APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("Cant delete a phone")
    public void cantDeletePhoneTest() throws Exception {

        BDDMockito.given(service.findById(Mockito.anyLong()))
                .willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(PHONE_API.concat("/" + 1))
                .accept(APPLICATION_JSON);

        mockMvc
                .perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should update the phone")
    public void updatePhoneTest() throws Exception {
        Long id = 1L;

        String json = new ObjectMapper().writeValueAsString(createPhoneDTO());

        Phone phone = createPhone();
        phone.setId(id);

        BDDMockito.given(service.findById(id)).willReturn(Optional.of(phone));

        Phone update = createPhone();
        update.setId(id);
        update.setNumber("1111111");
        update.setType(PhoneType.MOBILE);

        BDDMockito.given(service.update(phone)).willReturn(update);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(PHONE_API.concat("/" + id))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(json);

        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("number").value(createPhoneDTO().getNumber()))
                .andExpect(jsonPath("type").value(createPhoneDTO().getType()));
    }

    @Test
    @DisplayName("Cant update phone with wrong id")
    public void cantUpdatePhone() throws Exception {
        Long id = 2L;

        String json = new ObjectMapper().writeValueAsString(createPhoneDTO());

        BDDMockito.given(service.findById(id)).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(PHONE_API.concat("/" + id))
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(json);

        mockMvc
                .perform(request)
                .andExpect(status().isNotFound());
    }
}
