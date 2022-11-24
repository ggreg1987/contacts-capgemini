package one.digitalinnovation.contact.domain.rest.services;

import one.digitalinnovation.contact.domain.entities.Phone;

import java.util.Optional;

public interface PhoneService {

    Phone save(Phone phone);
    Optional<Phone> findById(Long id);
}
