package one.digitalinnovation.contact.domain.rest.services;

import one.digitalinnovation.contact.domain.entities.Phone;

import java.util.List;
import java.util.Optional;

public interface PhoneService {

    Phone save(Phone phone);
    Optional<Phone> findById(Long id);
    List<Phone> find(Phone phone);

    void delete(Phone phone);
    Phone update(Long id,Phone phone);
}
