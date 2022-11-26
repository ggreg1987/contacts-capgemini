package one.digitalinnovation.contact.domain.rest.dto.personDTO;

import lombok.*;
import one.digitalinnovation.contact.domain.rest.dto.phoneDTO.PhoneDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

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
    private LocalDate birthDate = LocalDate.now();

    @Valid
    @NotEmpty
    private List<PhoneDTO> phones;

//    public String getBirthDate() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        return birthDate.format(formatter);
//    }
}
