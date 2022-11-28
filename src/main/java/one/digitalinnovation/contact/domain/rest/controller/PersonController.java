package one.digitalinnovation.contact.domain.rest.controller;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.contact.domain.entities.Person;
import one.digitalinnovation.contact.domain.rest.dto.personDTO.PersonDTO;
import one.digitalinnovation.contact.domain.rest.dto.phoneDTO.PhoneDTO;
import one.digitalinnovation.contact.domain.rest.services.PersonService;
import one.digitalinnovation.contact.domain.rest.services.PhoneService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService service;
    private final ModelMapper modelMapper;

    private final PhoneService phoneService;

    @PostMapping
    @ResponseStatus(CREATED)
    public PersonDTO create(@RequestBody Person person) {
//        Person map = modelMapper.map(dto, Person.class);
//        dto.setBirthDate(map.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
//        Person person = service.save(map);
//        return modelMapper.map(person, PersonDTO.class);

        return PersonDTO
                .builder()
                .name(person.getName())
                .email(person.getEmail())
                .cpf(person.getCpf())
                .birthDate(person.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .phones(person
                        .getPhones()
                        .stream().map(p -> {
                            phoneService.save(p);
                        return  PhoneDTO
                                .builder()
                                .id(p.getId())
                                .number(p.getNumber())
                                .type(p.getType())
                                .build();
                        }).collect(Collectors.toList()))
                .build();
    }

    @GetMapping("{cpf}")
    @ResponseStatus(OK)
    public PersonDTO findById(@PathVariable String cpf) {
        Person person = service.findById(cpf);
        return modelMapper.map(person,PersonDTO.class);
    }
    @GetMapping
    public List<PersonDTO> find(PersonDTO dto) {
        Person person = modelMapper.map(dto, Person.class);
        List<Person> personList = service.find(person);
        return personList
                .stream()
                .map(list -> modelMapper.map(list, PersonDTO.class))
                .collect(Collectors.toList());
    }
    @DeleteMapping("{cpf}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable String cpf) {
       service.delete(cpf);
    }
    @PutMapping("{cpf}")
    @ResponseStatus(OK)
    public PersonDTO update(@PathVariable String cpf,@RequestBody @Valid PersonDTO dto) {
        Person person = modelMapper.map(dto, Person.class);
        Person update = service.update(cpf, person);
        return modelMapper.map(update,PersonDTO.class);
    }
}
