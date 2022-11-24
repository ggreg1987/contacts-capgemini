package one.digitalinnovation.contact.domain.rest.dto.phoneDTO;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneDTO {

    private Long id;
    private String number;
    private String type;
}
