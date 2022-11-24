package one.digitalinnovation.contact.domain.rest.services;

import one.digitalinnovation.contact.domain.entities.Phone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PhoneService {

    Phone save(Phone phone);
    Optional<Phone> findById(Long id);

    Page<Phone> find(Phone phone, Pageable pageable);
}
