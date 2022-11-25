package one.digitalinnovation.contact.domain.rest.dto.personDTO;

import lombok.*;
import one.digitalinnovation.contact.domain.rest.dto.phoneDTO.PhoneDTO;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDTO {

    private String cpf;
    private String name;
    private String email;
    private String birthDate;
    private List<PhoneDTO> phoneDTOS;
}
