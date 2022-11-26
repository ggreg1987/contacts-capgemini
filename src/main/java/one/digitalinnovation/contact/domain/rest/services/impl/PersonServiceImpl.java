package one.digitalinnovation.contact.domain.rest.services.impl;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.contact.domain.entities.Person;
import one.digitalinnovation.contact.domain.repository.PersonRepository;
import one.digitalinnovation.contact.domain.rest.dto.personDTO.PersonDTO;
import one.digitalinnovation.contact.domain.rest.services.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.StringMatcher.CONTAINING;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public Person save(Person person) {
      if(repository.existsByCpf(person.getCpf())) {
          throw new ResponseStatusException(BAD_REQUEST);
      }
      PersonDTO dto = modelMapper.map(person, PersonDTO.class);
      dto.setBirthDate(person.getBirthDate()
              .format(DateTimeFormatter
                      .ofPattern("dd-MM-yyyy")));
      return repository.save(person);
    }

    @Override
    public Person findById(String cpf) {
        if(!repository.existsByCpf(cpf)) {
            throw new ResponseStatusException(BAD_REQUEST);
        }
        return repository.findByCpf(cpf)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST));
    }

    @Override
    public List<Person> find(Person person) {

        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(CONTAINING);
        Example example = Example.of(person, exampleMatcher);

        return repository.findAll(example);
    }

    @Override
    public void delete(String cpf) {
        if(!repository.existsByCpf(cpf)) {
            throw new ResponseStatusException(NOT_FOUND);
        }
        repository.findByCpf(cpf)
                .map(person -> {
                    repository.delete(person);
                    return person;
                }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

    }

    @Override
    public Person update(String cpf,Person person) {
        if(!repository.existsByCpf(cpf)) {
            throw new ResponseStatusException(BAD_REQUEST);
        }
         return repository.findByCpf(cpf)
                 .map(found -> {
                     found.setCpf(person.getCpf());
                     return repository.save(person);
                 }).orElseThrow(() -> new ResponseStatusException(BAD_REQUEST));

    }
}
