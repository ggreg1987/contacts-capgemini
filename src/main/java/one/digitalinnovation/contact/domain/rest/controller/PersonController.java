package one.digitalinnovation.contact.domain.rest.controller;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.contact.domain.entities.Person;
import one.digitalinnovation.contact.domain.rest.dto.personDTO.PersonDTO;
import one.digitalinnovation.contact.domain.rest.services.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
    @GetMapping
    public Page<PersonDTO> find(PersonDTO dto, Pageable pageable) {
        Person entity = modelMapper.map(dto, Person.class);
        Page<Person> personPage = service.find(entity, pageable);
        List<PersonDTO> dtoList = personPage.getContent()
                .stream()
                .map(person -> modelMapper.map(person, PersonDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList,pageable,personPage.getTotalElements());
    }
    @DeleteMapping("{cpf}")
    public void delete(@PathVariable String cpf) {
        Person person = service.findById(cpf)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        service.delete(person);
    }
    @PutMapping("{cpf}")
    public PersonDTO update(@PathVariable String cpf,@RequestBody PersonDTO dto) {
        return service.findById(cpf)
                .map(person -> {
                    person.setCpf(dto.getCpf());
                    service.update(person);
                    return modelMapper.map(person, PersonDTO.class);
                }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }
}
