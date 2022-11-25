package one.digitalinnovation.contact.domain.rest.controller;
import lombok.RequiredArgsConstructor;
import one.digitalinnovation.contact.domain.entities.Phone;
import one.digitalinnovation.contact.domain.enums.PhoneType;
import one.digitalinnovation.contact.domain.rest.dto.phoneDTO.PhoneDTO;
import one.digitalinnovation.contact.domain.rest.dto.phoneDTO.PhoneTypeDTO;
import one.digitalinnovation.contact.domain.rest.services.PhoneService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/phones")
@RequiredArgsConstructor
public class PhoneController {

    private final PhoneService service;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(CREATED)
    public PhoneDTO create(@RequestBody @Valid PhoneDTO dto) {
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
    @GetMapping
    public Page<PhoneDTO> find(PhoneDTO dto, Pageable pageable) {
        Phone phone = modelMapper.map(dto, Phone.class);
        Page<Phone> phones = service.find(phone, pageable);
        List<PhoneDTO> dtoList = phones.getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, PhoneDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList,pageable,phones.getTotalElements());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        Phone phone = service.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        service.delete(phone);
    }
    @PutMapping("{id}")
    public PhoneDTO update(@PathVariable Long id,@RequestBody PhoneDTO dto) {
        return service.findById(id)
                .map(phone -> {
                    phone.setId(dto.getId());
                    service.update(phone);
                    return modelMapper.map(phone, PhoneDTO.class);
                }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }
    @PatchMapping("{id}")
    public void type(@PathVariable Long id,@RequestBody PhoneTypeDTO dto) {
        String typeDTO = dto.getTypeDTO();
        service.phoneType(id, PhoneType.valueOf(typeDTO));
    }
}
