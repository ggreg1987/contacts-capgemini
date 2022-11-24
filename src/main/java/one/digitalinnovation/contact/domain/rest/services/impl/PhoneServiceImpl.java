package one.digitalinnovation.contact.domain.rest.services.impl;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.contact.domain.entities.Phone;
import one.digitalinnovation.contact.domain.repository.PhoneRepository;
import one.digitalinnovation.contact.domain.rest.services.PhoneService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository repository;

    @Override
    public Phone save(Phone phone) {
        return repository.save(phone);
    }

    @Override
    public Optional<Phone> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Page<Phone> find(Phone phone, Pageable pageable) {

        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(phone,exampleMatcher);

        return repository.findAll(example,pageable);


    }
}
