package one.digitalinnovation.contact.domain.rest;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.contact.domain.entities.Phone;
import one.digitalinnovation.contact.domain.repository.PhoneRepository;
import one.digitalinnovation.contact.domain.rest.dto.PhoneDTO;
import one.digitalinnovation.contact.domain.rest.services.PhoneService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/phones")
@RequiredArgsConstructor
public class PhoneController {

    private final PhoneRepository repository;
    private final PhoneService service;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(CREATED)
    public PhoneDTO create(@RequestBody PhoneDTO dto) {
        Phone phone = modelMapper.map(dto, Phone.class);
        Phone save = service.save(phone);
        return modelMapper.map(save, PhoneDTO.class);
    }
    @GetMapping("{id}")
    @ResponseStatus(OK)
    public PhoneDTO findById(@PathVariable Long id) {
        return service.findById(id)
                .map(phone -> modelMapper.map(phone, PhoneDTO.class))
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }
}
