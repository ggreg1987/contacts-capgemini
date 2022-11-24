package one.digitalinnovation.contact.domain.rest.services.impl;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.contact.domain.entities.Person;
import one.digitalinnovation.contact.domain.repository.PersonRepository;
import one.digitalinnovation.contact.domain.rest.services.PersonService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;

    @Override
    public Person save(Person person) {
        if(repository.existsByCpf(person.getCpf())) {
            throw new RuntimeException("Duplicated CPF");
        }

        LocalDate.parse(person.getBirthDate()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        return repository.save(person);
    }

    @Override
    public Optional<Person> findById(String cpf) {
        return repository.findByCpf(cpf);
    }
}
