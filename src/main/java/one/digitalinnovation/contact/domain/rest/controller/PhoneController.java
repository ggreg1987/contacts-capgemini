package one.digitalinnovation.contact.domain.rest.controller;

import lombok.RequiredArgsConstructor;
import one.digitalinnovation.contact.domain.entities.Phone;
import one.digitalinnovation.contact.domain.rest.dto.phoneDTO.PhoneDTO;
import one.digitalinnovation.contact.domain.rest.services.PhoneService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

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
        Phone phone = service.findById(id);
        return modelMapper.map(phone,PhoneDTO.class);
    }

    @GetMapping
    public List<PhoneDTO> find(PhoneDTO dto) {
        Phone phone = modelMapper.map(dto, Phone.class);
        List<Phone> phoneList = service.find(phone);
        return phoneList
                .stream()
                .map(list -> modelMapper.map(list,PhoneDTO.class))
                .collect(Collectors.toList());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("{id}")
    public PhoneDTO update(@PathVariable Long id,@RequestBody @Valid PhoneDTO dto) {
        Phone phone = modelMapper.map(dto, Phone.class);
        Phone update = service.update(id, phone);
        return modelMapper.map(update,PhoneDTO.class);
    }
}
