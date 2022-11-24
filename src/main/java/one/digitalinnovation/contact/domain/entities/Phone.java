package one.digitalinnovation.contact.domain.entities;

import lombok.*;
import one.digitalinnovation.contact.domain.enums.PhoneType;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Phone implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 15,nullable = false)
    private String number;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PhoneType type;
}
