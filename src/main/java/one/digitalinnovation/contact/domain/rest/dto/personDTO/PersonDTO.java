package one.digitalinnovation.contact.domain.rest.dto.personDTO;

import lombok.*;
import one.digitalinnovation.contact.domain.rest.dto.phoneDTO.PhoneDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDTO implements Serializable {

    @NotEmpty
    private String cpf;
    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    private String birthDate;

    @Valid
    @NotEmpty
    private List<PhoneDTO> phones;

    public LocalDate getBirthDate() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return LocalDate.parse(this.birthDate, formatter);
    }
}
