package one.digitalinnovation.contact.domain.rest.services;

import one.digitalinnovation.contact.domain.entities.Person;

import java.util.Optional;

public interface PersonService {

    Person save(Person person);
    Optional<Person> findById(String cpf);
}
