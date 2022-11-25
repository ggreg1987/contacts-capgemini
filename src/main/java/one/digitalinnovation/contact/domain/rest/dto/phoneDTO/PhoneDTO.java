package one.digitalinnovation.contact.domain.rest.dto.phoneDTO;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneDTO {

    private Long id;
    @NotEmpty
    private String number;
    @NotEmpty
    private String type;
}
