package one.digitalinnovation.contact.domain.rest.dto.personDTO;

import lombok.*;
import one.digitalinnovation.contact.domain.rest.dto.phoneDTO.PhoneDTO;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDTO {

    @NotEmpty
    private String cpf;
    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    @NotEmpty
    private String birthDate;
    private List<PhoneDTO> phoneDTOS;
}
