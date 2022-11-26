package one.digitalinnovation.contact.domain.rest.services;

import one.digitalinnovation.contact.domain.entities.Phone;

import java.util.List;

public interface PhoneService {

    Phone save(Phone phone);
    Phone findById(Long id);
    List<Phone> find(Phone phone);

    void delete(Long id);
    Phone update(Long id,Phone phone);
}
