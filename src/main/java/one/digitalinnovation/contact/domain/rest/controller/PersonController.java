package one.digitalinnovation.contact.domain.rest.controller;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.contact.domain.entities.Person;
import one.digitalinnovation.contact.domain.rest.dto.personDTO.PersonDTO;
import one.digitalinnovation.contact.domain.rest.services.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        Person map = modelMapper.map(dto, Person.class);
        Person person = service.save(map);
        return modelMapper.map(person, PersonDTO.class);
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
