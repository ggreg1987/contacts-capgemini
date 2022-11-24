package one.digitalinnovation.contact.domain.rest.controller;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.contact.domain.entities.Person;
import one.digitalinnovation.contact.domain.rest.dto.personDTO.PersonDTO;
import one.digitalinnovation.contact.domain.rest.services.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService service;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(CREATED)
    public PersonDTO create(@RequestBody PersonDTO dto) {
        Person person = modelMapper.map(dto, Person.class);
        dto.setBirthDay(person.getBirthDate()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        Person save = service.save(person);
        return modelMapper.map(save,PersonDTO.class);
    }

    @GetMapping("{cpf}")
    @ResponseStatus(OK)
    public PersonDTO findById(@PathVariable String cpf) {
        return service.findById(cpf)
                .map(person -> modelMapper.map(person, PersonDTO.class))
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }
}
