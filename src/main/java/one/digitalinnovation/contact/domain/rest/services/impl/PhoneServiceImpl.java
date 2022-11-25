package one.digitalinnovation.contact.domain.rest.services.impl;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.contact.domain.entities.Phone;
import one.digitalinnovation.contact.domain.enums.PhoneType;
import one.digitalinnovation.contact.domain.repository.PhoneRepository;
import one.digitalinnovation.contact.domain.rest.services.PhoneService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.data.domain.ExampleMatcher.StringMatcher.CONTAINING;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository repository;

    @Override
    public Phone save(Phone phone) {
        if(phone == null ) {
            throw new RuntimeException("Cant be save an empty Phone");
        }
        return repository.save(phone);
    }

    @Override
    public Optional<Phone> findById(Long id) {
        if(id == null) {
            throw new RuntimeException("Error, cant find phone");
        }
        return repository.findById(id);
    }

    @Override
    public Page<Phone> find(Phone phone, Pageable pageable) {

        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(CONTAINING);

        Example example = Example.of(phone,exampleMatcher);

        return repository.findAll(example,pageable);

    }

    @Override
    public void delete(Phone phone) {
        if(phone == null || phone.getId() == null) {
           throw new IllegalArgumentException("Phone cant be null");
        }
        repository.delete(phone);
    }

    @Override
    public Phone update(Phone phone) {
        if(phone == null || phone.getId() == null) {
            throw new ResponseStatusException(NOT_FOUND);
        }
        return repository.save(phone);
    }

    @Override
    public void phoneType(Long id, PhoneType type) {
        repository.findById(id)
                .map(phone -> {
                    phone.setType(type);
                    return repository.save(phone);
                }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }


}
