package one.digitalinnovation.contact.domain.rest.services.impl;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.contact.domain.entities.Person;
import one.digitalinnovation.contact.domain.repository.PersonRepository;
import one.digitalinnovation.contact.domain.rest.services.PersonService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.StringMatcher.CONTAINING;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;

    @Override
    public Person save(Person person) {
        if( person == null) {
            throw new ResponseStatusException(BAD_REQUEST);
        }
        return repository.save(person);
    }

    @Override
    public Optional<Person> findById(String cpf) {
        if(!repository.existsByCpf(cpf)) {
            throw new ResponseStatusException(BAD_REQUEST);
        }
        return repository.findByCpf(cpf);
    }

    @Override
    public Page<Person> find(Person person, Pageable pageable) {

        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(CONTAINING);

        Example example = Example.of(person, exampleMatcher);

        return repository.findAll(example,pageable);

    }

    @Override
    public void delete(Person person) {
        if(!repository.existsByCpf(person.getCpf())) {
            throw new IllegalArgumentException("Error, wrong cpf");
        }
        repository.delete(person);
    }

    @Override
    public Person update(String cpf,Person person) {
        if(!repository.existsByCpf(person.getCpf())) {
            throw new ResponseStatusException(BAD_REQUEST);
        }
         return findById(cpf)
                 .map(found -> {
                     found.setCpf(person.getCpf());
                     return repository.save(person);
                 }).orElseThrow(() -> new ResponseStatusException(BAD_REQUEST));

    }
}
