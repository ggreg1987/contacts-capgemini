package one.digitalinnovation.contact.domain.rest.dto.phoneDTO;

import lombok.*;
import one.digitalinnovation.contact.domain.enums.PhoneType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneDTO implements Serializable {

    private Long id;

    @NotEmpty
    private String number;

    @Enumerated(EnumType.STRING)
    private PhoneType type;
}
