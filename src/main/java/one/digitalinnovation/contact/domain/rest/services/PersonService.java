package one.digitalinnovation.contact.domain.rest.services;

import one.digitalinnovation.contact.domain.entities.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    Person save(Person person);
    Optional<Person> findById(String cpf);

    List<Person> find(Person person);

    void delete(Person person);
    Person update(String cpf,Person person);
}
